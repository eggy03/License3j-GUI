package io.github.eggy03.application.ui.primary.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
        add(logTextArea, "cell 0 1 1 1, grow");

        // add action listeners
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
