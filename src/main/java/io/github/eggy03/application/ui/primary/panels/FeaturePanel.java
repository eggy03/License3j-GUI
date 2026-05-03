package io.github.eggy03.application.ui.primary.panels;

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
public class FeaturePanel extends JPanel {

    // define and initialize the components that will appear in the panel
    final JLabel featureNameLabel = new JLabel("F.Name");
    final JTextField featureNameTextField = new JTextField();

    final JLabel featureTypeLabel = new JLabel("F.Type");
    final JComboBox<String> featureTypeComboBox = new JComboBox<>(new Vector<>(List.of("STRING", "BINARY", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BIGINTEGER", "BIGDECIMAL", "DATE", "UUID")));

    final JLabel featureValueLabel = new JLabel("F.Value");
    final JTextField featureValueTextField = new JTextField();

    final JButton addFeatureButton = new JButton("Add Feature");

    final JLabel machineIdLabel = new JLabel("MachineID");
    final JTextField machineIdTextField = new JTextField("Set this later and make it un-editable");
    final JButton addMachineIdButton = new JButton("Add MachineID to License");

    // constructor for defining layout and metadata
    public FeaturePanel() {
        setLayout(new MigLayout("insets 1, fill", "[][]", "[][][][][][]"));
        setBorder(new TitledBorder("License Features"));
    }

    // lay them out under the defined layout manager and add them to the panel
    public FeaturePanel addComponents() {

        add(featureNameLabel, "cell 0 0 1 1, growx"); // cell column row width height grow along x-axis
        add(featureNameTextField, "cell 1 0 2 1, growx");
        add(featureTypeLabel, "cell 0 1 1 1, growx");
        add(featureTypeComboBox, "cell 1 1 2 1, growx");
        add(featureValueLabel, "cell 0 2 1 1, growx");
        add(featureValueTextField, "cell 1 2 2 1, growx");
        add(addFeatureButton, "cell 0 3 3 1, growx");
        add(machineIdLabel, "cell 0 4 1 1, growx");
        add(machineIdTextField, "cell 1 4 2 1, growx");
        add(addMachineIdButton, "cell 0 5 3 1, growx");

        return this;
    }

    public FeaturePanel registerComponentActionListeners() {
        // todo
        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
