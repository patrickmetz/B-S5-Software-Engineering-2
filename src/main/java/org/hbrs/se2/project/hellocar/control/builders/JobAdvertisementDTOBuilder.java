package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.entities.User;

import java.time.LocalDate;

public class JobAdvertisementDTOBuilder {

    private final JobAdvertisementDTOImpl advertisement;

    public JobAdvertisementDTOBuilder() {
        this.advertisement = new JobAdvertisementDTOImpl();
    }

    protected void addAllAttributes() {

        advertisement.setJobTitle("JobTitle");
        advertisement.setJobType("JobType");
        advertisement.setDescription("Description");
        advertisement.setBegin(LocalDate.of(1989, 10, 3));
        advertisement.setTags("Tags");
    }

    public JobAdvertisementDTOBuilder buildDefaultAdvertisement() {
        addAllAttributes();
        return this;
    }

    public JobAdvertisementDTOBuilder buildId(int id) {
        advertisement.setJobAdvertisementId(id);
        return this;
    }

    public JobAdvertisementDTOBuilder buildJobTitle(String jobTitle) {
        advertisement.setJobTitle(jobTitle);
        return this;
    }

    public JobAdvertisementDTOBuilder buildJobType(String jobType) {
        advertisement.setJobType(jobType);
        return this;
    }

    public JobAdvertisementDTOBuilder buildDescription(String description) {
        advertisement.setDescription(description);
        return this;
    }


    public JobAdvertisementDTOBuilder buildBegin(LocalDate begin) {
        advertisement.setBegin(begin);
        return this;
    }

    public JobAdvertisementDTOBuilder buildTags(String tags) {
        advertisement.setTags(tags);
        return this;
    }

    public JobAdvertisementDTOBuilder buildUser(User user) {
        advertisement.setId(user.getId());
        return this;
    }


    public JobAdvertisementDTOImpl done() {
        return advertisement;
    }
}
