package chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author WallfacerRZD
 * @date 2018/6/18 21:54
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);
        while (true) {
            final Socket clientSocket = socket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            new Thread(() -> {
                try (OutputStream out = clientSocket.getOutputStream()) {
                    out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // close the OutputStream that clientSocket.getOutputStream() returns
                    // will close the associated socket
                    // so the following code are not necessary
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        // ignore on close
                    }
                }
            }).start();
        }
    }
}
