package io.github.eggy03.application.ui.primary.panels;

import javax0.license3j.io.IOFormat;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("java:S1192")
public class LicensePanel extends JPanel {

    final JButton newLicenseButton = new JButton("New License");

    final JButton loadLicenseButton = new JButton("Load License");
    final JComboBox<String> licenseLoadFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY.name(), IOFormat.BASE64.name(), IOFormat.STRING.name())));

    final JButton displayLicenseButton = new JButton("Display License");

    final JButton signLicenseButton = new JButton("Sign License");
    final JComboBox<String> signatureDigestComboBox = new JComboBox<>(new Vector<>(List.of("SHA-512")));

    final JButton verifyLicenseButton = new JButton("Verify License");

    final JButton saveLicenseButton = new JButton("Save License");
    final JComboBox<String> licenseSaveFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY.name(), IOFormat.BASE64.name(), IOFormat.STRING.name())));
    final JTextField licenseSaveNameTextField = new JTextField("License Name...");

    public LicensePanel() {
        setLayout(new MigLayout("insets 1, fill", "[][][]", "[][][][][][]"));
        setBorder(new TitledBorder("License Functions"));

        // define components


        // add them to the panel


        // add action listeners to the components

    }

    public LicensePanel addComponents() {

        add(newLicenseButton, "cell 0 0 3 1, growx"); // cell column row width height grow along x-axis
        add(loadLicenseButton, "cell 0 1 2 1, growx");
        add(licenseLoadFormatComboBox, "cell 2 1 1 1, growx");
        add(displayLicenseButton, "cell 0 2 3 1, growx");
        add(signLicenseButton, "cell 0 3 1 1, growx");
        add(signatureDigestComboBox, "cell 1 3 2 1, growx");
        add(verifyLicenseButton, "cell 0 4 3 1, growx");
        add(saveLicenseButton, "cell 0 5 1 1, growx");
        add(licenseSaveFormatComboBox, "cell 1 5 1 1, growx");
        add(licenseSaveNameTextField, "cell 2 5 1 1, growx");

        return this;
    }

    public LicensePanel registerComponentActionListeners() {
        // todo
        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
