package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.log.LogQueue;
import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

@SuppressWarnings("java:S1192")
public class LogPanel extends JPanel {

    // Non-Injectable UI Components
    private final JTextArea logTextArea = new JTextArea();

    public LogPanel initUI() {
        setLayout(new MigLayout("insets 1, fill", "[grow]", "[grow]"));
        setBorder(new TitledBorder("License Operation Logs"));

        logTextArea.setEditable(false);

        return this;
    }

    public LogPanel initComponents() {
        add(new JScrollPane(logTextArea), "cell 0 0 1 1, grow");

        return this;
    }

    public LogPanel initListeners() {

        // poll log queue every 100ms and flush log to text area in UI
        new Timer(100, _ -> {
            String logMessage = LogQueue.dequeueMessage();
            if (logMessage != null) SwingUtilities.invokeLater(() -> logTextArea.append(logMessage));
        }).start();

        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
