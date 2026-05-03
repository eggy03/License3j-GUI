package io.github.eggy03.application.ui.primary;

import app.ui.primary.App;
import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import io.github.eggy03.application.services.LicenseKeyPairEntityService;
import io.github.eggy03.application.ui.primary.panels.FeaturePanel;
import io.github.eggy03.application.ui.primary.panels.KeyPanel;
import io.github.eggy03.application.ui.primary.panels.LicensePanel;
import io.github.eggy03.application.ui.primary.panels.LogPanel;
import io.github.eggy03.application.ui.primary.panels.MenuPanel;
import io.github.eggy03.application.ui.primary.panels.StatusPanel;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("java:S1192")
public class MainUI extends JFrame {

    public MainUI() {
        setTitle("License3j-GUI");
        setLayout(new MigLayout("insets 1", "[grow][grow][grow]", "[][grow][grow]"));
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/icons/logo.png")));
        setBounds(new Rectangle(100, 100, 1100, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public MainUI addComponents(
            @NonNull final AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull final LicenseEntityService licenseEntityService,
            @NonNull final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull final LicenseKeyPairEntityService licenseKeyPairEntityService
    ) {

        JScrollPane menuPane = new MenuPanel().addComponents().registerComponentActionListeners().getAsScrollPane();
        JScrollPane licensePane = new LicensePanel().addComponents().registerComponentActionListeners().getAsScrollPane();

        JScrollPane featurePane = new FeaturePanel()
                .addComponents()
                .registerComponentActionListeners(licenseEntityAtomicReference, licenseEntityService)
                .getAsScrollPane();

        JScrollPane keyPane = new KeyPanel().addComponents().registerComponentActionListeners().getAsScrollPane();
        JScrollPane logPane = new LogPanel().addComponents().registerComponentActionListeners().getAsScrollPane();

        JScrollPane statusPane = new StatusPanel()
                .addComponents()
                .registerComponentActionListeners(licenseEntityAtomicReference, licenseKeyPairEntityAtomicReference)
                .getAsScrollPane();

        add(menuPane, "cell 0 0 3 1, grow"); // cell column row width height
        add(licensePane, "cell 0 1, grow");
        add(featurePane, "cell 1 1, grow");
        add(keyPane, "cell 2 1, grow");
        add(logPane, "cell 0 2 2 1, grow");
        add(statusPane, "cell 2 2 1 1, grow");

        return this;
    }
}
