package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;

public class JobPortalUserDTOBuilder extends UserDTOBuilder {

    public JobPortalUserDTOBuilder(JobPortalUserDTOImpl jobPortalUserDTOImpl) {
        super(jobPortalUserDTOImpl);
    }

    @Override
    public JobPortalUserDTOBuilder buildDefaultUser() {
        super.buildDefaultUser();
        ((JobPortalUserDTOImpl) user).setGender("male");
        ((JobPortalUserDTOImpl) user).setStreet("testStreet");
        ((JobPortalUserDTOImpl) user).setStreetNumber("53b");
        ((JobPortalUserDTOImpl) user).setZipCode("50823");
        ((JobPortalUserDTOImpl) user).setCity("cologne");
        // TODO: add default avatar image
        ((JobPortalUserDTOImpl) user).setProfilePicture(null);
        ((JobPortalUserDTOImpl) user).setAbout("about me");
        return this;
    }

    public JobPortalUserDTOBuilder buildGender(String gender) {
        ((JobPortalUserDTOImpl) user).setGender(gender);
        return this;
    }

    public JobPortalUserDTOBuilder buildStreet(String street) {
        ((JobPortalUserDTOImpl) super.user).setStreet(street);
        return this;
    }

    public JobPortalUserDTOBuilder buildStreetNumber(String streetNumber) {
        ((JobPortalUserDTOImpl) super.user).setStreetNumber(streetNumber);
        return this;
    }

    public JobPortalUserDTOBuilder buildZipCode(String zipCode) {
        ((JobPortalUserDTOImpl) super.user).setZipCode(zipCode);
        return this;
    }

    public JobPortalUserDTOBuilder buildCity(String city) {
        ((JobPortalUserDTOImpl) super.user).setCity(city);
        return this;
    }

    public JobPortalUserDTOBuilder buildProfilePicture(byte[] profilePicture) {
        ((JobPortalUserDTOImpl) super.user).setProfilePicture(profilePicture);
        return this;
    }

    public JobPortalUserDTOBuilder buildAbout(String about) {
        ((JobPortalUserDTOImpl) super.user).setAbout(about);
        return this;
    }

    @Override
    public JobPortalUserDTOImpl done() {
        return (JobPortalUserDTOImpl) user;
    }

}





