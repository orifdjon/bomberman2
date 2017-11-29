package ru.bomber.game.service;



import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bomber.game.dao.GameSessionDao;
import ru.bomber.game.mm.Connection;
import ru.bomber.game.model.GameSession;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Queue;

@Service
public class BomberService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BomberService.class);

    @Autowired
    private GameSessionDao gameSessionDao;


    @Transactional
    public void addToDB(@NotNull Integer gameId, @NotNull Queue<? extends Connection> queue, @NotNull Date date) {
        GameSession gameSession = new GameSession(queue);
        gameSession.setGameId(gameId);
        gameSession.setTime(date);
        gameSessionDao.save(gameSession);
        log.info("Added a new line to DataBase: {}", gameSession);
    }
}
