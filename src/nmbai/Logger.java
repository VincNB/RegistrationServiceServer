package nmbai;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public enum Logger implements Runnable {
    INSTANCE;

    private static final String format = "[%s]:%s";
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024); //this is probably way too big
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void run() {
        while (running.get()) {
            try {
                String string = queue.poll(100, TimeUnit.MILLISECONDS);
                if (string != null) {
                    System.out.println(string);
                }
            } catch (InterruptedException ignored) {
                //empty??
            }
        }
    }

    public void log(String message) {
        if (running.get()) {
            if (!queue.offer(String.format(format, LocalTime.now().format(dateTimeFormatter), message))) {
                System.err.println("LOGGER IS FULL");
            }
        }
    }

    public void start() {
        running.set(true);
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
        try {
            Thread.sleep(100);
        } catch (InterruptedException exception) {

        }
    }

}
