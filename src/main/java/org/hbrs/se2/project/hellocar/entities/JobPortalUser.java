package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class JobPortalUser extends User {

    @Basic
    @Column(name = "gender")
    protected String gender;

    @Basic
    @Column(name = "street")
    private String street;

    @Basic
    @Column(name = "street_number")
    private String streetNumber;

    @Basic
    @Column(name = "zip_code")
    private String zipCode;

    @Basic
    @Column(name = "city")
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}
}
