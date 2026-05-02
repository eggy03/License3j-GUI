package io.github.eggy03.application.ui;

import io.github.eggy03.application.ui.primary.MainUI;
import io.github.eggy03.application.ui.utility.GlobalUISettings;

import java.awt.EventQueue;

public class Start {

    void main() {

        new GlobalUISettings("com.formdev.flatlaf.themes.FlatMacDarkLaf")
                .enableRoundComponents()
                .enableTabSeparators(true);

        EventQueue.invokeLater(()-> new MainUI().setVisible(true));
    }
}
