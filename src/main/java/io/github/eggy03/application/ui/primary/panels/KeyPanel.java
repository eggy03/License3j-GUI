package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.ui.component.EntityRuntimeComponent;
import io.github.eggy03.application.ui.component.ServiceRuntimeComponent;
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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * UI panel responsible for managing {@link javax0.license3j.crypto.LicenseKeyPair} used for license signing.
 *
 * <p>Dependencies are injected via {@link EntityRuntimeComponent} and
 * {@link ServiceRuntimeComponent} </p>
 *
 * <p>All feature operations are executed asynchronously using SwingWorkers
 * to avoid blocking the Event Dispatch Thread (EDT).</p>
 *
 * <p>Initialization follows a staged lifecycle:
 * <ul>
 *     <li>{@link #initUI()} – configures layout and UI properties</li>
 *     <li>{@link #initComponents()} – adds UI components to the panel</li>
 *     <li>{@link #initListeners()} – registers event handlers and background tasks</li>
 * </ul>
 */
@SuppressWarnings("java:S1192")
public class KeyPanel extends JPanel {

    // Non-Injectable UI Components
    private final JLabel cipherLabel = new JLabel("Cipher");
    private final JLabel sizeLabel = new JLabel("Size");

    private final JComboBox<@NonNull String> cipherComboBox = new JComboBox<>(new Vector<>(List.of("RSA")));
    private final JComboBox<@NonNull Integer> sizeComboBox = new JComboBox<>(new Vector<>(List.of(2048, 3072, 4096)));

    private final JButton generateKeysButton = new JButton("Generate Keys");
    private final JButton digestPublicKeyButton = new JButton("Digest Public Key");

    private final JLabel privateKeyLabel = new JLabel("Private Key Name");
    private final JTextField privateKeyTextField = new JTextField();

    private final JLabel publicKeyLabel = new JLabel("Public Key Name");
    private final JTextField publicKeyTextField = new JTextField();

    private final JButton saveKeysButton = new JButton("Save Keys");
    private final JComboBox<@NonNull IOFormat> keySaveFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY, IOFormat.BASE64)));

    private final JButton loadKeysButton = new JButton("Load Keys");
    private final JComboBox<@NonNull IOFormat> keyLoadFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY, IOFormat.BASE64)));

    // Injectable Non-UI Components
    // non-serializable
    @SuppressWarnings("java:S1948")
    private final EntityRuntimeComponent entityRuntimeComponent;

    @SuppressWarnings("java:S1948")
    private final ServiceRuntimeComponent serviceRuntimeComponent;

    public KeyPanel(@NonNull EntityRuntimeComponent entityRuntimeComponent, @NonNull ServiceRuntimeComponent serviceRuntimeComponent) {
        this.entityRuntimeComponent = Objects.requireNonNull(entityRuntimeComponent);
        this.serviceRuntimeComponent = Objects.requireNonNull(serviceRuntimeComponent);
    }

    /**
     * Configures layout, borders, and component properties.
     *
     * @return this panel instance for chaining
     */
    public KeyPanel initUI() {
        setLayout(new MigLayout("insets 1, fill", "[][]", "[][][][][][][]"));
        setBorder(new TitledBorder("Sign Keys"));

        return this;
    }

    /**
     * Adds and arranges all UI components within the panel.
     *
     * @return this panel instance for chaining
     */
    public KeyPanel initComponents() {
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

    /**
     * Registers action listeners and initializes background workers:
     *
     * @return this panel instance for chaining
     */
    public KeyPanel initListeners() {

        generateKeysButton.addActionListener(actionEvent ->
                new KeyPairGenerationWorker(
                        entityRuntimeComponent.licenseKeyPairRef(),
                        serviceRuntimeComponent.licenseKeyPairEntityServiceRef(),
                        String.valueOf(cipherComboBox.getSelectedItem()),
                        (Integer) Objects.requireNonNull(sizeComboBox.getSelectedItem())
                ).execute()
        );

        digestPublicKeyButton.addActionListener(actionEvent ->
                new PublicKeyDigestWorker(
                        entityRuntimeComponent.licenseKeyPairRef(),
                        serviceRuntimeComponent.licenseKeyPairEntityServiceRef()
                ).execute()
        );

        saveKeysButton.addActionListener(actionEvent -> {
            JFileChooser keySaveFolderChooser = new JFileChooser();
            keySaveFolderChooser.setDialogTitle("Choose a directory to save your keys");
            keySaveFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            keySaveFolderChooser.setMultiSelectionEnabled(false);
            keySaveFolderChooser.setApproveButtonText("Select Directory");
            int option = keySaveFolderChooser.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                new KeyPairSaveWorker(
                        entityRuntimeComponent.licenseKeyPairRef(),
                        serviceRuntimeComponent.licenseKeyPairEntityServiceRef(),
                        privateKeyTextField.getText(),
                        publicKeyTextField.getText(),
                        (IOFormat) Objects.requireNonNull(keySaveFormatComboBox.getSelectedItem()),
                        keySaveFolderChooser.getSelectedFile()
                ).execute();
            }
        });

        loadKeysButton.addActionListener(actionEvent -> {

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
                        entityRuntimeComponent.licenseKeyPairRef(),
                        serviceRuntimeComponent.licenseKeyPairEntityServiceRef(),
                        (IOFormat) Objects.requireNonNull(keyLoadFormatComboBox.getSelectedItem()),
                        privateKeySelector.getSelectedFile(),
                        publicKeySelector.getSelectedFile()
                ).execute();
            }

        });

        return this;
    }
}