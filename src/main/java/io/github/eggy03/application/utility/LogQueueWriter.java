package io.github.eggy03.application.utility;

import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;

import java.util.Map;

/**
 * TinyLog writer that forwards formatted log entries to {@link LogQueue}.
 */
public class LogQueueWriter extends AbstractFormatPatternWriter {

    public LogQueueWriter(final Map<String, String> properties) {
        super(properties);
    }

    /**
     * This writer renders each {@link LogEntry} using the configured pattern
     * and enqueues the result to {@link LogQueue}
     *
     * @param logEntry Log entry to output
     */
    @Override
    public void write(LogEntry logEntry) {
        LogQueue.enqueueLog(render(logEntry));
    }

    @Override
    public void flush() {
        // no-op for queue-based writer
    }

    @Override
    public void close() {
        // no-op for queue-based writer
    }

}
