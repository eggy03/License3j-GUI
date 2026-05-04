package io.github.eggy03.application.component;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Holds shared runtime instances of entities from the {@code io.github.eggy03.application.entity} package.
 *
 * <p>Entities are immutable (records), but are wrapped in {@link AtomicReference}
 * to support shared state and atomic updates.</p>
 */
public class EntityRuntimeComponent {

    private final AtomicReference<LicenseEntity> licenseEntityRef;
    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairRef;

    /**
     * Initializes {@link AtomicReference} with {@code null} initial values for
     * {@link LicenseEntity} and {@link LicenseKeyPairEntity}
     */
    public EntityRuntimeComponent() {
        this.licenseEntityRef = new AtomicReference<>();
        this.licenseKeyPairRef = new AtomicReference<>();
    }

    /**
     * @return the atomic reference holding the current {@link LicenseEntity}.
     */
    @NonNull
    public AtomicReference<LicenseEntity> licenseEntityRef() {
        return licenseEntityRef;
    }

    /**
     * @return the atomic reference holding the current {@link LicenseKeyPairEntity}.
     */
    @NonNull
    public AtomicReference<LicenseKeyPairEntity> licenseKeyPairRef() {
        return licenseKeyPairRef;
    }
}