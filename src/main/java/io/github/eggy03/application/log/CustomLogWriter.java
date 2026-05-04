package io.github.eggy03.application.log;

import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;

import java.util.Map;

/**
 * A custom TinyLog writer that publishes all logs to a queue defined by {@link CustomLogQueue}
 */
public class CustomLogWriter extends AbstractFormatPatternWriter {

    public CustomLogWriter(final Map<String, String> properties) {
        super(properties);
    }

    @Override
    public void write(LogEntry logEntry) {
        CustomLogQueue.enqueueMessage(render(logEntry));
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
