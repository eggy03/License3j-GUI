package io.github.eggy03.application.log;

import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;

import java.util.Map;

/**
 * A custom TinyLog writer that publishes all logs to a queue defined by {@link LogQueue}
 */
public class LogQueueWriter extends AbstractFormatPatternWriter {

    public LogQueueWriter(final Map<String, String> properties) {
        super(properties);
    }

    @Override
    public void write(LogEntry logEntry) {
        LogQueue.enqueueMessage(render(logEntry));
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
