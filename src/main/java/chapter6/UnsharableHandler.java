package chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author WallfacerRZD
 * @date 2018/6/22 17:55
 */
@ChannelHandler.Sharable
public class UnsharableHandler extends ChannelInboundHandlerAdapter {
    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count++;
        System.out.println("channelRead(...) called the " + count + " time");
        ctx.fireChannelRead(msg);
    }
}
