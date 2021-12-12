package org.hbrs.se2.project.hellocar.dtos.impl.account;

import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;

public class CompanyDTOImpl extends JobPortalUserDTOImpl implements CompanyDTO   {

    private String companyName;
    private String webSite;

    public void setCompanyName(String name){ companyName = name; }

    public void setWebSite(String webSite){ this.webSite = webSite; }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getWebSite() { return webSite; }
}

