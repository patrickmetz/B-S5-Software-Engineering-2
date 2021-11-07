package org.hbrs.se2.project.hellocar.dtos.impl.registration;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.CompanyDTO;

import java.time.LocalDate;
import java.util.List;

public class CompanyDTOImpl extends JobPortalUserDTOImpl implements CompanyDTO   {

    String companyName;

    public void setCompanyName(String name){ companyName = name; }

    @Override
    public String getCompanyName() {
        return companyName;
    }

}

