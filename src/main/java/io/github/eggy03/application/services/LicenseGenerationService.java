package io.github.eggy03.application.services;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.exception.LicenseReadException;
import io.github.eggy03.application.exception.LicenseViewException;
import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.LicenseReader;
import javax0.license3j.io.LicenseWriter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@SuppressWarnings("java:S1192")
public class LicenseGenerationService {

    private static final int MAX_LICENSE_SIZE = 20 * 1024 * 1024;

    public LicenseEntity generateLicense() {
        log.info("A new license has been generated in memory");
        return new LicenseEntity(new License(), false, false);
    }

    public LicenseEntity loadLicense(@NonNull File licenseToLoad, @NonNull IOFormat licenseFormat) {

        Objects.requireNonNull(licenseToLoad, "licenseToLoad cannot be null");
        Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");

        try (LicenseReader licenseReader = new LicenseReader(licenseToLoad, MAX_LICENSE_SIZE)) {
            log.info("A new license has been loaded in memory");
            return new LicenseEntity(licenseReader.readChecking(licenseFormat), false, false);

        } catch (IOException e) {
            log.error("License read failure", e);
            throw new LicenseReadException("License read failure", e);
        }

    }

    public LicenseEntity addFeature(@NonNull LicenseEntity licenseEntity, @NonNull Feature feature) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(feature, "feature cannot be null");

        License originalLicense = Objects.requireNonNull(licenseEntity.license(), "licenseEntity.license() cannot be null");
        License copyLicense = License.Create.from(originalLicense.serialized());

        // mutation operation on copied license
        copyLicense.add(feature);
        log.info("Feature: {}={} has been added to the license", feature.name(), feature.valueString());
        return new LicenseEntity(copyLicense, false, false); // adding features should invalidate existing signature and save status
    }

    public String viewLicense(@NonNull LicenseEntity licenseEntity) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseEntity.license(), "licenseEntity.license() cannot be null");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); LicenseWriter lw = new LicenseWriter(outputStream)) {
            lw.write(licenseEntity.license(), IOFormat.STRING);
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("License view failure", e);
            throw new LicenseViewException("License view failure", e);
        }
    }

}
