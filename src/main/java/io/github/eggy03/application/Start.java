package io.github.eggy03.application;

import io.github.eggy03.application.ui.component.EntityRuntimeComponent;
import io.github.eggy03.application.ui.component.ServiceRuntimeComponent;
import io.github.eggy03.application.ui.primary.MainUI;
import io.github.eggy03.application.ui.utility.GlobalUISettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.EventQueue;

public class Start {

    private static final Logger log = LoggerFactory.getLogger(Start.class);

    void main() {

        try {
            EventQueue.invokeLater(() -> {
                new GlobalUISettings().enableRoundComponents().enableTabSeparators(true);
                new MainUI(new EntityRuntimeComponent(), new ServiceRuntimeComponent())
                        .initUI()
                        .initComponents()
                        .setVisible(true);
            });
        } catch (Exception e) {
            log.error("Unhandled Error: {}", e.getCause().getMessage());
            log.debug("Stack trace for unhandled error", e);
        }
    }
}
