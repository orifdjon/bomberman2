package ru.atom.mm;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameSession;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;


@Controller
@RequestMapping("/connection")
public class ConnectionController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatController.class);


    /**
     * curl test
     * <p>
     * <p>
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     * localhost:8080/connection/connect -d 'id=1&name=bomberman'
     */
    @RequestMapping(
            path = "connect",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void connect(@RequestParam("id") long id,
                        @RequestParam("name") String name) {

        log.info("New connection id={} name={}", id, name);
        ConnectionQueue.getInstance().offer(new Connection(id, name));
    }

    /**
     * curl test
     * <p>
     * curl -i localhost:8080/connection/list'
     * </p>
     */
    @RequestMapping(
            path = "list",
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
