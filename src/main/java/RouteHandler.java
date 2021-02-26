import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/26 上午10:11
 */
@ChannelHandler.Sharable public class RouteHandler extends ChannelInboundHandlerAdapter {
  @Override public void channelRead(ChannelHandlerContext context, Object msg) {
    FullHttpRequest in = (FullHttpRequest) msg;

    System.out.println("Received meth: " + in.method());
    System.out.println("Received url: " + in.uri());

    if (in.uri().contains("echo")) {
      context.pipeline().addLast(new EchoServerHandler());
    } else if (in.uri().contains("hello")) {
      context.pipeline().addLast(new HelloHandler());
    }
    System.out.println("pipeline: " + context.pipeline());
    context.pipeline().remove(this);
    System.out.println("pipeline: " + context.pipeline());
    context.fireChannelRead(msg);
  }
}
