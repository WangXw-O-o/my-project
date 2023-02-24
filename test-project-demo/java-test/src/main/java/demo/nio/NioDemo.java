package demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioDemo {

    /**
     * 简单的服务器使用 Selector 监听服务端口并接收数据
     * @throws IOException
     */
    public static void runServerSocketChannel() throws IOException {
        //创建一个Selector
        Selector selector = Selector.open();
        //创建一个服务端的ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //设置是否阻塞
        channel.configureBlocking(false);
        //绑定套接字，并监听套接字
        channel.bind(new InetSocketAddress(18899));
        //将Channel注册到Selector上, 指定选择器要监听的IO事件类型
        //ServerSocketChannel 只能监听 OP_ACCEPT 事件
        channel.register(selector, SelectionKey.OP_ACCEPT);

        //轮询Selector，当有IO事件时执行对应操作
        //select() 阻塞调用，直到有一个Channel发生了注册的IO事件
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //触发接收事件
                if (key.isAcceptable()) {
                    System.out.println("触发接收事件：OP_ACCEPT");
                    //创建一个缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //获取 key 对应的 Channel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将数据读入到缓冲区
                    int length = socketChannel.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array(), 0, length));

                }
                //移除已被处理的事件
                iterator.remove();
            }
        }
    }

    /**
     * 使用 SocketChannel 监听 OP_READ 事件
     * 实际中并不会这么使用
     * @throws IOException
     */
    public static void runServer_OP_READ() throws IOException {
        //创建一个Selector
        Selector selector = Selector.open();
        //创建一个服务端的ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //设置是否阻塞
        channel.configureBlocking(false);
        //绑定套接字，并监听套接字
        channel.bind(new InetSocketAddress(18899));
        //将Channel注册到Selector上, 指定选择器要监听的IO事件类型
        //ServerSocketChannel 只能监听 OP_ACCEPT 事件
        channel.register(selector, SelectionKey.OP_ACCEPT);

        //轮询Selector，当有IO事件时执行对应操作
        //select() 阻塞调用，直到有一个Channel发生了注册的IO事件
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //触发接收事件
                if (key.isAcceptable()) {
                    System.out.println("触发接收事件：OP_ACCEPT");
                    //获取 key 对应的 Channel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    /**
                        在此处绑定一个监控 OP_READ 的 SocketChannel，当之后的数据接收的时候，就能触发写事件
                     */
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("触发写事件：OP_READ");
                    //创建一个缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //获取 key 对应的 Channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.configureBlocking(false);
                    //将数据读入到缓冲区
                    int length = 0;
                    while ((length = socketChannel.read(byteBuffer)) > 0) {
                        System.out.println(new String(byteBuffer.array(), 0, length));
                    }
                    //读完数据后关闭通道，不然会一直触发读事件
                    socketChannel.close();
                }
                //移除已被处理的事件
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        runServer_OP_READ();
    }

}
