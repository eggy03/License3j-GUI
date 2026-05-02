package io.github.eggy03.application.ui.primary.panels;

import javax0.license3j.io.IOFormat;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("java:S1192")
public class KeyPanel extends JPanel {

    public KeyPanel() {
        setLayout(new MigLayout("insets 1, fill", "[][]", "[][][][][][][]"));
        setBorder(new TitledBorder("Sign Keys"));

        // define components
        JLabel cipherLabel = new JLabel("Cipher");
        JLabel sizeLabel = new JLabel("Size");

        JComboBox<String> cipherComboBox = new JComboBox<>(new Vector<>(List.of("RSA/ECB/PKCS1Padding")));
        JComboBox<String> sizeComboBox = new JComboBox<>(new Vector<>(List.of("2048", "3072", "4096")));

        JButton generateKeysButton = new JButton("Generate Keys");
        JButton digestPublicKeyButton = new JButton("Digest Public Key");

        JLabel privateKeyLabel = new JLabel("Private Key Name");
        JTextField privateKeyTextField = new JTextField("private.key");

        JLabel publicKeyLabel = new JLabel("Public Key Name");
        JTextField publicKeyTextField = new JTextField("public.key");

        JButton saveKeysButton = new JButton("Save Keys");
        JComboBox<String> keySaveFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY.name(), IOFormat.BASE64.name())));

        JButton loadKeysButton = new JButton("Load Keys");
        JComboBox<String> keyLoadFormatComboBox = new JComboBox<>(new Vector<>(List.of(IOFormat.BINARY.name(), IOFormat.BASE64.name())));

        // add them to the panel
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

        // add action listeners to the components
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
