package demo.io.nio.reactor.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiThreadEchoServerReactor {

    private final ServerSocketChannel serverSocketChannel;
    private final Selector[] selectors = new Selector[2];
    private final SubReactor[] subReactors;

    public MultiThreadEchoServerReactor() throws IOException {
        this.selectors[0] = Selector.open();
        this.selectors[1] = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(18899));
        this.serverSocketChannel.configureBlocking(false);
        SelectionKey key = this.serverSocketChannel.register(this.selectors[0], SelectionKey.OP_ACCEPT);

        key.attach(new AcceptorHandler());
        SubReactor subReactor_accept = new SubReactor(selectors[0]);
        SubReactor subReactor_read = new SubReactor(selectors[1]);
        subReactors = new SubReactor[]{subReactor_accept, subReactor_read};
    }

    //开启两个反应器线程执行分发
    public void startService() {
        Thread accept = new Thread(this.subReactors[0]);
        accept.setName("【连接】反应器");
        accept.start();
        Thread echo = new Thread(this.subReactors[1]);
        echo.setName("【读写】反应器");
        echo.start();
    }

    //子反应器，负责分发事件给处理器
    static class SubReactor implements Runnable {

        private final Selector selector;

        //构造时绑定到对应的选择器上
        public SubReactor(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
//                    System.out.println(Thread.currentThread().getName() + " -- [.[.[轮询中].].]");
                    /*
                     * 第二个线程 SubReactor 的开启会导致其对应的 Selector[1], 一直在 select() 阻塞状态，
                     * 无法进行 register 注册新 Channel，需要在 register 前执行 selector.wakeup();
                     */
                    this.selector.select();
                    Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        //分发事件处理
                        dispatch(next);
                        iterator.remove();
                    }
                    selectionKeys.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //执行绑定到key上的事件处理器
        public void dispatch(SelectionKey key) {
            Runnable handler = (Runnable) key.attachment();
            if (handler != null) {
                handler.run();
            }
        }
    }

    //新连接处理器
    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("AcceptorHandler 获取新连接...");
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    System.out.println("AcceptorHandler 将 Channel 转移到监听 Selector 上...");
                    //接收到新连接后，在另一个选择器上监听其他事件
                    new MultiThreadEchoHandler(socketChannel, selectors[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
