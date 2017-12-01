

package boot;


import com.sun.jna.platform.unix.X11;
import okhttp3.Request;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import thread.Connection;

import okhttp3.Response;
import thread.ConnectionQueue;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("/matchmaker")
public class MMController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MmApplication.class);
    private static Integer gameId = null;
    private static AtomicLong idGenerator = new AtomicLong();
    public static final int MAX_PLAYER_IN_GAME = 4;


    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) throws IOException, InterruptedException {
        StartThread startThread = new StartThread(gameId); //creates an object of StartTh
        if (gameId == null) {
            log.info("Requesting GS to create a game");
//            gameId = Long.parseLong(response.body().string()); // надо будет присовить gameId, т.е. response
            gameId = Integer.parseInt(Requests.create(MAX_PLAYER_IN_GAME).body().string());
            log.info("Response of GS - gameId={}", gameId);
            startThread.setGameId(gameId);
            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(), name));
            log.info("Adding a new player to the list: name={}", name);
        } else {
            log.info("Adding a new player to the list: name={}", name);
            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(), name));
            if (ConnectionQueue.getInstance().size() == MAX_PLAYER_IN_GAME) {
                startThread.start(); //starts our thread
            }
        }
        log.info("Responding with gameID to the player={}, gameID={}", name, gameId);
        startThread.resumeThread(); //потоки на перегонки, не хорошо
        return ResponseEntity.ok().body(gameId.toString());
    }

    public static void clear() {
        gameId = null;
        ConnectionQueue.getInstance().clear();
    }

    public static Integer getGameId() {
        return gameId;
    }
}

