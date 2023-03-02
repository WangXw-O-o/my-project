package demo.io.nio.reactor.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 回显处理器
 */
public class EchoHandler implements Runnable {

    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private static final int RECEIVING = 0, SENDING = 1;
    private int state = RECEIVING;

    public EchoHandler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        //再注册一次，取得选择键，再设置感兴趣的IO事件
        this.selectionKey = this.socketChannel.register(selector, 0);
        //将当前 处理器对象 附加到key上
        this.selectionKey.attach(this);
        //在注册监听 Read 就绪事件
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == SENDING) {
                System.out.println("SENDING");
                socketChannel.write(byteBuffer);
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_READ);
                state = RECEIVING;
                //处理完成需要关闭通道，不然会一直触发事件
                socketChannel.close();
            } else if (state == RECEIVING){
                System.out.println("RECEIVING");
                int length = 0;
                while ((length = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));
                }
                byteBuffer.flip();
                //准备监听写事件
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
