#### 目的
> 学习netty4中各个组件的使用，并实现一个基础的高并发系统

#### 当前功能
+ 连接数管理
> 通过非共享类 connectionManager实现
+ 支持Http协议的数据传输
> 通过内置的Decoder以及Encoder实现
+ 支持链接超时管理
> 通过内置IdleStateHandler进行检测，TimeoutHandler接受IdleStateEvent时间并处理
+ 支持路由动态配置Handler
> 由RouteHandler对url进行检测，并动态添加HelloHandler Or EchoHandler进行具体数据处理

#### 未来工作
+ RouteHandler中实现对url以及参数合法性检测

+ HelloHandler EchoHandler 都是对http协议的处理，是否可以抽离出公共父类出来

+ 支持Telnet协议

+ 丰富Handler的功能（可对比实现TSDB中的Netty功能）