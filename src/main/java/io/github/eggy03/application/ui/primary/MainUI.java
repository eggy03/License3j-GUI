package io.github.eggy03.application.ui.primary;

import app.ui.primary.App;
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
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import java.awt.Toolkit;

@SuppressWarnings("java:S1192")
public class MainUI extends JFrame {

    @NonNull
    private static final Rectangle INIT_RES = new Rectangle(100, 100, 850, 500);

    public MainUI() {

        setTitle("License3j-GUI");
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/icons/logo.png")));
        setBounds(INIT_RES);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new MigLayout("insets 1", "[grow][grow][grow]", "[][grow][grow]"));

        contentPane.add(new MenuPanel(), "cell 0 0 3 1, grow"); // cell column row width height
        contentPane.add(new LicensePanel(), "cell 0 1, grow");
        contentPane.add(new FeaturePanel(), "cell 1 1, grow");
        contentPane.add(new KeyPanel(), "cell 2 1, grow");
        contentPane.add(new LogPanel(), "cell 0 2 2 1, grow");
        contentPane.add(new StatusPanel(), "cell 2 2 1 1, grow");

        setContentPane(contentPane);
    }
}
