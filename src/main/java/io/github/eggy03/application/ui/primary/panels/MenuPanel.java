package io.github.eggy03.application.ui.primary.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import io.github.eggy03.application.ui.secondary.AboutUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * UI panel responsible for creating the menu.
 *
 * <p>Initialization follows a staged lifecycle:
 * <ul>
 *     <li>{@link #initUI()} – configures layout and UI properties</li>
 *     <li>{@link #initComponents()} – adds UI components to the panel</li>
 *     <li>{@link #initListeners()} – registers event handlers and background tasks</li>
 * </ul>
 */
@SuppressWarnings("java:S1192")
public class MenuPanel extends JPanel {

    // Non-Injectable UI Components
    // define menu bar
    private final JMenuBar menuBar = new JMenuBar();

    // define menus
    private final JMenu helpMenu = new JMenu();

    // define menu items
    private final JMenuItem aboutMenuItem = new JMenuItem();

    /**
     * Configures layout, borders, and component properties.
     *
     * @return this panel instance for chaining
     */
    public MenuPanel initUI() {
        setLayout(new GridLayout(0, 1, 0, 0));
        return this;
    }

    /**
     * Adds and arranges all UI components within the panel.
     *
     * @return this panel instance for chaining
     */
    public MenuPanel initComponents() {
        // order: menu items -> menus -> menu bar -> panel

        aboutMenuItem.setText("About");
        aboutMenuItem.setIcon(new FlatSVGIcon(MenuPanel.class.getResource("/icons/general/about.svg")));

        helpMenu.setText("Help");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        add(menuBar);

        return this;
    }

    /**
     * Registers action listeners and initializes background workers:
     *
     * @return this panel instance for chaining
     */
    public MenuPanel initListeners() {
        aboutMenuItem.addActionListener(_ -> new AboutUI().initUI().initComponents().setVisible(true));
        return this;
    }
}
