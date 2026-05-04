package io.github.eggy03.application.ui.secondary;

import io.github.eggy03.application.constant.VersionAndOtherInfo;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class AboutUI extends JFrame {

    public AboutUI() {
        setTitle("About License3J GUI");

        setIconImage(Toolkit.getDefaultToolkit().getImage(AboutUI.class.getResource("/icons/logo.png")));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 620, 420);

        setLayout(new BorderLayout(0, 0));

        JPanel aboutPanel = new JPanel();
        add(aboutPanel, BorderLayout.CENTER);

        aboutPanel.setBorder(new TitledBorder("About"));
        aboutPanel.setLayout(new BorderLayout(0, 0));

        JPanel versionPanel = new JPanel();
        aboutPanel.add(versionPanel, BorderLayout.NORTH);
        versionPanel.setLayout(new GridLayout(1, 0, 0, 0));
        versionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JLabel appVersionLabel = new JLabel("App Version: " + VersionAndOtherInfo.APP_VERSION);
        versionPanel.add(appVersionLabel);

        JLabel licenseThreeJVersionLabel = new JLabel("License3J Version: " + VersionAndOtherInfo.LICENSE3J_VERSION);
        versionPanel.add(licenseThreeJVersionLabel);

        JPanel descriptionPanel = new JPanel();
        aboutPanel.add(descriptionPanel, BorderLayout.CENTER);
        descriptionPanel.setLayout(new GridLayout(1, 0, 0, 0));
        descriptionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JScrollPane scrollPane = new JScrollPane();
        descriptionPanel.add(scrollPane);

        JEditorPane descriptionPane = new JEditorPane();
        scrollPane.setViewportView(descriptionPane);
        descriptionPane.setContentType("text/html");
        descriptionPane.setText(VersionAndOtherInfo.ABOUT);
        descriptionPane.setCaretPosition(0);
        descriptionPane.setEditable(false);
    }

}
