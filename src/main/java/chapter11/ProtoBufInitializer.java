package chapter11;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author WallfacerRZD
 * @date 2018/6/25 9:57
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new ProtobufVarint32FrameDecoder(),
                new ProtobufEncoder(),
                new ProtobufDecoder(lite),
                new ObjectHandler()
        );
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Channel> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Channel msg) throws Exception {
            // Do something with the object
        }
    }
}
