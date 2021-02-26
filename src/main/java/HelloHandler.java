import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
import io.netty.util.CharsetUtil;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/26 上午10:23
 */
@ChannelHandler.Sharable
public class HelloHandler extends ChannelInboundHandlerAdapter {

  @Override public void channelRead(ChannelHandlerContext context, Object msg) {
    FullHttpRequest in = (FullHttpRequest) msg;
    ByteBuf message = Unpooled.copiedBuffer("Netty Hello", CharsetUtil.UTF_8);
    FullHttpResponse response =
        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, message);
    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, message.readableBytes());

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
    System.out.println("Come exception of: " + throwable.getMessage());
    context.close();
  }
}
