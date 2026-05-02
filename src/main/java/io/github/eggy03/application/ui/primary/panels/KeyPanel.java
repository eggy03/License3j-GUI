package io.github.eggy03.application.ui.primary.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class KeyPanel extends JPanel {

    public KeyPanel() {
        setLayout(new MigLayout("insets 1", "[grow]", "[grow]"));
        setBorder(new TitledBorder("Sign Keys"));
    }
}
