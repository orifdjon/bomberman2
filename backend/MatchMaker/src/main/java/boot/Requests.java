package boot;

import okhttp3.*;

import java.io.IOException;

public class Requests {

    static final OkHttpClient client = new OkHttpClient();
    static final String PROTOCOL = "http://";
    static final String HOST = "localhost";
    static final String PORT = ":8090";


    static Response create(final int playerCounter) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "playerCount={" + playerCounter + "}"))
                .url(PROTOCOL + HOST + PORT + "/game/create")
                .build();
        return client.newCall(request).execute();
    }

    static Response start(final String gameId) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId={" + gameId + "}"))
                .url(PROTOCOL + HOST + PORT + "/game/start")
                .build();
        return client.newCall(request).execute();
    }




}
