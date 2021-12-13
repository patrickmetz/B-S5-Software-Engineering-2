package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.JobPortalUser;

public class CompanyFactory extends AbstractJobPortalUserFactory {
    public Company create() {
        return new Company();
    }

    @Override
    public void setupEntityByDto(JobPortalUser entity, JobPortalUserDTO dto) {
        super.setupEntityByDto(entity, dto);

        ((Company) entity).setCompanyName(
                ((CompanyDTO) dto).getCompanyName()
        );

        ((Company) entity).setWebSite(
                ((CompanyDTO) dto).getWebSite()
        );
    }
}
