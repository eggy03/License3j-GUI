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

    public FeaturePanel() {
        setLayout(new MigLayout("insets 1, fill", "[][]", "[][][][][][]"));
        setBorder(new TitledBorder("License Features"));

        // define components
        JLabel featureNameLabel = new JLabel("F.Name");
        JTextField featureNameTextField = new JTextField();

        JLabel featureTypeLabel = new JLabel("F.Type");
        JComboBox<String> featureTypeComboBox = new JComboBox<>(new Vector<>(List.of("STRING", "BINARY", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BIGINTEGER", "BIGDECIMAL", "DATE", "UUID")));

        JLabel featureValueLabel = new JLabel("F.Value");
        JTextField featureValueTextField = new JTextField();

        JButton addFeatureButton = new JButton("Add Feature");

        JLabel machineIdLabel = new JLabel("MachineID");
        JTextField machineIdTextField = new JTextField("Set this later and make it un-editable");
        JButton addMachineIdButton = new JButton("Add MachineID to License");

        // add them to the panel
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

        // add action listeners to the components
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
