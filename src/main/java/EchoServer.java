import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/3 上午11:25
 */
public class EchoServer {
  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public void start() throws InterruptedException {
    final RouteHandler routeHandler=new RouteHandler();
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override protected void initChannel(SocketChannel socketChannel) throws Exception {
              System.out.println("Connection new channel: " + socketChannel.localAddress());
              /**
               * 此处是直接使用http协议进行编解码了，其实可以根据channel的首字段来判断协议类型，
               * 再根据不同的协议动态向pipeline中add不同的编解码器
               */
              socketChannel.pipeline().addLast("conectionManager",new ConnectionManager());
              socketChannel.pipeline().addLast("http_request", new HttpRequestDecoder());
              socketChannel.pipeline().addLast("http_response", new HttpResponseEncoder());
              socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
              socketChannel.pipeline().addLast("timeoutDetectot",new IdleStateHandler(0,0,5));
              socketChannel.pipeline().addLast("timeOutHandler",new TimeoutHandler());
              socketChannel.pipeline().addLast(routeHandler);
            }
          });
      ChannelFuture channelFuture = bootstrap.bind().sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully().sync();
      workerGroup.shutdownGracefully().sync();
    }
  }
}
