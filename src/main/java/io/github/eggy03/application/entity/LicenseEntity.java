package io.github.eggy03.application.entity;

import javax0.license3j.License;
import org.jspecify.annotations.NonNull;

public record LicenseEntity(@NonNull License license, boolean signed, boolean saved) {

}
