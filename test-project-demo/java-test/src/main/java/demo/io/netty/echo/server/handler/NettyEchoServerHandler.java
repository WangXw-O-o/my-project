package demo.io.netty.echo.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;


@Sharable //此注解表示当前的Handler实例可以被多个通道安全的共享
public class NettyEchoServerHandler  extends ChannelInboundHandlerAdapter {

    public static final NettyEchoServerHandler INSTANCE = new NettyEchoServerHandler();

    /**
     * 第一个入站处理器
     * @param ctx
     * @param msg 作为第一个入站的处理器，类型一定就是 ByteBuf
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("msg type: " + (in.hasArray() ? "堆内存" : "直接内存"));
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        System.out.println("server received: " + new String(arr, StandardCharsets.UTF_8));
        System.out.println("写回前， msg.refCnt: " + ((ByteBuf) msg).refCnt());
        ChannelFuture channelFuture = ctx.writeAndFlush(msg);
        channelFuture.addListener((ChannelFuture futureListener) -> {
            System.out.println("写回后，msg.refCnt: " + ((ByteBuf) msg).refCnt());
        });
    }

}
