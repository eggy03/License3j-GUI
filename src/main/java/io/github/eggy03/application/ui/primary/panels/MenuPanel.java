package io.github.eggy03.application.ui.primary.panels;

import app.ui.secondary.AboutUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

@SuppressWarnings("java:S1192")
public class MenuPanel extends JPanel {

    // define menu bar
    final JMenuBar menuBar = new JMenuBar();

    // define menus
    final JMenu helpMenu = new JMenu("Help");

    // define menu items
    final JMenuItem aboutMenuItem = new JMenuItem("About");

    public MenuPanel() {
        setLayout(new GridLayout(0, 1, 0, 0));
    }

    public MenuPanel addComponents() {
        // order: menu items -> menus -> menu bar -> panel
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        add(menuBar);

        return this;
    }

    public MenuPanel registerComponentActionListeners() {
        aboutMenuItem.addActionListener(_ -> new AboutUI().setVisible(true));
        return this;
    }

    public JScrollPane getAsScrollPane() {
        return new JScrollPane(this);
    }
}
