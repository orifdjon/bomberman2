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
import thread.MatchMaker;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/matchmaker")
public class ConnectionController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MmApplication.class);
    List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
    private long gameId = 0;
    private int START_GAME = 4;
    private int CREATE_GAME = 1;



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


            gameId = create();//
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

    /**
     * curl test
     * <p>
     * curl -i localhost:8080/connection/list'
     * </p>
     */
    @RequestMapping(
            path = "create",
            method = RequestMethod.GET,
            consumes = MediaType.TEXT_PLAIN_VALUE
    )
    public String list() {
        java.util.List<String> arrayList = new ArrayList<>();
        String name = new String("");
        for (Connection connection : ConnectionQueue.getInstance()) {
            name += connection.getName() + " ";
        }
        return name;

    }


}
