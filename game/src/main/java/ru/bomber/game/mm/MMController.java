
package ru.bomber.game.mm;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bomber.game.GameApplication;
import ru.bomber.game.service.BomberService;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/matchmaker")
public class MMController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameApplication.class);
    private static Integer gameId = null;
    private static AtomicLong idGenerator = new AtomicLong();
    public static final int MAX_PLAYER_IN_GAME = 4;

    @Autowired
    private BomberService bomberService;
    /**
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     * localhost:8080/matchmaker/join -d 'name=bomberman'
     * we have default gameId = 42
     */
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
                startThread.suspendThread();
                bomberService.addToDB(gameId, ConnectionQueue.getInstance(), new Date());

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
}
