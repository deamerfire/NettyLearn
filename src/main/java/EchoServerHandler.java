import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/3 上午11:35
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

  @Override public void channelRead(ChannelHandlerContext context, Object msg) {
    FullHttpRequest in = (FullHttpRequest) msg;

    FullHttpResponse response =
        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, in.content());
    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, in.content().readableBytes());

    if (HttpUtil.isKeepAlive(in)) {
      response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    }
    context.write(response);
  }

  @Override public void channelReadComplete(ChannelHandlerContext context) throws Exception {
    context.flush();
  }

  @Override public void exceptionCaught(ChannelHandlerContext context, Throwable throwable)
      throws Exception {
    System.out.println("Come exception of: "+throwable.getMessage());
    context.close();
  }
}
