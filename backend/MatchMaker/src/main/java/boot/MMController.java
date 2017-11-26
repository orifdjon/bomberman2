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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("/matchmaker")
public class MMController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MmApplication.class);

    private static String gameId = null;
    private static AtomicLong idGenerator = new AtomicLong();
    private static final int MAX_PLAYER_IN_GAME = 4;

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
            Response response = Requests.create(MAX_PLAYER_IN_GAME);
            gameId = response.body().string();// надо будет присовить gameId, т.е. response
            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(), name));
        } else {
            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(), name));
            if (ConnectionQueue.getInstance().size() == MAX_PLAYER_IN_GAME) {
                Requests.start(gameId);//
                gameId = null;
                ConnectionQueue.getInstance().clear();

            }
        }

        return ResponseEntity.ok().body(gameId);
    }




}
