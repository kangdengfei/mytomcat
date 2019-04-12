# mytomcat
[TOC]

本项目是基于Netty实现自己的web容器，重复造轮子，来巩固相关知识点

**功能**：
   + [x] **IOC容器**：通过自定注解`@Bee`实现对所有对象的管理,`通过@Autowired`注解实现对象的依赖注入
   + [x] **请求分发** 使用`@Controller`和`@RequestMappin` 实现自定义路由，匹配对应请求
   + [x] **自动参数转换**
   + [x] **结果渲染**
   + [x] **Filter拦截器**
   + [ ] **session管理**
   + [x] **cookie管理**




### 快速启动
项目启动值需要运行`ServerBootstrap`这个类，默认实现的是`NettyServer`这个类,如下所示：
```
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.preStart();//初始化context
        nettyServer.start()；//启动服务器
    }
}
```
启动成功后，浏览器输入 http://127.0.0.1:8888，可以看到初始页面如下：
![Alt text](./md/picture/default-index.png)

### IOC容器
#### 容器定义
通过注解方式，实现自己的IOC容器，首先定义一个BeanContext接口，如下所示:
```
public interface BeanContext {
    /**
     * 获得Bean
     * @param name Bean的名称
     * @return Bean
     */
    Object getBean(String name);

    /**
     * 获得bean
     * @param name bean的名称
     * @param clazz bean的类型
     * @param <T> 泛型
     * @return bean
     */
    <T> T getBean(String name,Class<T> clazz);
}
```
我们在系统启动的时候扫描出所有被@Bean注解修饰的类，然后对这些类进行实例化，把实例化的后的对象保存在一个Map中，具体做法如下所示：
```
public void initBean()  {
        logger.info("[DefaultBeanContex] begin initBean");
        try {

            Set<Class<?>> classSets = ClassScaner.scanPackageByAnnotation(CommonConstants.BEAN_SCAN_PACKAGE, Bean.class);
            if (CollectionUtil.isNotEmpty(classSets)) {
                /*
                 * 遍历所以类,找出有bean注解的Class，并且保存到beanmap中
                 */
                for (Class<?> cls : classSets) {

                    if (!cls.isAnnotation() && cls.isAnnotationPresent(Bean.class)) {
                        Bean annotation = (Bean) cls.getAnnotation(Bean.class);
                        String beanName = StrUtil.isNotBlank(annotation.name()) ? annotation.name() : cls.getName();
                        if (beanMap.containsKey(beanName)) {
                            logger.warn("[DefaultBeanContext] duplicate bean with name={}", beanName);
                            continue;
                        }
                        beanMap.put(beanName, cls.newInstance());
                    }
                }
                int size = beanMap.size();
                logger.info("[DefaultBeanContext] initBean success! [" + size + "] beans have created");
            } else {
                logger.warn("[DefaultBeanContext] no bean class scanned");
            }

        }catch (Exception e){
            logger.error("[DefaultBeanContext] initBean find error,cause:{}",e.getMessage(),e);
        }
    }
```
通过在指定路径下扫描出所有的类之后，把实例对象加入map中，但是对于已经加入过的bean 不能继续加入，需要获取bean时，直接通过nama到map中获取。

