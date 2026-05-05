package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.ui.component.EntityRuntimeComponent;
import io.github.eggy03.application.ui.component.ServiceRuntimeComponent;
import io.github.eggy03.application.ui.swingworkers.LicenseFeatureAdditionWorker;
import io.github.eggy03.application.ui.swingworkers.MachineIDWorker;
import javax0.license3j.Feature;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * UI panel responsible for creating and adding features to a {@link javax0.license3j.License}
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
public class FeaturePanel extends JPanel {

    // Non-Injectable UI Components
    // define and initialize the components that will appear in the panel
    private final JLabel featureNameLabel = new JLabel("F.Name");
    private final JTextField featureNameTextField = new JTextField();

    private final JLabel featureTypeLabel = new JLabel("F.Type");
    private final JComboBox<String> featureTypeComboBox = new JComboBox<>(new Vector<>(List.of("STRING", "BINARY", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BIGINTEGER", "BIGDECIMAL", "DATE", "UUID")));

    private final JLabel featureValueLabel = new JLabel("F.Value");
    private final JTextField featureValueTextField = new JTextField();

    private final JButton addFeatureButton = new JButton("Add Feature");

    private final JLabel machineIdLabel = new JLabel("MachineID");
    private final JTextField machineIdTextField = new JTextField();
    private final JButton addMachineIdButton = new JButton("Add MachineID to License");

    // Injectable Non-UI Components
    // non-serializable
    @SuppressWarnings("java:S1948")
    private final EntityRuntimeComponent entityRuntimeComponent;
    @SuppressWarnings("java:S1948")
    private final ServiceRuntimeComponent serviceRuntimeComponent;

    public FeaturePanel(@NonNull EntityRuntimeComponent entityRuntimeComponent, @NonNull ServiceRuntimeComponent serviceRuntimeComponent) {
        this.entityRuntimeComponent = Objects.requireNonNull(entityRuntimeComponent);
        this.serviceRuntimeComponent = Objects.requireNonNull(serviceRuntimeComponent);
    }

    /**
     * Configures layout, borders, and component properties.
     *
     * @return this panel instance for chaining
     */
    public FeaturePanel initUI() {
        setLayout(new MigLayout("insets 1, fill", "[][]", "[][][][][][]"));
        setBorder(new TitledBorder("License Features"));

        return this;
    }

    /**
     * Adds and arranges all UI components within the panel.
     *
     * @return this panel instance for chaining
     */
    public FeaturePanel initComponents() {

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

    /**
     * Registers action listeners and initializes background workers:
     *
     * @return this panel instance for chaining
     */
    public FeaturePanel initListeners() {

        // fetch machineID
        new MachineIDWorker(machineIdTextField).execute();

        // addFeatureButton action listener
        addFeatureButton.addActionListener(_ -> {

            String featureName = featureNameTextField.getText();
            String featureType = String.valueOf(featureTypeComboBox.getSelectedItem());
            String featureValue = featureValueTextField.getText();

            Feature feature = Feature.Create.from(featureName + ":" + featureType + "=" + featureValue);
            new LicenseFeatureAdditionWorker(
                    entityRuntimeComponent.licenseEntityRef(),
                    serviceRuntimeComponent.licenseEntityServiceRef(),
                    feature
            ).execute();
        });

        // addMachineIdButton action listener
        addMachineIdButton.addActionListener(_ -> {
            Feature feature = Feature.Create.from("licenseId:UUID=" + machineIdTextField.getText());
            new LicenseFeatureAdditionWorker(
                    entityRuntimeComponent.licenseEntityRef(),
                    serviceRuntimeComponent.licenseEntityServiceRef(),
                    feature
            ).execute();
        });

        return this;
    }
}
