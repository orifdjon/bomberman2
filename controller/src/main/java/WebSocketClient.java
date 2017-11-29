import gameserver.controller.EventHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;

public class WebSocketClient {
    public static void main(String[] args) {
        // connection url
        String uri = "ws://localhost:8090/game/connect";

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            // The socket that receives events
            EventHandler socket = new EventHandler();
            // Make a handshake with server
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            // Wait for Connect
            session = fut.get();
//            int counter = 0;
//            while (session.isOpen()) {
//                sleep(SECONDS.toMillis(1));
//                System.out.println("Connection is opened for" + ++counter + "sec");
//            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
