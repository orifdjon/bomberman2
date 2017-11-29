package service;


import dao.GameSessionDao;
import model.GameSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thread.Connection;

import javax.transaction.Transactional;
import java.util.Queue;

@Service
public class BomberService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BomberService.class);

    @Autowired
    private GameSessionDao gameSessionDao;


    @Transactional
    public void addToDB(@NotNull Integer gameId, @NotNull Queue<? extends Connection> queue) {
        GameSession gameSession = new GameSession(queue);
        gameSession.setGameId(gameId);
        gameSessionDao.save(gameSession);
        log.info("Added a new line to DataBase: {}", gameSession);
    }
}
