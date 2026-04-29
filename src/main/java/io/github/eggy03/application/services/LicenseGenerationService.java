package io.github.eggy03.application.services;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.exception.LicenseReadException;
import io.github.eggy03.application.exception.LicenseSaveException;
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
        Objects.requireNonNull(licenseToLoad, "license file to be loaded cannot be null");
        Objects.requireNonNull(licenseFormat, "license format of the loaded license cannot be null");

        try (LicenseReader licenseReader = new LicenseReader(licenseToLoad, MAX_LICENSE_SIZE)) {
            log.info("A new license has been loaded in memory");
            return new LicenseEntity(licenseReader.readChecking(licenseFormat), false, false);

        } catch (IOException e) {
            log.error("License Read Failure", e);
            throw new LicenseReadException("license cannot be read", e);
        }

    }

    public LicenseEntity addFeature(@NonNull LicenseEntity licenseEntity, @NonNull Feature feature) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(feature, "feature to be added cannot be null");

        License originalLicense = Objects.requireNonNull(licenseEntity.license(), "cannot add feature because license cannot be null");
        License copyLicense = License.Create.from(originalLicense.serialized());

        // mutation operation on license
        copyLicense.add(feature);
        return new LicenseEntity(copyLicense, false, false); // adding features should invalidate existing signature and save status
    }

    public LicenseEntity saveLicense(@NonNull LicenseEntity licenseEntity, @NonNull File licenseFolder, @NonNull String licenseName, @NonNull IOFormat licenseFormat) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseFolder, "licenseFolder cannot be null");
        Objects.requireNonNull(licenseName, "licenseName cannot be null");
        Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");

        if (!licenseFolder.isDirectory())
            throw new LicenseSaveException("provided license folder is not a directory");

        if (licenseName.contains("..") || licenseName.contains("/") || licenseName.contains("\\")) {
            throw new LicenseSaveException("Invalid license name");
        }

        License originalLicense = Objects.requireNonNull(licenseEntity.license(), "license to be saved cannot be null");
        License copyLicense = License.Create.from(originalLicense.serialized());

        try (LicenseWriter licenseWriter = new LicenseWriter(new File(licenseFolder, licenseName))) {
            licenseWriter.write(copyLicense, licenseFormat);
            log.info("license saved at {}", licenseFolder.getCanonicalPath());
            return new LicenseEntity(copyLicense, licenseEntity.signed(), true);

        } catch (IOException e) {
            log.error("License Save Failure", e);
            throw new LicenseSaveException("license could not be saved", e);
        }

    }

    public String viewLicense(@NonNull LicenseEntity licenseEntity) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); LicenseWriter lw = new LicenseWriter(outputStream)) {
            lw.write(Objects.requireNonNull(licenseEntity.license(), "license to be viewed cannot be null"), IOFormat.STRING);
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("License View Error", e);
            throw new LicenseViewException("cannot view license", e);
        }
    }

}
