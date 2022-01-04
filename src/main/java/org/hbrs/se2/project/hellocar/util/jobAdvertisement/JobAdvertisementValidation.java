package org.hbrs.se2.project.hellocar.util.jobAdvertisement;

import java.time.LocalDate;

public class JobAdvertisementValidation {
    public static final String FIRST_NAME_ERROR_MESSAGE = "Firstname must contain at least 3 characters";
    public static final String IS_EMPTY_ERROR = "cannot be empty";

    public static boolean notEmptyFieldValidator(String input) {
        return !input.trim().isEmpty();
    }

    public static boolean notNullDateValidator(LocalDate input) {
        return input != null;
    }
}
