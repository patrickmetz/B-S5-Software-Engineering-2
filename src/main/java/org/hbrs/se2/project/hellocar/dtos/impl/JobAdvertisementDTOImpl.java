package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;

import java.time.LocalDate;

public class JobAdvertisementDTOImpl implements JobAdvertisementDTO {

    private int jobAdvertisementId;
    private String jobTitle;
    private String jobType;
    private String description;
    private LocalDate begin;
    private String tags;

    //todo: is user id
    private int id;

    @Override
    public int getJobAdvertisementId() {
        return this.jobAdvertisementId;
    }

    public void setJobAdvertisementId(int jobAdvertisementId) { this.jobAdvertisementId = jobAdvertisementId; }

    @Override
    public String getJobTitle() { return jobTitle; }

    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    @Override
    public String getJobType() { return jobType; }

    public void setJobType(String jobType) { this.jobType = jobType; }

    @Override
    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public LocalDate getBegin() { return begin; }

    public void setBegin(LocalDate begin) { this.begin = begin; }

    @Override
    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
