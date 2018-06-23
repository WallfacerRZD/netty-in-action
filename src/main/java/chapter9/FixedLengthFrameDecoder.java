package chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author WallfacerRZD
 * @date 2018/6/23 17:20
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int FRAME_LENGTH;

    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength
            );
        }
        this.FRAME_LENGTH = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= FRAME_LENGTH) {
            ByteBuf buf = in.readBytes(FRAME_LENGTH);
            out.add(buf);
        }
    }
}
