package io.github.eggy03.application.ui.primary;

import io.github.eggy03.application.ui.component.EntityRuntimeComponent;
import io.github.eggy03.application.ui.component.ServiceRuntimeComponent;
import io.github.eggy03.application.ui.primary.panels.FeaturePanel;
import io.github.eggy03.application.ui.primary.panels.KeyPanel;
import io.github.eggy03.application.ui.primary.panels.LicensePanel;
import io.github.eggy03.application.ui.primary.panels.LogPanel;
import io.github.eggy03.application.ui.primary.panels.MenuPanel;
import io.github.eggy03.application.ui.primary.panels.StatusPanel;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Objects;

/**
 * Main application window for the License3j GUI.
 *
 * <p>This class is responsible for initializing and composing all primary UI panels,
 * as well as wiring them with shared runtime state and services.</p>
 *
 * <p> {@link EntityRuntimeComponent} and {@link ServiceRuntimeComponent} contains the required License3j dependencies
 * which will need to be passed on to the SwingWorkers and other background workers. Dependencies are injected
 * via the constructor.</p>
 *
 * <p>UI initialization follows a staged approach:
 * <ul>
 *     <li>{@link #initUI()} – configures frame properties (layout, size, icon, etc.)</li>
 *     <li>{@link #initComponents()} – creates, initializes, and lays out all panels</li>
 * </ul>
 *
 * <p>Each underlying panel is responsible for its own internal setup via a fluent API</p>
 * <p>Underlying panels are wrapped in {@link JScrollPane} before being added to the {@link MainUI} panel </p>
 */
@SuppressWarnings("java:S1192")
public class MainUI extends JFrame {

    // Injectable Non-UI components
    // non-serializable
    @SuppressWarnings("java:S1948")
    private final EntityRuntimeComponent entityRuntimeComponent;

    @SuppressWarnings("java:S1948")
    private final ServiceRuntimeComponent serviceRuntimeComponent;

    public MainUI(@NonNull EntityRuntimeComponent entityRuntimeComponent, @NonNull ServiceRuntimeComponent serviceRuntimeComponent) {
        this.entityRuntimeComponent = Objects.requireNonNull(entityRuntimeComponent);
        this.serviceRuntimeComponent = Objects.requireNonNull(serviceRuntimeComponent);
    }

    /**
     * Initializes frame-level UI properties such as layout, size, icon, and close behavior.
     *
     * @return this instance for method chaining
     */
    public MainUI initUI() {
        setTitle("License3j-GUI");
        setLayout(new MigLayout("insets 1", "[grow][grow][grow]", "[][grow][grow]"));
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainUI.class.getResource("/icons/logo.png")));
        setBounds(new Rectangle(100, 100, 1100, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return this;
    }

    /**
     * Creates and initializes all primary panels, injects dependencies where required,
     * and adds them to the frame using {@link net.miginfocom.swing.MigLayout}.
     *
     * @return this instance for method chaining
     */
    public MainUI initComponents() {

        JPanel menuPane = new MenuPanel()
                .initUI()
                .initComponents()
                .initListeners();

        JPanel licensePane = new LicensePanel(entityRuntimeComponent, serviceRuntimeComponent)
                .initUI()
                .initComponents()
                .initListeners();

        JPanel featurePane = new FeaturePanel(entityRuntimeComponent, serviceRuntimeComponent)
                .initUI()
                .initComponents()
                .initListeners();

        JPanel keyPane = new KeyPanel(entityRuntimeComponent, serviceRuntimeComponent)
                .initUI()
                .initComponents()
                .initListeners();

        JPanel logPane = new LogPanel()
                .initUI()
                .initComponents()
                .initListeners();

        JPanel statusPane = new StatusPanel(entityRuntimeComponent)
                .initUI()
                .initComponents()
                .initListeners();

        add(menuPane, "cell 0 0 3 1, grow"); // cell column row width height
        add(new JScrollPane(licensePane), "cell 0 1, grow");
        add(new JScrollPane(featurePane), "cell 1 1, grow");
        add(new JScrollPane(keyPane), "cell 2 1, grow");
        add(new JScrollPane(logPane), "cell 0 2 2 1, grow");
        add(new JScrollPane(statusPane), "cell 2 2 1 1, grow");

        return this;
    }
}
