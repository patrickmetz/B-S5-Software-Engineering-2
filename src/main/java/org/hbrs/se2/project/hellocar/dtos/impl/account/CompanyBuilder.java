package org.hbrs.se2.project.hellocar.dtos.impl.account;

public class CompanyBuilder extends JobPortalUserBuilder{
    public CompanyBuilder() {

        super(new CompanyDTOImpl());
    }

    @Override
    public CompanyBuilder buildDefaultUser(){
        super.buildDefaultUser();
        ((CompanyDTOImpl) super.userDTOImpl).setCompanyName("companyName");
        return this;
    }

    public CompanyBuilder buildCompanyName(String companyName){
        ((CompanyDTOImpl) super.userDTOImpl).setCompanyName(companyName);
        return this;
    }

    @Override
    public CompanyDTOImpl done(){
        return (CompanyDTOImpl)userDTOImpl;
    }
}
