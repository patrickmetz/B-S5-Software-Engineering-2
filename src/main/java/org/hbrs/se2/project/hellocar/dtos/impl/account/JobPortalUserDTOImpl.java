package org.hbrs.se2.project.hellocar.dtos.impl.account;

import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;

public class JobPortalUserDTOImpl extends UserDTOImpl implements JobPortalUserDTO {

    private String gender;
    private String street;
    private String streetNumber;
    private String zipCode;
    private String city;
    private byte[] profilePicture;
    private String about;

    public void setGender(String gender) { this.gender = gender; }
    public void setStreet(String street) { this.street = street; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setCity(String city) { this.city = city; }
    public void setProfilePicture(byte[] profilePicture) { this.profilePicture = profilePicture; }
    public void setAbout(String about) { this.about = about; }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getStreetNumber() {
        return streetNumber;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public byte[] getProfilePicture() {
        return profilePicture;
    }

    @Override
    public String getAbout() {
        return about;
    }
}

