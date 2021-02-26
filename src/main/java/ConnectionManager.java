import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: wuqiong8
 * @Date: 2021/2/23 下午3:13
 */
public class ConnectionManager extends ChannelInboundHandlerAdapter {
  private long connection_limit=0;
  private static final AtomicLong CONNECTIONS=new AtomicLong(0);

  ConnectionManager(long connection_limit){
    this.connection_limit=connection_limit;
  }

  ConnectionManager(){
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    if(connection_limit>0){
      if(CONNECTIONS.get()>connection_limit){
        throw new Exception("Channel size (" + CONNECTIONS.get() + ") exceeds total "
            + "connection limit (" + connection_limit + ")");
      }
    }
    System.out.println("Open connection and size: " + CONNECTIONS.incrementAndGet() + " time: "
        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Close connection and size: " + CONNECTIONS.decrementAndGet() + " time: "
        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
  }


}
