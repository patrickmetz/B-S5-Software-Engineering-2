package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;

public class CompanyBuilder extends JobPortalUserBuilder {
    public CompanyBuilder() {
        super(new CompanyDTOImpl());
    }

    @Override
    public CompanyBuilder buildDefaultUser() {
        super.buildDefaultUser();
        ((CompanyDTOImpl) super.user).setCompanyName("companyName");
        return this;
    }

    public CompanyBuilder buildCompanyName(String companyName) {
        ((CompanyDTOImpl) super.user).setCompanyName(companyName);
        return this;
    }

    @Override
    public CompanyDTOImpl done() {
        return (CompanyDTOImpl) user;
    }
}
