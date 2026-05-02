package io.github.eggy03.application.ui.primary.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class LogPanel extends JPanel {

    public LogPanel() {
        setLayout(new MigLayout("insets 1", "[grow]", "[grow]"));
        setBorder(new TitledBorder("License Operation Logs"));
    }
}
