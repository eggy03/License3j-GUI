package io.github.eggy03.application.ui.primary.panels;

import app.ui.secondary.AboutUI;
import org.jspecify.annotations.NonNull;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

@SuppressWarnings("java:S1192")
public class MenuPanel extends JPanel {

    public MenuPanel() {
        setLayout(new GridLayout(0, 1, 0, 0));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(helpMenu());

        add(menuBar);
    }

    @NonNull
    private JMenu helpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(actionEvent -> new AboutUI());

        helpMenu.add(aboutMenuItem);

        return helpMenu;
    }

    public JScrollPane getAsScrollPane(){
        return new JScrollPane(this);
    }
}
