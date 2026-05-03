package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import javax0.license3j.io.IOFormat;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class LicenseLoadWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseLoadWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final LicenseEntityService service;
    private final IOFormat licenseFormat;
    private final File licenseFile;

    public LicenseLoadWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull LicenseEntityService service,
            @NonNull IOFormat licenseFormat,
            @NonNull File licenseFile
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.licenseFormat = Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");
        this.licenseFile = Objects.requireNonNull(licenseFile, "licenseFile cannot be null");
    }

    @Override
    protected String doInBackground() {
        LicenseEntity newLicenseEntity = service.loadLicense(licenseFile, licenseFormat);
        licenseEntityAtomicReference.set(newLicenseEntity);
        return "An existing license has been loaded in memory";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License load interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License load failure", e.getCause());
        }
    }
}
