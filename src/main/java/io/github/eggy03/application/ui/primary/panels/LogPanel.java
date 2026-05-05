package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.utility.LogQueue;
import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

/**
 * UI panel responsible for displaying operational logs in real-time.
 *
 * <p>All feature operations are executed asynchronously using SwingWorkers
 * to avoid blocking the Event Dispatch Thread (EDT).</p>
 *
 * <p>Initialization follows a staged lifecycle:
 * <ul>
 *     <li>{@link #initUI()} – configures layout and UI properties</li>
 *     <li>{@link #initComponents()} – adds UI components to the panel</li>
 *     <li>{@link #initListeners()} – registers event handlers and background tasks</li>
 * </ul>
 */
@SuppressWarnings("java:S1192")
public class LogPanel extends JPanel {

    // Non-Injectable UI Components
    private final JTextArea logTextArea = new JTextArea();

    /**
     * Configures layout, borders, and component properties.
     *
     * @return this panel instance for chaining
     */
    public LogPanel initUI() {
        setLayout(new MigLayout("insets 1, fill", "[grow]", "[grow]"));
        setBorder(new TitledBorder("License Operation Logs"));

        logTextArea.setEditable(false);

        // this will prevent the text area from taking up all the screen space
        logTextArea.setRows(8);
        logTextArea.setColumns(10);

        return this;
    }

    /**
     * Adds and arranges all UI components within the panel.
     *
     * @return this panel instance for chaining
     */
    public LogPanel initComponents() {
        // you also need to wrap a scroll pane around the text area even if the parent panel/component
        // is wrapped in its own scroll pane
        // otherwise setRows() for text area wont work
        add(new JScrollPane(logTextArea), "cell 0 0 1 1, grow");

        return this;
    }

    /**
     * Registers action listeners and initializes background workers:
     *
     * @return this panel instance for chaining
     */
    public LogPanel initListeners() {

        // poll log queue every 100ms and flush log to text area in UI
        new Timer(100, actionEvent -> {
            String logMessage = LogQueue.getLog();
            if (logMessage != null) SwingUtilities.invokeLater(() -> logTextArea.append(logMessage));
        }).start();

        return this;
    }
}
