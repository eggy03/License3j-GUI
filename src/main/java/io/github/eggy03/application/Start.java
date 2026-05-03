package io.github.eggy03.application;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import io.github.eggy03.application.services.LicenseKeyPairEntityService;
import io.github.eggy03.application.ui.primary.MainUI;
import io.github.eggy03.application.ui.utility.GlobalUISettings;

import java.awt.EventQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Start {

    void main() {

        final AtomicReference<LicenseEntity> licenseEntityAtomicReference = new AtomicReference<>();
        final LicenseEntityService licenseEntityService = new LicenseEntityService();

        final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference = new AtomicReference<>();
        final LicenseKeyPairEntityService licenseKeyPairEntityService = new LicenseKeyPairEntityService();

        EventQueue.invokeLater(()-> {
            new GlobalUISettings().enableRoundComponents().enableTabSeparators(true);
            new MainUI(
                    licenseEntityAtomicReference, licenseEntityService,
                    licenseKeyPairEntityAtomicReference, licenseKeyPairEntityService
            ).setVisible(true);
        });
    }
}
