package org.hbrs.se2.project.hellocar.util.account;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;

public class AccountValidation {

    public static final String FIRST_NAME_ERROR_MESSAGE = "Firstname must contain at least 3 characters";
    public static final String LAST_NAME_ERROR_MESSAGE = "Lastname must contain at least 3 characters";
    public static final String USERNAME_ERROR_MESSAGE = "Username must contain at least 3 characters";
    public static final String USERNAME_IN_USE_ERROR_MESSAGE = "Username is already in use!";
    public static final String PASSWORD_ERROR_MESSAGE = "Password must contain at least 4 characters";
    public static final String CONFIRM_PASSWORD_ERROR_MESSAGE = "Passwords do not match";
    public static final String EMAIL_ERROR_MESSAGE = "Email address is not valid";
    public static final String EMAIL_IN_USE_ERROR_MESSAGE = "Email is already in use!";
    public static final String COMPANY_NAME_ERROR_MESSAGE = "Company name must contain at least 3 characters";


    public static boolean firstnameValidator(String fname) {
        return fname.trim().length() >= 3;
    }

    public static boolean lastnameValidator(String lname) {
        return lname.trim().length() >= 3;
    }

    public static boolean usernameValidator(String username) {
        return username.trim().length() >= 3;
    }

    public static boolean usernameAvailableValidator(
            String username,
            ManageUserControl userService
    ) {
        return userService.findUserByUserId(username) == null;
    }

    public static boolean emailAvailableValidator(
            String email,
            ManageUserControl userService
    ) {
        return userService.findUserByEmail(email) == null;
    }

    public static boolean passwordValidator(String pw) {
        return pw.length() >= 4;
    }

    public static boolean confirmPassowrdValidator(String pw1, String pw2) {
        return pw1.equals(pw2);
    }

    public static boolean companyNameValidator(String cname) {
        return cname.trim().length() >= 3;
    }


}
