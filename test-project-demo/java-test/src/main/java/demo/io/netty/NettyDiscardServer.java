package demo.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyDiscardServer {

    private int serverPort;

    ServerBootstrap bootstrap = new ServerBootstrap();

    public NettyDiscardServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        //boss轮询组
        EventLoopGroup boss = new NioEventLoopGroup(1);
        //worker轮询组
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //将轮询组加入到引导类中
            bootstrap.group(boss, worker);
            //设置通道的IO类型。NioServerSocketChannel：nio的TCP服务端通道
            bootstrap.channel(NioServerSocketChannel.class);
            //设置监听端口
            bootstrap.localAddress(serverPort);
            //设置通道的参数。SO_KEEPALIVE：心跳机制
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            //装配子通道的Pipeline
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyDiscardHandler());
                }
            });
            //绑定端口。sync()阻塞，直到绑定成功才返回。
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println("服务器启动成功！监听端口：" + channelFuture.channel().localAddress());
            //自我阻塞，直到通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    static class NettyDiscardHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf in = (ByteBuf) msg;
            try {
                System.out.println("收到消息！");
                while (in.isReadable()) {
                    System.out.println((char) in.readByte());
                }
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NettyDiscardServer nettyDiscardServer = new NettyDiscardServer(18899);
        nettyDiscardServer.runServer();
    }
}
