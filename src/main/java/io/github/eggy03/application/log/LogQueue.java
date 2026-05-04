package io.github.eggy03.application.log;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This queue is consumed by a JTextArea to display all the logs
 */
public class LogQueue {

    private LogQueue() {
        /* This utility class should not be instantiated */
    }

    private static final BlockingQueue<String> BLOCKING_QUEUE = new LinkedBlockingQueue<>(5000);

    public static boolean enqueueMessage(@NonNull String logMessage) {

        boolean queueSuccess = BLOCKING_QUEUE.offer(logMessage);

        if(!queueSuccess) {
            BLOCKING_QUEUE.poll(); // remove oldest message
            return BLOCKING_QUEUE.offer(logMessage);
        }

        return true;
    }

    @Nullable
    public static String dequeueMessage() {
        return BLOCKING_QUEUE.poll();
    }

}
