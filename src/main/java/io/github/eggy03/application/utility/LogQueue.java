package io.github.eggy03.application.utility;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Bounded queue for storing and retrieving logs written by {@link LogQueueWriter}
 */
public class LogQueue {

    private static final BlockingQueue<String> BLOCKING_QUEUE = new LinkedBlockingQueue<>(5000);

    private LogQueue() {
        /* This utility class should not be instantiated */
    }

    /**
     * Enqueues a log message.
     *
     * <p>If the queue is at capacity, the oldest logs are removed
     * until space becomes available.</p>
     *
     * @param logMessage the log message to enqueue (must not be {@code null})
     */
    static void enqueueLog(@NonNull String logMessage) {

        while (!BLOCKING_QUEUE.offer(logMessage)) { // keep dropping the oldest message until the message can be enqueued
            BLOCKING_QUEUE.poll();
        }
    }

    /**
     * Dequeues the next available log message.
     *
     * @return the next log message, or {@code null} if the queue is empty
     */
    @Nullable
    public static String getLog() {
        return BLOCKING_QUEUE.poll();
    }

}
