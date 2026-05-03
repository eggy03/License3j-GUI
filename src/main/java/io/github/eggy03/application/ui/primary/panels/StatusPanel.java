package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("java:S1192")
public class StatusPanel extends JPanel {

    final JLabel licenseLoadedStatusLabel = new JLabel("License");
    final JTextField licenseLoadedStatusTextField = new JTextField();

    final JLabel licenseSignedStatusLabel = new JLabel("License Signature");
    final JTextField licenseSignedStatusTextField = new JTextField();

    final JLabel privateKeyLoadedStatusLabel = new JLabel("Private Key");
    final JTextField privateKeyLoadedStatusTextField = new JTextField();

    final JLabel publicKeyLoadedStatusLabel = new JLabel("Public Key");
    final JTextField publicKeyLoadedStatusTextField = new JTextField();

    public StatusPanel() {
        setLayout(new MigLayout("insets 1, fillx", "[][]", "[][][][]"));
        setBorder(new TitledBorder("License Status"));
    }

    public StatusPanel addComponents() {
        add(licenseLoadedStatusLabel, "cell 0 0 1 1, grow");
        add(licenseLoadedStatusTextField, "cell 1 0 1 1, grow");

        add(licenseSignedStatusLabel, "cell 0 1 1 1, grow");
        add(licenseSignedStatusTextField, "cell 1 1 1 1, grow");

        add(privateKeyLoadedStatusLabel, "cell 0 2 1 1, grow");
        add(privateKeyLoadedStatusTextField, "cell 1 2 1 1, grow");

        add(publicKeyLoadedStatusLabel, "cell 0 3 1 1, grow");
        add(publicKeyLoadedStatusTextField, "cell 1 3 1 1, grow");

        licenseLoadedStatusTextField.setEditable(false);
        licenseSignedStatusTextField.setEditable(false);
        privateKeyLoadedStatusTextField.setEditable(false);
        publicKeyLoadedStatusTextField.setEditable(false);

        return this;
    }

    public StatusPanel registerComponentActionListeners(
            @NonNull final AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference
    ) {

        new Timer(100, _ -> {

            LicenseEntity licenseEntity = licenseEntityAtomicReference.get();
            LicenseKeyPairEntity licenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

            String licenseLoaded = licenseEntity != null && licenseEntity.hasLicense() ? "Loaded" : "Unloaded";
            String licenseSigned = licenseEntity != null && licenseEntity.hasSignature() ? "Signed" : "Unsigned";
            String privateKeyLoaded = licenseKeyPairEntity != null && licenseKeyPairEntity.hasPrivateKey() ? "Loaded" : "Unloaded";
            String publicKeyLoaded = licenseKeyPairEntity != null && licenseKeyPairEntity.hasPublicKey() ? "Loaded" : "Unloaded";

            SwingUtilities.invokeLater(() -> {
                licenseLoadedStatusTextField.setText(licenseLoaded);
                licenseSignedStatusTextField.setText(licenseSigned);
                privateKeyLoadedStatusTextField.setText(privateKeyLoaded);
                publicKeyLoadedStatusTextField.setText(publicKeyLoaded);
            });
        }).start();

        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
