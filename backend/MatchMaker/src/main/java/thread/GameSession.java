package thread;


import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */
public class GameSession {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MatchMaker.class);
    private static AtomicLong idGenerator = new AtomicLong();

    public static final int PLAYERS_IN_GAME = 4;

    private final Connection[] connections;
    private final long id = idGenerator.getAndIncrement();

    public GameSession(Connection[] connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "connections=" + Arrays.toString(connections) +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }
}
