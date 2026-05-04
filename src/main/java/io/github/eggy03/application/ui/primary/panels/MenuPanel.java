package io.github.eggy03.application.ui.primary.panels;

import io.github.eggy03.application.ui.secondary.AboutUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

@SuppressWarnings("java:S1192")
public class MenuPanel extends JPanel {

    // Non-Injectable UI Components
    // define menu bar
    private final JMenuBar menuBar = new JMenuBar();

    // define menus
    private final JMenu helpMenu = new JMenu("Help");

    // define menu items
    private final JMenuItem aboutMenuItem = new JMenuItem("About");

    public MenuPanel initUI() {
        setLayout(new GridLayout(0, 1, 0, 0));
        return this;
    }

    public MenuPanel initComponents() {
        // order: menu items -> menus -> menu bar -> panel
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        add(menuBar);

        return this;
    }

    public MenuPanel initListeners() {
        aboutMenuItem.addActionListener(_ -> new AboutUI().setVisible(true));
        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