通过 `@Bean`注解已经将所有的对象管理起来了，接下来将依赖到的其他bean通过`@Autowried`注解实现属性的依赖注入，这个注解可以修饰在类的属性或者set方法上。
####  setter注入
```
private void propertyAnnotation(Object bean){
        logger.info("[DefaultBeanContext] start propertyAnnotation");
        try {
            // 获取其属性的描述
            PropertyDescriptor[] descriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
            for(PropertyDescriptor descriptor : descriptors){
                // 获取所有set方法
                Method setter = descriptor.getWriteMethod();
                // 判断set方法是否定义了注解
                if(setter!=null && setter.isAnnotationPresent(Autowired.class)){
                    // 获取当前注解，并判断name属性是否为空
                    Autowired resource = setter.getAnnotation(Autowired.class);
                    String name;
                    Object value = null;
                    if(StrUtil.isNotBlank(resource.name())){
                        // 获取注解的name属性的内容
                        name = resource.name();
                        value = beanMap.get(name);
                    }else{ // 如果当前注解没有指定name属性,则根据类型进行匹配
                        for(Map.Entry<String,Object>  entry : beanMap.entrySet()){
                            // 判断当前属性所属的类型是否在beanHolderMap中存在
                            if(descriptor.getPropertyType().isAssignableFrom(entry.getValue().getClass())){
                                // 获取类型匹配的实例对象
                                value = entry.getValue();
                                break;
                            }
                        }
                    }
                    // 允许访问private方法
                    setter.setAccessible(true);
                    // 把引用对象注入属性
                    setter.invoke(bean, value);
                }
            }
            logger.info("[DefaultBeanContext] propertyAnnotation success!");
        } catch (Exception e) {
            logger.info("[DefaultBeanContext] propertyAnnotation error,cause:{}",e.getMessage(),e);
        }
    }
```
#### field注入
```
 public void fieldAnnotation(Object bean){
        logger.info("[DefaultBeanContext] start fieldAnnotation");
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields){
            if (field != null && field.isAnnotationPresent(Autowired.class)){
                Autowired annotation = field.getAnnotation(Autowired.class);
                Object value = null;
                if (StrUtil.isNotBlank(annotation.name())){
                    value = beanMap.get(annotation.name());
                }else {
                    for (Map.Entry<String, Object> entry : beanMap.entrySet()){
                        if (field.getType().isAssignableFrom(entry.getValue().getClass())){
                            value = entry.getValue();
                            break;
                        }
                    }
                }
                // 允许访问private字段
                field.setAccessible(true);
                try {
                    // 把引用对象注入属性
                    field.set(bean,value);
                    logger.info("[DefaultBeanContext] fieldAnnotation success!");
                } catch (IllegalAccessException e) {
                    logger.error("[DefaultBeanContext] fieldAnnotation error,cause:{}",e.getMessage(),e);
                }
            }
        }
```
#### 通过Aware获取BeanContext
BeanContext 已经实现了，那怎么获取 BeanContext 的实例呢？Spring 中有很多的 Aware 接口，每种接口负责一种实例的回调，比如我们想要获取一个 BeanFactory 那只要将我们的类实现 BeanFactoryAware 接口就可以了，接口中的 setBeanFactory(BeanFactory factory) 方法参数中的 BeanFactory 实例就是我们所需要的，我们只要实现该方法，然后将参数中的实例保存在我们的类中，后续就可以直接使用了。
那现在我就来实现这样的功能，首先定义一个 Aware 接口，所有其他需要回调塞值的接口都继承自该接口，如下所示：
```
public interface Aware {
}
public interface BeanContextAware extends Aware {
    /**
     * 设置beanContext
     * @param beanContext
     */
     void setBeanContext(BeanContext beanContext);
}
```
接下来需要将 BeanContext 的实例注入到所有 BeanContextAware 的实现类中去。BeanContext 的实例很好得到，BeanContext 的实现类本身就是一个 BeanContext 的实例，并且可以将该实例设置为单例，这样的话所有需要获取 BeanContext 的地方都可以获取到同一个实例。
拿到 BeanContext 的实例后，我们就需要扫描出所有实现了 BeanContextAware 接口的类，并实例化这些类，然后调用这些类的 setBeanContext 方法，参数就传我们拿到的 BeanContext 实例。
```
public void processBeanContextAware() {
        Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(CommonConstants.BEAN_SCAN_PACKAGE,BeanContextAware.class);
        if (CollectionUtil.isNotEmpty(classSet)) {
            try {
                for (Class cls : classSet) {
                    if (!cls.isInterface() && BeanContextAware.class.isAssignableFrom(cls)) {
                        BeanContextAware aware = (BeanContextAware)cls.newInstance();
                        aware.setBeanContext(getInstance());
                    }
                }
            }catch (Exception e){
                logger.error("[DefaultBeanContext] processBeanContextAware error,cause:{}",e.getMessage());
            }
        }
    }
```
### 责任链模式设计Filter
filter过滤器的作用动态的拦截请求和响应，这里通过真正的链表结构来实现责任链，假设我们需要对前端提交的请求做以下操作：鉴权，登录，日志记录。通过责任链出来非常合适。

