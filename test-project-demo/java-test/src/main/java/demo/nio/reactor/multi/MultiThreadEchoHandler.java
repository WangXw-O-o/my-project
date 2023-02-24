package demo.nio.reactor.multi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadEchoHandler implements Runnable {

    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private static final int RECEIVING = 0, SENDING = 1;
    private int state = RECEIVING;
    private static final ExecutorService pool = Executors.newFixedThreadPool(4);

    public MultiThreadEchoHandler(SocketChannel socketChannel, Selector selector) throws IOException {
        System.out.println("构建 MultiThreadEchoHandler...");
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        selector.wakeup();
        this.selectionKey = this.socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        pool.execute(this::asyncRun);
    }

    public synchronized void asyncRun() {
        try {
            System.out.println(Thread.currentThread().getName() + ": ");
            if (this.state == SENDING) {
                System.out.println("-------> MultiThreadEchoHandler SENDING...");
                this.socketChannel.write(byteBuffer);
                this.byteBuffer.flip();
                this.selectionKey.interestOps(SelectionKey.OP_READ);
                this.state = RECEIVING;
                //处理完成需要关闭通道，不然会一直触发事件
                System.out.println("处理完成，关闭通道...");
                socketChannel.close();
            } else if (this.state == RECEIVING){
                System.out.println("-------> MultiThreadEchoHandler RECEIVING...");
                int length = 0;
                while ((length = this.socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(this.byteBuffer.array(), 0, length));
                }
                this.byteBuffer.flip();
                //准备监听写事件
                this.selectionKey.interestOps(SelectionKey.OP_WRITE);
                this.state = SENDING;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
