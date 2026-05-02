package io.github.eggy03.application.ui.primary.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("java:S1192")
public class StatusPanel extends JPanel {

    public StatusPanel() {
        setLayout(new MigLayout("insets 1, fillx", "[][]", "[][][][]"));
        setBorder(new TitledBorder("License Status"));

        // define components
        JLabel licenseSignedStatusLabel = new JLabel("License Signed");
        JTextField licenseSignedStatusTextField = new JTextField();
        licenseSignedStatusTextField.setEditable(false);

        JLabel licenseSavedStatusLabel = new JLabel("License Saved");
        JTextField licenseSavedStatusTextField = new JTextField();
        licenseSavedStatusTextField.setEditable(false);

        JLabel keysLoadedStatusLabel = new JLabel("Keys Loaded");
        JTextField keysLoadedStatusTextField = new JTextField();
        keysLoadedStatusTextField.setEditable(false);

        JLabel keysSavedStatusLabel = new JLabel("Keys Saved");
        JTextField keysSavedStatusTextField = new JTextField();
        keysSavedStatusTextField.setEditable(false);

        // add them to the panel
        add(licenseSignedStatusLabel, "cell 0 0 1 1, grow");
        add(licenseSignedStatusTextField, "cell 1 0 1 1, grow");

        add(licenseSavedStatusLabel, "cell 0 1 1 1, grow");
        add(licenseSavedStatusTextField, "cell 1 1 1 1, grow");

        add(keysLoadedStatusLabel, "cell 0 2 1 1, grow");
        add(keysLoadedStatusTextField, "cell 1 2 1 1, grow");

        add(keysSavedStatusLabel, "cell 0 3 1 1, grow");
        add(keysSavedStatusTextField, "cell 1 3 1 1, grow");

        // add action listeners
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
