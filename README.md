# mytomcat
本项目是基于Netty实现自己的web容器，重复造轮子，来巩固相关知识点

**功能**：
   + [x] **IOC容器**：通过自定注解`@Bee`实现对所有对象的管理,`通过@Autowired`注解实现对象的依赖注入
   + [x] **请求分发** 使用`@Controller`和`@RequestMappin` 实现自定义路由，匹配对应请求
   + [ ] **自动参数转换**
   + [x] **结果渲染**
   + [ ] **前置后置拦截器**
   + [ ] **session管理**
   + [ ] **cookie管理**




####快速启动
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
启动成功后，浏览器输入 http://127.0.0.1:8888， 可以看到初始页面如下：
![Alt text](./md/picture/default-index.png)

#### IOC容器
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
**setter**注入
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
**field**注入
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
那现在就来实现这样的功能，首先定义一个 Aware 接口，所有其他需要回调塞值的接口都继承自该接口，如下所示：
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




