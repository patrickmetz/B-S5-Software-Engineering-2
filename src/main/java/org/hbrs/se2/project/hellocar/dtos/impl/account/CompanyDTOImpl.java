package org.hbrs.se2.project.hellocar.dtos.impl.account;

import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;

public class CompanyDTOImpl extends JobPortalUserDTOImpl implements CompanyDTO   {

    String companyName;

    public void setCompanyName(String name){ companyName = name; }

    @Override
    public String getCompanyName() {
        return companyName;
    }

}

