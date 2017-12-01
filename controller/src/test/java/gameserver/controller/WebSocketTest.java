package gameserver.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GameServerApplication.class)
@WebIntegrationTest
public class WebSocketTest {





    static final String WEBSOCKET_GAMEID = "42";
    static final String WEBSOCKET_NAME = "Кунька_Задунайский";
    static final String WEBSOCKET_URI = "ws://localhost:8090/game/connect?gameId=" +
            WEBSOCKET_GAMEID + "&name=" + WEBSOCKET_NAME;




    @Test
    public void sendAndGet() throws Exception {

        String uri = "ws://localhost:8090/game/connect?gameId=" + 42 + "&name=" + name;

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            EventHandler socket = new EventHandler();
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            session.getRemote().sendString("Hello");
            session = fut.get();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

        Assert.assertEquals(name, blockingQueue.poll(1, SECONDS));
    }


}
