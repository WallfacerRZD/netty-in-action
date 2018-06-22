package chapter5;

import io.netty.buffer.*;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * @author WallfacerRZD
 * @date 2018/6/21 18:42
 */
public class Snippets {
    public static void listing5_1() {
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            // handleArray(array, offset, length);
        }
    }

    public static void listing5_2() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer();
        if (!byteBuf.hasArray()) {
            int length = byteBuf.readableBytes();
            byte[] array = new byte[length];
            byteBuf.readBytes(array);
            // handleArray(array, 0, length);
        }
    }

    public static void listing5_3() {
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);
        ByteBuffer[] message = new ByteBuffer[]{header, body};
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }

    public static void listing5_4() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        ByteBuf bodyBuf = ByteBufAllocator.DEFAULT.directBuffer();
        messageBuf.addComponents(headerBuf, bodyBuf);
        // ...
        messageBuf.removeComponent(0);
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString());
        }
    }

    public static void listing5_5() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        int length = compBuf.readableBytes();
        byte[] array = new byte[length];
        compBuf.getBytes(compBuf.readerIndex(), array);
        // handleArray(array, 0, array.length);
    }

    public static void listing5_6() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.getByte(i);
            System.out.println(b);
        }
    }

    public static void listing5_7() {
        ByteBuf buffer = Unpooled.buffer();
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    public static void listing5_8() {
        Random random = new Random();
        ByteBuf byteBuf = Unpooled.buffer();
        while (byteBuf.writableBytes() >= 4) {
            byteBuf.writeInt(random.nextInt());
        }
    }

    public static void listing5_9() {
        ByteBuf byteBuf = Unpooled.buffer();
        int index = byteBuf.forEachByte(ByteProcessor.FIND_CR);
    }

    public static void listing5_10() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(CharsetUtil.UTF_8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    public static void listing5_11() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        ByteBuf copy = buf.copy(0, 14);
        System.out.println(copy.toString(CharsetUtil.UTF_8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) != copy.getByte(0);
    }

    public static void listing5_12() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        System.out.println(ByteBufUtil.hexDump(byteBuf));
        System.out.println((char) byteBuf.getByte(0));
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        byteBuf.setByte(0, (byte) 'B');
        System.out.println((char) byteBuf.getByte(0));

        assert readerIndex == byteBuf.readerIndex();
        assert writerIndex == byteBuf.writerIndex();
    }

    public static void listing5_13() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        System.out.println((char) byteBuf.getByte(0));
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        byteBuf.writeByte((byte) '?');
        assert readerIndex == byteBuf.readerIndex();
        assert writerIndex != byteBuf.writerIndex();
    }

    public static void listing5_14() {
/*        Channel channel = ...;
        ByteBufAllocator allocator = channel.alloc();
        ChannelHandlerContext ctx = ...;
        ByteBufAllocator allocator1 = ctx.alloc();*/
    }

    public static void listing5_15() {
/*        Channel channel = ...;
        ByteBufAllocator allocator = channel.alloc();
        ByteBuf buffer = allocator.directBuffer();
        assert buffer.refCnt() == 1;*/
    }

    public static void listing5_16() {
/*        ByteBuf buffer = ...;
        boolean released = buffer.release();*/
    }
}
