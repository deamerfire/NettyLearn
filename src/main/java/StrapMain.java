/**
 * @Author: wuqiong8
 * @Date: 2021/2/3 下午12:56
 */
public class StrapMain {
  private static final int PORT = 9000;
  private static final String SERVER = "Server";

  public static void main(String[] args) throws InterruptedException {
    if (SERVER.equalsIgnoreCase(args[0])) {
      new EchoServer(PORT).start();
    } else {
      new EchoClient("127.0.0.1", PORT).start();
    }
  }
}
