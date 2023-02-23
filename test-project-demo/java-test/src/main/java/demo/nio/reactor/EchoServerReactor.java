package demo.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServerReactor implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public EchoServerReactor() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(18899));
        //注册，并获取对应的 SelectionKey
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptorHandler());

    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    //当新链接就绪时，执行的是 AcceptorHandler 的 run 方法
                    //当读事件就绪时，执行的是 EchoHandler 的 run 方法
                    dispatch(next);
                    iterator.remove();
                }
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取key上附加的处理器对象，然后执行处理
    public void dispatch(SelectionKey key) {
        Runnable attachment = (Runnable) key.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }

    //新连接处理器
    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    //构造对象时，就将key上附加的处理器对象从AcceptorHandler转换成了EchoHandler
                    new EchoHandler(socketChannel, selector);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
