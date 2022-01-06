package org.hbrs.se2.project.hellocar.util.JobApplication;

public class JobApplicationValidation {
    public static final String MESSAGE_IS_MISSING_ERROR = "Message cannot be empty!";
    public static final String RESUME_IS_MISSING_ERROR = "Please upload your resume as pdf!";

    public static boolean notEmptyFieldValidator(String input) {
        return !input.trim().isEmpty();
    }
}
