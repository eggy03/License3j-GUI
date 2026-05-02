package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.ui.utility.CustomLogQueue;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

@SuppressWarnings("java:S1192")
public class LogPanel extends JPanel {

    public LogPanel() {
        setLayout(new MigLayout("insets 1, fill", "[grow]", "[grow]"));
        setBorder(new TitledBorder("License Operation Logs"));

        // define components
        JTextArea logTextArea = new JTextArea();
        logTextArea.setEditable(false);

        // add components to the panel
        add(logTextArea, "cell 0 0 1 1, grow");

        // add action listeners
        readLogs(logTextArea);
    }

    private void readLogs (@NonNull JTextArea textArea) {

        new Timer(100, actionEvent-> {

            String logMessage = CustomLogQueue.dequeueMessage();
            if (logMessage != null)
                textArea.append(logMessage);

        }).start();
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
