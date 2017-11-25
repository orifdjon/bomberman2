package boot;



import okhttp3.OkHttpClient;
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

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ComparisonChain.start;


@Controller
@RequestMapping("/matchmaker")
public class ConnectionController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MmApplication.class);
    List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
    private static long gameId = 0;
    private static int START_GAME = 4;
    private static int CREATE_GAME = 1;




    /**
     * curl test
     * <p>
     * <p>
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     * localhost:8080/connection/connect -d 'id=1&name=bomberman'
     */
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {


        if (gameId == 0) {
            Response response = Requests.create();

            gameId = (long) response.toString();// надо будет присовить gameId, т.е. response
            candidates.add(new Connection(name));
            ConnectionQueue.getInstance().offer(new Connection(name));
        } else {
            candidates.add(new Connection(name));
            ConnectionQueue.getInstance().offer(new Connection(name));
            if (candidates.size() == CREATE_GAME) {
                start();//
                gameId = 0;
                candidates.clear();
            }
        }

        return ResponseEntity.ok().body(Long.toString(gameId));
    }




}
