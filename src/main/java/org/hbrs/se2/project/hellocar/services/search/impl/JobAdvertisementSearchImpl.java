package org.hbrs.se2.project.hellocar.services.search.impl;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.services.search.JobAdvertisementSearch;

public class JobAdvertisementSearchImpl implements JobAdvertisementSearch {

    private ListDataProvider<JobAdvertisementDTO> dataProvider;
    private ManageUserControl manageUserControl;

    private String jobTitle;
    private String jobType;
    private String tags;
    private String company;

    public JobAdvertisementSearchImpl(
            ListDataProvider<JobAdvertisementDTO> dataProvider,
            ManageUserControl manageUserControl
    ) {
        this.manageUserControl = manageUserControl;
        this.dataProvider = dataProvider;
        this.dataProvider.addFilter(this::isMatching);
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        this.dataProvider.refreshAll();
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
        this.dataProvider.refreshAll();
    }

    public void setTags(String tags) {
        this.tags = tags;
        this.dataProvider.refreshAll();
    }

    public void setCompany(String company) {
        this.company = company;
        this.dataProvider.refreshAll();
    }

    private boolean isMatching(JobAdvertisementDTO jobAdvertisement) {
        JobPortalUserDTO jobUser = manageUserControl.readUser(jobAdvertisement.getId());
        String companyName = "";
        if(jobUser instanceof CompanyDTO) {
            companyName = ((CompanyDTO) jobUser).getCompanyName();
        } else {
            companyName = jobUser.getFirstName() + " " + jobUser.getLastName();
        }
        boolean matchesJobTitle = matches(jobAdvertisement.getJobTitle(), jobTitle);
        boolean matchesJobType = matches(jobAdvertisement.getJobType(), jobType);
        boolean matchesTags = matchesArray(jobAdvertisement.getTags().split(","),
                tags != null ? tags.split(",") : null);
        boolean matchesCompany = matches(companyName, company);

        return matchesJobTitle && matchesJobType && matchesTags && matchesCompany;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null
                || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private boolean matchesArray(String[] values, String[] searchTerms) {
        if(searchTerms == null || searchTerms.length <= 0) return true;
        for(String value : values) {
            for(String searchTerm : searchTerms) {
                if(matches(value, searchTerm)) return true;
            }
        }
        return false;
    }

}
