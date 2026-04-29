package io.github.eggy03.application.services;

import io.github.eggy03.application.enums.LicenseState;
import io.github.eggy03.application.exception.LicenseFeatureException;
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
import org.jspecify.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class LicenseGenerationService {

    @Nullable
    private License license = null;

    @NonNull
    private LicenseState licenseState = LicenseState.UNINITIALIZED;

    @Nullable
    public synchronized License getLicense(){
        return license;
    }

    @NonNull
    public synchronized LicenseState getLicenseState() {
        return licenseState;
    }

    public synchronized void generateLicense() {
        license = new License();
        licenseState = LicenseState.NEW;
        log.info("A new license has been generated in memory");
    }

    public synchronized void loadLicense(@NonNull File licenseToLoad, @NonNull IOFormat licenseFormat) {
        Objects.requireNonNull(licenseToLoad, "license file to be loaded cannot be null");
        Objects.requireNonNull(licenseFormat, "license format of the loaded license cannot be null");

        try(LicenseReader licenseReader = new LicenseReader(licenseToLoad, 20971520)) {

            license = licenseReader.readChecking(licenseFormat);
            licenseState = LicenseState.LOADED;

        } catch (IOException e) {
            throw new LicenseReadException("license cannot be read", e);
        }

    }

    public synchronized void addFeature(@NonNull Feature feature) {

        Objects.requireNonNull(feature, "feature to be added cannot be null");

        if(license==null || licenseState==LicenseState.UNINITIALIZED)
            throw new LicenseFeatureException("Cannot add feature because license was either null or it's state was uninitialized");

        license.add(feature);
        licenseState = LicenseState.MODIFIED_REQUIRES_SIGNING;
    }

    public synchronized void saveLicense(@NonNull String licenseName, @NonNull String licenseSavePath, @NonNull IOFormat licenseFormat) {

        Objects.requireNonNull(licenseName, "licenseName cannot be null");
        Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");
        Objects.requireNonNull(licenseSavePath, "licenseSavePath cannot be null");

        if(license==null || licenseState==LicenseState.UNINITIALIZED)
            throw new LicenseSaveException("cannot save license because it was either null or it's state was uninitialized");

        File licenseFolder = new File(licenseSavePath);
        if(!licenseFolder.isDirectory())
            throw new LicenseSaveException("provided license save path is not a directory");

        try (LicenseWriter licenseWriter = new LicenseWriter(licenseFolder+File.separator+licenseName)) {

            licenseWriter.write(license, licenseFormat);
            licenseState=LicenseState.SAVED;

        } catch (IOException e) {
            throw new LicenseSaveException("license could not be saved", e);
        }

    }

    public synchronized String viewLicense() {
        if (license==null || licenseState==LicenseState.UNINITIALIZED)
            throw new LicenseViewException("cannot view license because it is either null or it's state is uninitialized");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); LicenseWriter lw = new LicenseWriter(outputStream)) {
            lw.write(license, IOFormat.STRING);
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new LicenseViewException("cannot view license", e);
        }
    }

}
