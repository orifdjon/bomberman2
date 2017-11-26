package boot;




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
import thread.ConnectionQueue;
import thread.GameSession;

import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("/matchmaker")
public class MMController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MmApplication.class);
    List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);

    private static String gameId = null;
    private static AtomicLong idGenerator = new AtomicLong();
    private static int playerCounter = 0;
    private static int START_GAME = 4;
    private static int CREATE_GAME = 1;

    /**
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     * localhost:8080/matchmaker/join -d 'name=bomberman'
     * we have defoult gameId = 42
     */
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) throws IOException {


        if (gameId == null) {
            Response response = Requests.create(GameSession.PLAYERS_IN_GAME);
            gameId = response.body().string();// надо будет присовить gameId, т.е. response
            candidates.add(new Connection(idGenerator.getAndIncrement(), name));
            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(), name));
        } else {
            candidates.add(new Connection(idGenerator.getAndIncrement(),name));
            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(), name));
            if (candidates.size() == START_GAME) {
                Requests.start(42);//
                gameId = null;
                candidates.clear();
            }
        }

        return ResponseEntity.ok().body(gameId);
    }




}
