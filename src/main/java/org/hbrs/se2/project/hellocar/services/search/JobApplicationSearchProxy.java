package org.hbrs.se2.project.hellocar.services.search;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.services.search.impl.JobAdvertisementSearchImpl;
import org.hbrs.se2.project.hellocar.services.search.impl.JobApplicationSearchImpl;

public class JobApplicationSearchProxy implements JobApplicationSearch {

    private JobApplicationSearch applicationSearch;
    private ListDataProvider<JobApplicationDTO> data;

    public JobApplicationSearchProxy(
            ListDataProvider<JobApplicationDTO> data,
            ManageUserControl manageUserControl
    ) {
        this.data = data;
        this.applicationSearch = new JobApplicationSearchImpl(data, manageUserControl);
    }

    public void setApplicantFirstName(String applicantFirstName) {
        this.applicationSearch.setApplicantFirstName(applicantFirstName);
    }

    public void setApplicantLastName(String applicantLastName) {
        this.applicationSearch.setApplicantLastName(applicantLastName);
    }

}
