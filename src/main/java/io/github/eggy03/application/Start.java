package io.github.eggy03.application;

import io.github.eggy03.application.ui.primary.MainUI;
import io.github.eggy03.application.ui.utility.GlobalUISettings;

import java.awt.EventQueue;

public class Start {

    void main() {
        EventQueue.invokeLater(()-> {
            new GlobalUISettings().enableRoundComponents().enableTabSeparators(true);
            new MainUI().setVisible(true);
        });
    }
}
