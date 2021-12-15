package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("company")
public class Company extends JobPortalUser {

    @Basic
    @Column(name = "company_name")
    private String companyName;

    @Basic
    @Column(name = "website")
    private String webSite;

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }
}
