package boot;

import okhttp3.*;
import okio.ByteString;

import java.io.IOException;
import java.net.URI;

public class Requests {

    static final OkHttpClient client = new OkHttpClient();
    public static final String HTTP_PROTOCOL = "http://";
    public static final String WEBS_PROTOCOL = "ws://";
    public static final String HOST = "localhost";
    public static final String PORT_GS = ":8090";
    public static final String PORT_MM = ":8080";


    static Response create(final int playerCounter) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "playerCount={" + playerCounter + "}"))
                .url(HTTP_PROTOCOL + HOST + PORT_GS + "/game/create")
                .build();
        return client.newCall(request).execute();
    }

    static Response start(final String gameId) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId={" + gameId + "}"))
                .url(HTTP_PROTOCOL + HOST + PORT_GS + "/game/start")
                .build();
        return client.newCall(request).execute();
    }

    static Response checkStatus() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(HTTP_PROTOCOL + HOST + PORT_GS + "/game/checkstatus")
                .addHeader("host", HOST + PORT_GS)
                .build();
        return client.newCall(request).execute();
    }

//
//    void webSocketCLient(final String gameId, final String name) {
//        String uri = WEBS_PROTOCOL + HOST + PORT_GS +
//                "/game/connect?gameId={" + gameId + "}" + "&name={" + name + "}";//адрес, по которому подключаемся
//        OkHttpClient client = new OkHttpClient(); //объявляем клиента
//        Request request = new Request.Builder().url(uri).build(); // какой смысл подключаться и ничего не спрашивать?
//        EchoWebSocketListener listener = new EchoWebSocketListener();// набор методов для взаимодействия
//
//        WebSocket ws = client.newWebSocket(request, listener);//дали понять, что клиент идет по вебсокету
//
//        client.dispatcher().executorService().shutdown(); //поработали, надо бы закрыть соединение
//
//    }


}
