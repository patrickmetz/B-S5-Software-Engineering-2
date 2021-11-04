package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Company extends Users {

    @Basic
    @Column(name = "first_name_contact_person")
    private String firstNameContactPerson;

    @Basic
    @Column(name = "last_name_contact_person")
    private String lastNameContactPerson;

    @Basic
    @Column(name = "gender_contact_person")
    private String genderContactPerson;

    @Basic
    @Column(name = "company_name")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    @Override
    public String toString() {
        return "Company{" +
                super.toString() +
                " firstNameContactPerson='" + firstNameContactPerson + '\'' +
                ", lastNameContactPerson='" + lastNameContactPerson + '\'' +
                ", genderContactPerson='" + genderContactPerson + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    public String getFirstNameContactPerson() {
        return firstNameContactPerson;
    }

    public void setFirstNameContactPerson(String firstNameContactPerson) {
        this.firstNameContactPerson = firstNameContactPerson;
    }

    public String getLastNameContactPerson() {
        return lastNameContactPerson;
    }

    public void setLastNameContactPerson(String lastNameContactPerson) {
        this.lastNameContactPerson = lastNameContactPerson;
    }

    public String getGenderContactPerson() {
        return genderContactPerson;
    }

    public void setGenderContactPerson(String genderContactPerson) {
        this.genderContactPerson = genderContactPerson;
    }
}
