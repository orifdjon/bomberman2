package thread;

import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class ConnectionProducer implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConnectionProducer.class);
    private static final String[] names = {"John", "Paul", "George", "Someone else"};
    private static AtomicLong idGenerator = new AtomicLong();

    private static AtomicLong id = new AtomicLong();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long newId = id.getAndIncrement();

            ConnectionQueue.getInstance().offer(new Connection(idGenerator.getAndIncrement(),names[(int) (names.length)]));
            log.info("Connection {} added.", newId);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                log.info("Interrupted");
            }
        }
    }
}
