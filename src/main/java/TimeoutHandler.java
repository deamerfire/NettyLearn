import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/23 下午3:36
 */
public class TimeoutHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if(evt instanceof IdleStateEvent){
      ctx.writeAndFlush(Unpooled.copiedBuffer("timeOut", CharsetUtil.UTF_8)).addListener(
          ChannelFutureListener.CLOSE_ON_FAILURE);
    }else {
      super.userEventTriggered(ctx,evt);
    }
  }
}
