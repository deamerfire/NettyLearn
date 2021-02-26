import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/3 下午12:06
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
      throws Exception {
    System.out.println("Client receive: "+byteBuf.toString(CharsetUtil.UTF_8));
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx){
    Scanner scanner=new Scanner(System.in);
    do{
      String line=scanner.nextLine();
      ctx.writeAndFlush(Unpooled.copiedBuffer(line,CharsetUtil.UTF_8));
    }while (scanner.hasNextLine());

  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Channel inActive! ");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

}
