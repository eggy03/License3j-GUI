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

    public StatusPanel(
            @NonNull final AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference
    ) {
        setLayout(new MigLayout("insets 1, fillx", "[][]", "[][][][]"));
        setBorder(new TitledBorder("License Status"));

        // define components
        JLabel licenseLoadedStatusLabel = new JLabel("License");
        JTextField licenseLoadedStatusTextField = new JTextField();
        licenseLoadedStatusTextField.setEditable(false);

        JLabel licenseSignedStatusLabel = new JLabel("License Signature");
        JTextField licenseSignedStatusTextField = new JTextField();
        licenseSignedStatusTextField.setEditable(false);

        JLabel privateKeyLoadedStatusLabel = new JLabel("Private Key");
        JTextField privateKeyLoadedStatusTextField = new JTextField();
        privateKeyLoadedStatusTextField.setEditable(false);

        JLabel publicKeyLoadedStatusLabel = new JLabel("Public Key");
        JTextField publicKeyLoadedStatusTextField = new JTextField();
        publicKeyLoadedStatusTextField.setEditable(false);

        // add them to the panel
        add(licenseLoadedStatusLabel, "cell 0 0 1 1, grow");
        add(licenseLoadedStatusTextField, "cell 1 0 1 1, grow");

        add(licenseSignedStatusLabel, "cell 0 1 1 1, grow");
        add(licenseSignedStatusTextField, "cell 1 1 1 1, grow");

        add(privateKeyLoadedStatusLabel, "cell 0 2 1 1, grow");
        add(privateKeyLoadedStatusTextField, "cell 1 2 1 1, grow");

        add(publicKeyLoadedStatusLabel, "cell 0 3 1 1, grow");
        add(publicKeyLoadedStatusTextField, "cell 1 3 1 1, grow");

        // add action listeners
        statusFieldUpdater(
                licenseEntityAtomicReference,
                licenseKeyPairEntityAtomicReference,
                licenseLoadedStatusTextField,
                licenseSignedStatusTextField,
                privateKeyLoadedStatusTextField,
                publicKeyLoadedStatusTextField
        );
    }

    private void statusFieldUpdater(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull JTextField licenseLoadedStatusTextField,
            @NonNull JTextField licenseSignedStatusTextField,
            @NonNull JTextField privateKeyLoadedStatusTextField,
            @NonNull JTextField publicKeyLoadedStatusTextField
    ) {

        new Timer(100, _ -> {

            LicenseEntity licenseEntity = licenseEntityAtomicReference.get();
            LicenseKeyPairEntity licenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

            String licenseLoaded = licenseEntity!=null && licenseEntity.hasLicense() ? "Loaded" : "Unloaded";
            String licenseSigned = licenseEntity!=null && licenseEntity.hasSignature() ? "Signed" : "Unsigned";
            String privateKeyLoaded = licenseKeyPairEntity!=null && licenseKeyPairEntity.hasPrivateKey() ? "Loaded" : "Unloaded";
            String publicKeyLoaded = licenseKeyPairEntity!=null && licenseKeyPairEntity.hasPublicKey() ? "Loaded" : "Unloaded";

            SwingUtilities.invokeLater(()-> {
                licenseLoadedStatusTextField.setText(licenseLoaded);
                licenseSignedStatusTextField.setText(licenseSigned);
                privateKeyLoadedStatusTextField.setText(privateKeyLoaded);
                publicKeyLoadedStatusTextField.setText(publicKeyLoaded);
            });
        }).start();
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
