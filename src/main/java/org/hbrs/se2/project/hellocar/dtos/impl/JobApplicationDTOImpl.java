package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;

public class JobApplicationDTOImpl implements JobApplicationDTO {

    private int jobApplicationId;
    private String text;
    private byte[] resume;
    private int id;

    @Override
    public int getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(int jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    @Override
    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    @Override
    public byte[] getResume() { return resume; }

    public void setResume(byte[] resume) { this.resume = resume; }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
