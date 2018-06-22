package chapter6;

import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;

/**
 * @author WallfacerRZD
 * @date 2018/6/22 11:22
 */
interface Snippets {
    @ChannelHandler.Sharable
    class DiscardHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ReferenceCountUtil.release(msg);
        }
    }

    @ChannelHandler.Sharable
    class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            // No need to do anything special
        }
    }

    @ChannelHandler.Sharable
    class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            ReferenceCountUtil.release(msg);
            promise.setSuccess();
        }
    }

    @ChannelHandler.Sharable
    class SharableHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Channel read message: " + msg);
            ctx.fireChannelRead(msg);
        }
    }

    @ChannelHandler.Sharable
    class UnsharableHandler extends ChannelInboundHandlerAdapter {
        private int count;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            count++;
            System.out.println("channelRead(...) called the " + count + " time");
            ctx.fireChannelRead(msg);
        }
    }

    class InboundExceptionHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    class OutBoundExceptionHandler extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            promise.addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            });
        }
    }

    ChannelHandler listing6_1 = new DiscardHandler();

    ChannelHandler listing6_2 = new SimpleDiscardHandler();

    ChannelHandler listing6_3 = new DiscardOutboundHandler();

    ChannelHandler listing6_10 = new SharableHandler();

    ChannelHandler listing6_11 = new UnsharableHandler();

    ChannelHandler listing6_14 = new OutBoundExceptionHandler();

}
