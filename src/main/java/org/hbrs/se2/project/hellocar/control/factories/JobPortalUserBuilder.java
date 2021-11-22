package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;

public class JobPortalUserBuilder extends UserBuilder {

    public JobPortalUserBuilder(JobPortalUserDTOImpl jobPortalUserDTOImpl) {
        super(jobPortalUserDTOImpl);
    }

    @Override
    public JobPortalUserBuilder buildDefaultUser() {
        super.buildDefaultUser();
        ((JobPortalUserDTOImpl) user).setGender("male");
        ((JobPortalUserDTOImpl) user).setStreet("testStreet");
        ((JobPortalUserDTOImpl) user).setStreetNumber("53b");
        ((JobPortalUserDTOImpl) user).setZipCode("50823");
        ((JobPortalUserDTOImpl) user).setCity("cologne");
        return this;
    }

    public JobPortalUserBuilder buildGender(String gender) {
        ((JobPortalUserDTOImpl) user).setGender(gender);
        return this;
    }

    public JobPortalUserBuilder buildStreet(String street) {
        ((JobPortalUserDTOImpl) super.user).setStreetNumber(street);
        return this;
    }

    public JobPortalUserBuilder buildStreetNumber(String streetNumber) {
        ((JobPortalUserDTOImpl) super.user).setStreetNumber(streetNumber);
        return this;
    }

    public JobPortalUserBuilder buildZipCode(String zipCode) {
        ((JobPortalUserDTOImpl) super.user).setZipCode(zipCode);
        return this;
    }

    public JobPortalUserBuilder buildCity(String city) {
        ((JobPortalUserDTOImpl) super.user).setCity(city);
        return this;
    }

    @Override
    public JobPortalUserDTOImpl done() {
        return (JobPortalUserDTOImpl) user;
    }

}





