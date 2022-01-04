package org.hbrs.se2.project.hellocar.services.search.impl;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.hbrs.se2.project.hellocar.control.LoginControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.services.search.JobAdvertisementSearch;
import org.hbrs.se2.project.hellocar.services.search.JobApplicationSearch;
import org.springframework.beans.factory.annotation.Autowired;

public class JobApplicationSearchImpl implements JobApplicationSearch {

    private ManageUserControl manageUserControl;
    private ListDataProvider<JobApplicationDTO> dataProvider;
    private String applicantFirstName;
    private String applicantLastName;

    public JobApplicationSearchImpl(
            ListDataProvider<JobApplicationDTO> dataProvider,
            ManageUserControl manageUserControl
    ) {
        this.manageUserControl = manageUserControl;
        this.dataProvider = dataProvider;
        this.dataProvider.addFilter(this::isMatching);
    }

    public void setApplicantFirstName(String applicantFirstName) {
        this.applicantFirstName = applicantFirstName;
        this.dataProvider.refreshAll();
    }

    public void setApplicantLastName(String applicantLastName) {
        this.applicantLastName = applicantLastName;
        this.dataProvider.refreshAll();
    }

    private boolean isMatching(JobApplicationDTO jobApplication) {
        JobPortalUserDTO jobUser = manageUserControl.readUser(jobApplication.getId());
        boolean matchesFirstName = matches(jobUser.getFirstName(), applicantFirstName);
        boolean matchesLastName = matches(jobUser.getLastName(), applicantLastName);

        return matchesFirstName && matchesLastName;
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
