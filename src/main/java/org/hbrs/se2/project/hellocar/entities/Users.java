package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "test_coll")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "user_type", nullable = false)
    private String userType;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String role) {
        this.userType = role;
    }

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

    @Override
    public String toString() {
        return "id=" + id +
                ", userType='" + userType + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' + ", " ;
    }
}
