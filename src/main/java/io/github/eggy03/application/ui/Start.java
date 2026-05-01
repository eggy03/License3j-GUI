package io.github.eggy03.application.ui;

import io.github.eggy03.application.ui.primary.MainUI;
import io.github.eggy03.application.ui.utility.UIManagerConfigurations;

import java.awt.EventQueue;

public class Start {

    void main() {
        new UIManagerConfigurations()
                .enableRoundComponents()
                .enableTabSeparators(true)
                .setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");

        EventQueue.invokeLater(()-> new MainUI().setVisible(true));
    }
}
