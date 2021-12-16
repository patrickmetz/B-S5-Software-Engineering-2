package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;

import java.time.LocalDate;

public class JobAdvertisementDTOBuilder {

    private JobAdvertisementDTOImpl advertisement;

    public JobAdvertisementDTOBuilder(JobAdvertisementDTOImpl advertisement){ this.advertisement = advertisement;}

    protected void addAllAttributes() {

        advertisement.setJobTitle("JobTitle");
        advertisement.setJobType("JobType");
        advertisement.setDescription("Description");
        advertisement.setBegin(LocalDate.of(1989, 10, 3));
        advertisement.setTags("Tags");
    }

    public JobAdvertisementDTOBuilder buildDefaultUser() {
        addAllAttributes();
        return this;
    }

    public JobAdvertisementDTOBuilder buildJobTitle(String jobTitle){
        advertisement.setJobTitle(jobTitle);
        return this;
    }

    public JobAdvertisementDTOBuilder buildJobType(String jobType){
        advertisement.setJobType(jobType);
        return this;
    }

    public JobAdvertisementDTOBuilder buildDescription(String description){
        advertisement.setDescription(description);
        return this;
    }


    public JobAdvertisementDTOBuilder buildBegin(LocalDate begin){
        advertisement.setBegin(begin);
        return this;
    }

    public JobAdvertisementDTOBuilder buildTag(String tag){
        advertisement.setTags(tag);
        return this;
    }


    public JobAdvertisementDTOImpl done(){
        return advertisement;
    }
}
