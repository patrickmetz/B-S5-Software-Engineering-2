package org.hbrs.se2.project.hellocar.dtos.impl.registration;

import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.registration.JobPortalUserDTO;

public class JobPortalUserDTOImpl extends UserDTOImpl implements JobPortalUserDTO {

    private String gender;
    private String street;
    private String streetNumber;
    private String zipCode;
    private String city;

    public void setGender(String gender) { this.gender = gender; }
    public void setStreet(String street) { this.street = street; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setCity(String city) { this.city = city; }

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
}

