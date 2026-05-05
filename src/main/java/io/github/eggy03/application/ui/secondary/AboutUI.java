package io.github.eggy03.application.ui.secondary;

import io.github.eggy03.application.constant.VersionAndOtherInfo;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class AboutUI extends JFrame {

    public AboutUI initUI() {
        setTitle("About License3J GUI");

        setIconImage(Toolkit.getDefaultToolkit().getImage(AboutUI.class.getResource("/icons/logo.png")));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 620, 420);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(0, 0));

        return this;
    }

    public AboutUI initComponents() {

        // version panel
        JPanel versionPanel = new JPanel();
        versionPanel.setLayout(new GridLayout(1, 0, 0, 0));
        versionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JLabel appVersionLabel = new JLabel("App Version: " + VersionAndOtherInfo.APP_VERSION);
        JLabel licenseThreeJVersionLabel = new JLabel("License3J Version: " + VersionAndOtherInfo.LICENSE3J_VERSION);

        versionPanel.add(appVersionLabel);
        versionPanel.add(licenseThreeJVersionLabel);

        // description panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(1, 0, 0, 0));
        descriptionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JEditorPane descriptionEditorPane = new JEditorPane();
        descriptionEditorPane.setContentType("text/html");
        descriptionEditorPane.setText(VersionAndOtherInfo.ABOUT);
        descriptionEditorPane.setCaretPosition(0);
        descriptionEditorPane.setEditable(false);

        descriptionPanel.add(new JScrollPane(descriptionEditorPane));

        // add both the panels to main panel
        add(versionPanel, BorderLayout.NORTH);
        add(descriptionPanel, BorderLayout.CENTER);

        return this;

    }

}
