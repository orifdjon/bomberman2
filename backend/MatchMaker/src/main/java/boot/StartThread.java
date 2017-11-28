package boot;

import org.slf4j.LoggerFactory;

import java.io.IOException;

import static boot.MMController.MAX_PLAYER_IN_GAME;
import static java.util.concurrent.TimeUnit.SECONDS;

public class StartThread extends Thread {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(StartThread.class);
    private Integer gameId;
    private boolean suspendFlag;
    static final int TIMEOUT = 10;
    static final int MAX_TIMEOUTS = 3;
    private boolean isStarted = false;

    public StartThread(Integer gameId) {
        super("StartThread_gameId=" + gameId);
        suspendFlag = false;
        this.gameId = gameId;
    }

    @Override
    public void run() {

        while (suspendFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.info("Wait of thread={} interrupted", currentThread());
            }
        }
        int tryCounter = 0;
        while (!currentThread().isInterrupted()
                && tryCounter++ <= MAX_TIMEOUTS
                && !isStarted) {
            try {
                if (Integer.parseInt(Requests.checkStatus().body().string()) == MAX_PLAYER_IN_GAME) {
                    log.info("Sending a request to start the game, gameID = {}", gameId);
                    Requests.start(this.gameId.toString());
                    isStarted = true;
                } else {
                    log.info("Timeout for {} SECONDS, waiting for players to CONNECT. {} TIMEOUTS left",
                            TIMEOUT, MAX_TIMEOUTS - tryCounter);
                    sleep(SECONDS.toMillis(TIMEOUT));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                log.info("Sleep of thread={} interrupted", currentThread());
            }
        }
        if (!isStarted)
            log.info("failed to start the game");
        MMController.clear();
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public synchronized void suspendThread() throws InterruptedException {
        suspendFlag = true;
    }

    public synchronized void resumeThread() throws InterruptedException {
        suspendFlag = false;
        notify();
    }
}