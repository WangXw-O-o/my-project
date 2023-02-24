package demo.nio.reactor;

import demo.nio.reactor.multi.MultiThreadEchoServerReactor;
import demo.nio.reactor.single.EchoServerReactor;

import java.io.IOException;

/**
 * Reactor模式 Demo，基于 NIO 实现
 */
public class ReactorDemo {

    public static void main(String[] args) throws IOException {
//        new Thread(new EchoServerReactor()).start();
        MultiThreadEchoServerReactor reactor = new MultiThreadEchoServerReactor();
        reactor.startService();
    }

}
