package io.github.eggy03.application.ui.component;

import io.github.eggy03.application.services.LicenseEntityService;
import io.github.eggy03.application.services.LicenseKeyPairEntityService;
import org.jspecify.annotations.NonNull;

/**
 * Holds shared runtime instances of services from the {@code io.github.eggy03.application.services} package.
 */
public class ServiceRuntimeComponent {

    private final LicenseEntityService licenseEntityService;
    private final LicenseKeyPairEntityService licenseKeyPairEntityService;

    /**
     * Initializes an instance of each of {@link LicenseEntityService} and {@link LicenseKeyPairEntityService}
     */
    public ServiceRuntimeComponent() {
        this.licenseEntityService = new LicenseEntityService();
        this.licenseKeyPairEntityService = new LicenseKeyPairEntityService();
    }

    /**
     * @return an instance of {@link LicenseEntityService}
     */
    @NonNull
    public LicenseEntityService licenseEntityServiceRef() {
        return licenseEntityService;
    }

    /**
     * @return an instance of {@link LicenseKeyPairEntityService}
     */
    @NonNull
    public LicenseKeyPairEntityService licenseKeyPairEntityServiceRef() {
        return licenseKeyPairEntityService;
    }
}
