package gameserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameServerController {


    @MessageMapping("/game/connect")
    public String UserConnect (@RequestParam("gameId") String gameId, @RequestParam("name") String name) throws Exception {
        Thread.sleep(3000); // simulated delay
        return new String("Player" + name + "has been connected");
    }

}