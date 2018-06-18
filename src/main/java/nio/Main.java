package nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author WallfacerRZD
 * @date 2018/6/3 9:57
 */
public class Main {
    private static Pipe pipe;

    static {
        try {
            pipe = Pipe.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // readNioPipe();
        writeNioPipe();
    }

    private static void nioRead(final String filePath) {
        try (FileChannel channel = new FileInputStream(filePath).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int byteRead = channel.read(buffer);
            while (byteRead != -1) {
                System.out.println("Read " + byteRead);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println((char) buffer.get());
                }
                buffer.clear();
                byteRead = channel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ioRead(final String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void select() throws IOException {
        SocketChannel channel = SocketChannel.open();
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterable = selectionKeys.iterator();
            while (iterable.hasNext()) {
                SelectionKey key = iterable.next();
                if (key.isAcceptable()) {

                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {

                } else if (key.isWritable()) {

                }
            }
        }

    }

    private static void nioWrite(final String fileName) {
        String dateToWrite = "this is a test sentence ";
        try (FileChannel fileChannel = new FileOutputStream(fileName).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.clear();
            buffer.put(dateToWrite.getBytes());
            buffer.flip();
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void socketReadChannel() throws IOException {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(2333));
            System.out.println(socketChannel);
            socketChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(buffer) != -1) {
                System.out.println("----");
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println((char) buffer.get());
                }
                buffer.clear();
            }
        }
    }

    private static void serverSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(2333));
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("someone in");
            String dataToWrite = "hello world";
            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.put(dataToWrite.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
        }
    }

    private static void openDatagramChannel() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.socket().bind(new InetSocketAddress(8080));
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        datagramChannel.receive(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
    }

    private static void writeDatagramChannel() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.put("this is test sentence".getBytes());
        buffer.flip();
        datagramChannel.send(buffer, new InetSocketAddress(8080));
    }

    private static void writeNioPipe() throws IOException {
        Pipe.SinkChannel sinkChannel = pipe.sink();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.put("test sentence ".getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            sinkChannel.write(buffer);
        }
    }

    private static void readNioPipe() throws IOException {
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        sourceChannel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
    }
}
