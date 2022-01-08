package org.hbrs.se2.project.hellocar.util;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String SHOW_CARS = "show";
        public static final String ENTER_CAR = "enter";

        public static final String LOGIN_VIEW = "login";
        public static final String MAIN_VIEW = "";

        public static final String REGISTER_VIEW = "register";
        public static final String REGISTER_STUDENT_VIEW = "registerstudent";
        public static final String REGISTER_COMPANY_VIEW = "registercompany";

        public static final String UPDATE_STUDENT_VIEW = "updatestudent";
        public static final String UPDATE_COMPANY_VIEW = "updatecompany";

        public static final String READONLY_STUDENT_VIEW = "viewstudent";
        public static final String READONLY_COMPANY_VIEW = "viewcompany";

        public static final String CREATE_JOB_AD = "jobadvertisement";
        public static final String JOB_AD_LIST = "jobs";
        public static final String VIEW_JOB_AD = "viewjob";
        public static final String UPDATE_JOB_AD = "updatejob";

        public static final String JOB_APPLICATION_LIST = "jobapplications";

        public static final String JOB_APPLICATION = "apply";

    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";

        public static final String STUDENT = "student";
        public static final String COMPANY = "company";
    }

    public static class Degrees {
        public static final String BACHELOR = "bachelor";
        public static final String MASTER = "master";
        public static final String DOCTORAL_STUDENT = "doctoral student";

        public static final String[] DEGREES = {"bachelor", "master", "doctoral student"};
    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
