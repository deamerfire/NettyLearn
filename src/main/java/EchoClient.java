import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/3 下午12:00
 */
public class EchoClient {
  private final String host;
  private final int port;

  public EchoClient(String host,int port){
    this.host=host;
    this.port=port;
  }

  public void start() throws InterruptedException {
    EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
    try{
      Bootstrap bootstrap=new Bootstrap();
      bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host,port)).handler(
          new ChannelInitializer<SocketChannel>() {
            @Override protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new EchoClientHandler());
            }
          });
      ChannelFuture channelFuture=bootstrap.connect().sync();
      channelFuture.channel().closeFuture().sync();
    }finally {
      eventLoopGroup.shutdownGracefully().sync();
    }
  }
}
