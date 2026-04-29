package io.github.eggy03.application.enums;

import javax0.license3j.License;

public enum LicenseState {

    /**
     * The default state. This state should only be used when a {@link LicenseState}
     * instance is generated for the first time.
     */
    UNINITIALIZED,

    /**
     * This state is used when a new {@link License} is generated in memory
     */
    NEW,

    /**
     * This state is used when an existing {@link License} is loaded in memory
     */
    LOADED,

    /**
     * This state is used when the {@link License} in memory has been modified and should be signed before saving
     */
    MODIFIED_REQUIRES_SIGNING,

    /**
     * This state is used when a loaded license is signed by a {@link java.security.PrivateKey}
     */
    SIGNED,

    /**
     * This state is used when a signed license is saved to file
     */
    SAVED;
}
