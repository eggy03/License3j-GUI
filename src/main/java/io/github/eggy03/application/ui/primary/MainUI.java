package io.github.eggy03.application.ui.primary;

import app.ui.primary.App;
import app.ui.secondary.AboutUI;
import net.miginfocom.swing.MigLayout;
import org.jspecify.annotations.NonNull;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

@SuppressWarnings("java:S1192")
public class MainUI extends JFrame {

    @NonNull
    private static final Rectangle INIT_RES = new Rectangle(100, 100, 500, 1000);

    public MainUI() {

        setTitle("License3j-GUI");
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/icons/logo.png")));
        setBounds(INIT_RES);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new MigLayout("insets 0", "[grow][grow][grow]", "[][grow][grow]"));

        contentPane.add(menuPanel(), "cell 0 0 3 1, grow"); // cell column row width height
        contentPane.add(licensePanel(), "cell 0 1, grow");
        contentPane.add(featurePanel(), "cell 1 1, grow");
        contentPane.add(keyPanel(), "cell 2 1, grow");
        contentPane.add(statusPanel(), "cell 0 2 3 1, grow");
        setContentPane(contentPane);
    }

    private @NonNull JPanel menuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 0, 0));

        JMenuBar menuBar = new JMenuBar();
        menuPanel.add(menuBar);

        JMenu menu = new JMenu("Help");
        menuBar.add(menu);

        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(actionEvent -> new AboutUI());
        menu.add(aboutMenuItem);

        return menuPanel;
    }

    private @NonNull JPanel licensePanel() {
        JPanel licensePanel = new JPanel(new MigLayout("insets 1", "[grow]", "[grow]"));
        licensePanel.setBorder(new TitledBorder("License Functions"));
        return licensePanel;
    }

    private @NonNull JPanel featurePanel() {
        JPanel featurePanel = new JPanel(new MigLayout("insets 1", "[grow]", "[grow]"));
        featurePanel.setBorder(new TitledBorder("Feature Panel"));
        return featurePanel;
    }

    private @NonNull JPanel keyPanel() {
        JPanel keyPanel = new JPanel(new MigLayout("insets 1", "[grow]", "[grow]"));
        keyPanel.setBorder(new TitledBorder("Key Panel"));
        return keyPanel;
    }

    private @NonNull JPanel statusPanel() {
        JPanel statusPanel = new JPanel(new MigLayout("insets 1", "[grow]", "[grow]"));
        statusPanel.setBorder(new TitledBorder("Status Panel"));
        return statusPanel;
    }
}
