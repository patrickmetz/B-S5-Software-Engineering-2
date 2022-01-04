package org.hbrs.se2.project.hellocar.services.search;

import com.vaadin.flow.data.provider.ListDataProvider;

public interface JobAdvertisementSearch {

    void setJobTitle(String jobTitle);
    void setJobType(String jobType);
    void setTags(String tags);
}
