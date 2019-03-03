# mytomcat
本项目是基于Netty实现自己的web容器，重复造轮子，来巩固相关知识点
**功能**：
   + [x] **IOC容器**：通过自定注解`@Bee`实现对所有对象的管理,`通过@Autowired`注解实现对象的依赖注入
   + [x] **请求分发** 使用`@Controller`和`@RequestMappin` 实现自定义路由，匹配对应请求
   + [ ] 自动参数转换
   + [ ] 结果渲染
   + [ ] 前置后置拦截器
   + [ ] session管理
   + [ ] cookie管理
   + [ ] 结果渲染 <br>
**代开发**
+ [ ] session管理
+ [ ] cookie管理
+ [ ] qin zh


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
启动成功后，浏览器输入 http://127.0.0.1:8888，可以看到初始页面如下：
![Alt text](./md/picture/default-index.png)

#### IOC容器
