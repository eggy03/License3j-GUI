package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import io.github.eggy03.application.ui.swingworkers.LicenseGenerationWorker;
import io.github.eggy03.application.ui.swingworkers.LicenseLoadWorker;
import io.github.eggy03.application.ui.swingworkers.LicenseSaveWorker;
import io.github.eggy03.application.ui.swingworkers.LicenseSignWorker;
import io.github.eggy03.application.ui.swingworkers.LicenseVerifyWorker;
import io.github.eggy03.application.ui.swingworkers.LicenseViewWorker;
import javax0.license3j.io.IOFormat;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("java:S1192")
public class LicensePanel extends JPanel {

    final JButton newLicenseButton = new JButton("New License");

    final JButton loadLicenseButton = new JButton("Load License");
    final JComboBox<IOFormat> licenseLoadFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY, IOFormat.BASE64, IOFormat.STRING)));

    final JButton displayLicenseButton = new JButton("Display License");

    final JButton signLicenseButton = new JButton("Sign License");
    final JComboBox<String> signatureDigestComboBox = new JComboBox<>(new Vector<>(List.of("SHA-512")));

    final JButton verifyLicenseButton = new JButton("Verify License");

    final JButton saveLicenseButton = new JButton("Save License");
    final JComboBox<IOFormat> licenseSaveFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY, IOFormat.BASE64, IOFormat.STRING)));
    final JTextField licenseSaveNameTextField = new JTextField();

    public LicensePanel() {
        setLayout(new MigLayout("insets 1, fill", "[][][]", "[][][][][][]"));
        setBorder(new TitledBorder("License Functions"));
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

    public LicensePanel registerComponentActionListeners(
            @NonNull final AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull final LicenseEntityService licenseEntityService
    ) {

        newLicenseButton.addActionListener(_ ->
                new LicenseGenerationWorker(
                        licenseEntityAtomicReference,
                        licenseEntityService
                ).execute()
        );

        loadLicenseButton.addActionListener(_ -> {
            JFileChooser licenseFileChooser = new JFileChooser();
            licenseFileChooser.setDialogTitle("Choose a License file to load");
            licenseFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            licenseFileChooser.setMultiSelectionEnabled(false);
            licenseFileChooser.setApproveButtonText("Load License");
            int option = licenseFileChooser.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                new LicenseLoadWorker(
                        licenseEntityAtomicReference,
                        licenseEntityService,
                        Objects.requireNonNullElse((IOFormat) licenseLoadFormatComboBox.getSelectedItem(), IOFormat.BINARY),
                        licenseFileChooser.getSelectedFile()
                ).execute();
            }
        });

        displayLicenseButton.addActionListener(_ ->
                new LicenseViewWorker(
                        licenseEntityAtomicReference,
                        licenseEntityService
                ).execute()
        );

        signLicenseButton.addActionListener(_ ->
                new LicenseSignWorker(
                        licenseEntityAtomicReference,
                        licenseKeyPairEntityAtomicReference,
                        licenseEntityService,
                        Objects.requireNonNullElse((String) signatureDigestComboBox.getSelectedItem(), "SHA-512")
                ).execute()
        );

        verifyLicenseButton.addActionListener(_ ->
                new LicenseVerifyWorker(
                        licenseEntityAtomicReference,
                        licenseKeyPairEntityAtomicReference,
                        licenseEntityService
                ).execute()
        );

        saveLicenseButton.addActionListener(_ -> {
            JFileChooser licenseSaveFolderChooser = new JFileChooser();
            licenseSaveFolderChooser.setDialogTitle("Choose a directory to save your license");
            licenseSaveFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            licenseSaveFolderChooser.setMultiSelectionEnabled(false);
            licenseSaveFolderChooser.setApproveButtonText("Select Directory");
            int option = licenseSaveFolderChooser.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                new LicenseSaveWorker(
                        licenseEntityAtomicReference,
                        licenseEntityService,
                        licenseSaveNameTextField.getText(),
                        Objects.requireNonNullElse((IOFormat) licenseSaveFormatComboBox.getSelectedItem(), IOFormat.BINARY),
                        licenseSaveFolderChooser.getSelectedFile()
                ).execute();
            }
        });

        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
