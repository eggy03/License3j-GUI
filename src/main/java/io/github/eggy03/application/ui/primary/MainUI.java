package io.github.eggy03.application.ui.primary;

import app.ui.primary.App;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MainUI extends JFrame {

    @NonNull
    private static final Rectangle INIT_RES = new Rectangle(100, 100, 500, 1000);

    public MainUI() {

        setTitle("License3j-GUI");
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/icons/logo.png")));
        setBounds(INIT_RES);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new MigLayout("insets 0", "[][][]", "[][grow][]"));

        contentPane.add(menuPanel(), "cell 0 0 3 1, grow"); // cell column row width height
        contentPane.add(licensePanel(), "cell 0 1, grow");
        contentPane.add(featurePanel(), "cell 1 1, grow");
        contentPane.add(keyPanel(), "cell 2 1, grow");
        contentPane.add(statusPanel(), "cell 0 2 3 1, grow");
        setContentPane(contentPane);
    }

    private @NonNull JPanel menuPanel() {
        JPanel menuPanel = new JPanel();
        return menuPanel;
    }

    private @NonNull JPanel licensePanel() {
        JPanel licensePanel = new JPanel();
        return licensePanel;
    }

    private @NonNull JPanel featurePanel() {
        JPanel featurePanel = new JPanel();
        return featurePanel;
    }

    private @NonNull JPanel keyPanel() {
        JPanel keyPanel = new JPanel();
        return keyPanel;
    }

    private @NonNull JPanel statusPanel() {
        JPanel statusPanel = new JPanel();
        return statusPanel;
    }
}
