# mytomcat
本项目是基于Netty实现自己的web容器，重复造轮子，来巩固相关知识点，详细设计方案[点击这里](https://github.com/kangdengfei/mytomcat/blob/master/md/article/mytomcat.md)

**功能**：
   + [x] **IOC容器**:通过自定注解`@Bean`实现对所有对象的管理,`通过@Autowired`注解实现对象的依赖注入
   + [x] **请求分发**: 使用`@Controller`和`@RequestMappin` 实现自定义路由，匹配对应请求  
   + [x] **结果渲染**:对响应结果进行渲染支持html，json，plain格式
   + [x] **cookie管理**:内置cookie管理器
   + [x] **自动参数转换** 支持http请求参数对象转换成对象(基本数据类型，Map，List，JavaBean)
   + [x] **Filter拦截器**
   + [ ] **session管理**
  




#### 快速启动
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


####Bean管理
仿照 Spring，我们实习自己的IOC容器，可以通过自定义注解`@Bean`对所有的对象进行管理，`@Autowried`注解实现属性的依赖注入。
**Tips：** 更多信息请查看wiki: [Bean][1]

####自定义路由
仿照 Spring，我们可以通过`@Controller`,注解来自定义一个Controller.`@RequestMapping`  注解用在方法上。通过`@Controller`+`@RequestMapping`可以唯一定义一个http请求。
`@Param` 注解用在方法参数上，通过使用该注解可以自动将基本类型转换成POJO对象。
**Tips：** 更多信息请查看wiki: [Router][2]
#### Filter过滤器
filter过滤器的作用动态的拦截请求和响应。我们可以实现自己的filter来完成相应的功能。
**Tips：** 更多信息请查看wiki: [Filter][3]

#### 例子
在模块mytomcat-example中已经内置一些测试用例。启动`ServerBootStrapDemo`中的main函数，即可通过以下例子进行测试。

| 测试      |     请求 |   响应类型   |
| :--------: | :--------| :------: |
| home页    |   curl -v 127.0.0.1:9999 |  HTML  |
| get |curl -v 127.0.0.1:9999/student/getStudent?id=33 |JSON |
|post|curl -d "name=Andy&age=16" 127.0.0.1:9999/student/addStudent|JSON|
|cookie| curl -v "127.0.0.1:9999/cookie/add?name=jsession&value=222"|JSON |

[1]: https://github.com/kangdengfei/mytomcat/wiki/Bean
[2]: https://github.com/kangdengfei/mytomcat/wiki/Router
[3]: https://github.com/kangdengfei/mytomcat/wiki/Filter