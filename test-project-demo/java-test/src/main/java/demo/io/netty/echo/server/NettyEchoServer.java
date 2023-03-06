package demo.io.netty.echo.server;

import demo.io.netty.echo.server.handler.NettyEchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyEchoServer {

    private int serverPort;

    ServerBootstrap bootstrap = new ServerBootstrap();

    public NettyEchoServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        EventLoopGroup boss = new NioEventLoopGroup();
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
                    socketChannel.pipeline().addLast(NettyEchoServerHandler.INSTANCE);
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
        }

    }

    public static void main(String[] args) {
        NettyEchoServer server = new NettyEchoServer(18899);
        server.runServer();
    }

}
