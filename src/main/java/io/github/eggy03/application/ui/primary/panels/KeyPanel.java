package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairEntityService;
import io.github.eggy03.application.ui.swingworkers.KeyPairGenerationWorker;
import io.github.eggy03.application.ui.swingworkers.KeyPairLoaderWorker;
import io.github.eggy03.application.ui.swingworkers.KeyPairSaveWorker;
import io.github.eggy03.application.ui.swingworkers.PublicKeyDigestWorker;
import javax0.license3j.io.IOFormat;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("java:S1192")
public class KeyPanel extends JPanel {

    final JLabel cipherLabel = new JLabel("Cipher");
    final JLabel sizeLabel = new JLabel("Size");

    final JComboBox<@NonNull String> cipherComboBox = new JComboBox<>(new Vector<>(List.of("RSA")));
    final JComboBox<@NonNull Integer> sizeComboBox = new JComboBox<>(new Vector<>(List.of(2048, 3072, 4096)));

    final JButton generateKeysButton = new JButton("Generate Keys");
    final JButton digestPublicKeyButton = new JButton("Digest Public Key");

    final JLabel privateKeyLabel = new JLabel("Private Key Name");
    final JTextField privateKeyTextField = new JTextField();

    final JLabel publicKeyLabel = new JLabel("Public Key Name");
    final JTextField publicKeyTextField = new JTextField();

    final JButton saveKeysButton = new JButton("Save Keys");
    final JComboBox<@NonNull IOFormat> keySaveFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY, IOFormat.BASE64)));

    final JButton loadKeysButton = new JButton("Load Keys");
    final JComboBox<@NonNull IOFormat> keyLoadFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY, IOFormat.BASE64)));

    public KeyPanel() {
        setLayout(new MigLayout("insets 1, fill", "[][]", "[][][][][][][]"));
        setBorder(new TitledBorder("Sign Keys"));
    }

    public KeyPanel addComponents() {
        add(cipherLabel, "cell 0 0 1 1, growx"); // cell column row width height grow along x-axis
        add(sizeLabel, "cell 1 0 1 1, growx");

        add(cipherComboBox, "cell 0 1 1 1, growx");
        add(sizeComboBox, "cell 1 1 1 1, growx");

        add(generateKeysButton, "cell 0 2 1 1, growx");
        add(digestPublicKeyButton, "cell 1 2 1 1, growx");

        add(privateKeyLabel, "cell 0 3 1 1, growx");
        add(privateKeyTextField, "cell 1 3 1 1, growx");

        add(publicKeyLabel, "cell 0 4 1 1, growx");
        add(publicKeyTextField, "cell 1 4 1 1, growx");

        add(saveKeysButton, "cell 0 5 1 1, growx");
        add(keySaveFormatComboBox, "cell 1 5 1 1, growx");

        add(loadKeysButton, "cell 0 6 1 1, growx");
        add(keyLoadFormatComboBox, "cell 1 6 1 1, growx");

        return this;
    }

    public KeyPanel registerComponentActionListeners(
            @NonNull final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull final LicenseKeyPairEntityService licenseKeyPairEntityService
    ) {

        generateKeysButton.addActionListener(_->
                new KeyPairGenerationWorker(
                        licenseKeyPairEntityAtomicReference,
                        licenseKeyPairEntityService,
                        String.valueOf(cipherComboBox.getSelectedItem()),
                        (Integer) Objects.requireNonNull(sizeComboBox.getSelectedItem())
                ).execute()
        );

        digestPublicKeyButton.addActionListener(_->
                new PublicKeyDigestWorker(
                        licenseKeyPairEntityAtomicReference,
                        licenseKeyPairEntityService
                ).execute()
        );

        saveKeysButton.addActionListener(_-> {
            JFileChooser keySaveFolderChooser = new JFileChooser();
            keySaveFolderChooser.setDialogTitle("Choose a directory to save your keys");
            keySaveFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            keySaveFolderChooser.setMultiSelectionEnabled(false);
            keySaveFolderChooser.setApproveButtonText("Select Directory");
            int option = keySaveFolderChooser.showOpenDialog(this);

            if(option == JFileChooser.APPROVE_OPTION) {
                new KeyPairSaveWorker(
                        licenseKeyPairEntityAtomicReference,
                        licenseKeyPairEntityService,
                        privateKeyTextField.getText(),
                        publicKeyTextField.getText(),
                        (IOFormat) Objects.requireNonNull(keySaveFormatComboBox.getSelectedItem()),
                        keySaveFolderChooser.getSelectedFile()
                ).execute();
            }
        });

        loadKeysButton.addActionListener(_-> {

            JFileChooser privateKeySelector = new JFileChooser();
            privateKeySelector.setDialogTitle("Load Private Key");
            privateKeySelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            privateKeySelector.setMultiSelectionEnabled(false);
            privateKeySelector.setApproveButtonText("Select Private Key File");

            JFileChooser publicKeySelector = new JFileChooser();
            publicKeySelector.setDialogTitle("Load Public Key");
            publicKeySelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            publicKeySelector.setMultiSelectionEnabled(false);
            publicKeySelector.setApproveButtonText("Select Public Key File");

            if (privateKeySelector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION && publicKeySelector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    new KeyPairLoaderWorker(
                            licenseKeyPairEntityAtomicReference,
                            licenseKeyPairEntityService,
                            (IOFormat) Objects.requireNonNull(keyLoadFormatComboBox.getSelectedItem()),
                            privateKeySelector.getSelectedFile(),
                            publicKeySelector.getSelectedFile()
                    ).execute();
            }

        });

        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}