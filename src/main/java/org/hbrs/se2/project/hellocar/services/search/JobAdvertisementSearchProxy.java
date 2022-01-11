package org.hbrs.se2.project.hellocar.services.search;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.services.search.impl.JobAdvertisementSearchImpl;

public class JobAdvertisementSearchProxy implements JobAdvertisementSearch {

    private JobAdvertisementSearch advertisementSearch;
    private ListDataProvider<JobAdvertisementDTO> data;

    public JobAdvertisementSearchProxy(
            ListDataProvider<JobAdvertisementDTO> data,
            ManageUserControl manageUserControl
    ) {
        this.data = data;
        this.advertisementSearch = new JobAdvertisementSearchImpl(data, manageUserControl);
    }

    public void setJobTitle(String jobTitle) {
        this.advertisementSearch.setJobTitle(jobTitle);
    }

    public void setJobType(String jobType) {
        this.advertisementSearch.setJobType(jobType);
    }

    public void setTags(String tags) {
        this.advertisementSearch.setTags(tags);
    }

    public void setCompany(String company) { this.advertisementSearch.setCompany(company); }

}
