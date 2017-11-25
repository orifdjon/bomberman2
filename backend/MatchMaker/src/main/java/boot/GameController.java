package boot;


import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import thread.GameRepository;


/**
 * Created by sergey on 3/15/17.
 */

@Controller
@RequestMapping("/game")
public class GameController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MmApplication.class);

    /**
     * curl test
     * <p>
     * curl -i localhost:8080/game/create</p>
     */
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> create(@RequestParam("playerCount") String playerCount) {
        log.info("Game has been created");
        return ResponseEntity.ok().body(Long.toString(42));//возращает gameId
    }

    @RequestMapping(
            path = "/start",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> start(@RequestParam("gameId") String gameId) {
        log.info("Game has been started");
        return ResponseEntity.ok().body(Long.toString(42)); //возращает gameId
    }
}
