package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;

public class CompanyDTOBuilder extends JobPortalUserDTOBuilder {
    public CompanyDTOBuilder() {
        super(new CompanyDTOImpl());
    }

    @Override
    public CompanyDTOBuilder buildDefaultUser() {
        super.buildDefaultUser();
        ((CompanyDTOImpl) super.user).setCompanyName("companyName");
        ((CompanyDTOImpl) super.user).setWebSite("webSite");
        return this;
    }

    public CompanyDTOBuilder buildCompanyName(String companyName) {
        ((CompanyDTOImpl) super.user).setCompanyName(companyName);
        return this;
    }

    public CompanyDTOBuilder buildWebSite(String webSite) {
        ((CompanyDTOImpl) super.user).setWebSite(webSite);
        return this;
    }


    @Override
    public CompanyDTOImpl done() {
        return (CompanyDTOImpl) user;
    }

}
