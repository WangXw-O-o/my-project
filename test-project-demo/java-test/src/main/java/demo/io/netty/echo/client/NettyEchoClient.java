package demo.io.netty.echo.client;

import demo.io.netty.echo.client.hanlder.NettyEchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NettyEchoClient {

    private int serverPort;
    private String serverIp;
    private Bootstrap bootstrap = new Bootstrap();

    public NettyEchoClient(int serverPort, String serverIp) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }

    public void runClient() {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(serverIp, serverPort);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            ChannelFuture connect = bootstrap.connect();
            connect.addListener((ChannelFuture futureListener) -> {
               if (futureListener.isSuccess()) {
                   System.out.println("Echo客户端连接成功！");
               } else {
                   System.out.println("Echo客户端连接失败！");
               }
            });
            //阻塞，直到成功
            connect.sync();
            Channel channel = connect.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入发送内容: ");
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyEchoClient nettyEchoClient = new NettyEchoClient(18899, "127.0.0.1");
        nettyEchoClient.runClient();
    }
}
