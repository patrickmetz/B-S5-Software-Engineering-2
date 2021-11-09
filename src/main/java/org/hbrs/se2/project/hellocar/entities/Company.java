package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("company")
public class Company extends JobPortalUser {

    @Basic
    @Column(name = "company_name")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

}