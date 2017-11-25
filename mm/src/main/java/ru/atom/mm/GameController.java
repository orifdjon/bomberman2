package ru.atom.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atom.thread.mm.GameRepository;

/**
 * Created by sergey on 3/15/17.
 */

@Controller
@RequestMapping("/game")
public class GameController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatController.class);

    /**
     * curl test
     * <p>
     * curl -i localhost:8080/game/list</p>
     */
    @RequestMapping(
            path = "list",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String list() {
        log.info("Games list request");
        return GameRepository.getAll().toString();
    }
}