定义一个处理接口，如下图所示：
```
public interface Processor <T,R> {
    void process(T t,R r);
}
```
#### 定义节点
通过链表形式实现，我们需要有一个类表示链表中的某个节点，同时该节点需要有一个通类型的私有变量表示该节点的下个节点，这样就可以实现一个链表，如下所示：
```
public abstract class AbstractLinkedProcessor<T ,R> implements Processor<T ,R> {

    public AbstractLinkedProcessor next = null;

    public AbstractLinkedProcessor getNext(){
        return next;
    }

    public void setNext(AbstractLinkedProcessor next) {
        this.next = next;
    }

    @Override
    public void process(T content,R object) {

        doProcess(content,object);
        //调用下一个processor 进行处理
        if (next != null){
            next.process(content,object);
        }

    }
    //具体业务方法
    public abstract void doProcess(T t,R r);
}
```
具体节点需要继承`AbstractLinkedProcessor`类并实现`doProcess`方法。
#### 容器定义
接着我们需要定义一个容器，容器中应该有头尾两个节点，头节点是一个空节点，真正的节点从头节点的next节点开始，尾节点作为一个值针，用来指向当前添加的节点，下一次添加节点时，将在尾节点处开始添加。具体实现逻辑如下：
```
public class LinkedProcessorChain<T,R> {
    /**
     * 头节点,头节点是个空节点，真正的节点将添加到头节点的next节点上去
     */
    private AbstractLinkedProcessor first = new AbstractLinkedProcessor(){
        @Override
        public void doProcess(Object content, Object object) {
            this.getNext().process(content,object);
        }
    };

    /**
     * 尾节点 ,用来指向当前添加的节点，下次添加节点时，从尾节点开始添加
     */
    private AbstractLinkedProcessor last = first;

    public void addLast(AbstractLinkedProcessor processor){
        if (processor == null){
            return;
        }
        last.setNext(processor);
        last = processor;
    }

    public void process(T content,R object){
        first.doProcess(content,object);
    }
}
```
`LinkedProcessorChain`中`addLast()`方法用来向容器中添加到节点到尾节点中，`process`为容器的开始方法，执行此方法后开始链式执行后序节点。
#### 发现过滤器
继续来是如何在项目中使用过滤器。参考Servlet中对Filter的使用。定义一个接口：
```
public interface Filter<T,R,S > {
    void doFilter(T t,R r,S s);
}
```
在项目启动的时候为扫描实现了Filter的接口

外部使用时实现`Filter`接口并实现`doFilter`方法。项目启动时会扫描包中实现`Filter`接口的类，通过方式创建对象。具体使用例子如下：
```
public class LinkedProcessorChainFilter implements Filter<FullHttpRequest,FullHttpResponse,LinkedProcessorChain>  {

    @Override
    public void doFilter(FullHttpRequest httpRequest ,FullHttpResponse httpResponse,LinkedProcessorChain chain) {
        chain.addLast(new AuthProcessor());
       chain.addLast(new LogProcessor());
        chain.addLast(new LogginProcessor());
 chain.process(httpRequest,httpResponse);
    }
}
```
如下实现三个节点：
1. 认证
```
public class AuthProcessor extends  AbstractLinkedProcessor<FullHttpRequest,FullHttpResponse> {

    @Override
    public void doProcess(FullHttpRequest httpRequest,FullHttpResponse httpResponse) {
        System.out.println("auth: 认证通过");
//        throw new ServerException(ExceptionEnum.FORBBINEN.getCode(),ExceptionEnum.FORBBINEN.getMsg());
    }
}
```
2. 登录
```
public class LogginProcessor extends AbstractLinkedProcessor<FullHttpRequest,FullHttpResponse> {
    @Override
    public void doProcess(FullHttpRequest httpRequest,FullHttpResponse httpResponse){
        System.out.println("log: 登录成功");
    }
}
```
3. 日志
```
public class LogProcessor extends AbstractLinkedProcessor<FullHttpRequest,FullHttpResponse> {

    @Override
    public void doProcess(FullHttpRequest httpRequest,FullHttpResponse httpResponse){
        ChannelHandlerContext context = DefaultHttpContext.currentContext().getContext();
        InetSocketAddress socketAddress =(InetSocketAddress) context.channel().remoteAddress();
        System.out.println("请求iP：" + socketAddress.getAddress());
    }
}
```
启动项目后访问 127.0.0.1:9999 ,可发现终端打印日志如下：
![Alt text](./md/picture/filter.png)

至此filter过滤器的功能实现完成，目前实现了前置拦截功能，提前结束链式调用需要在相应的节点中抛出异常。后续功能继续完善。




