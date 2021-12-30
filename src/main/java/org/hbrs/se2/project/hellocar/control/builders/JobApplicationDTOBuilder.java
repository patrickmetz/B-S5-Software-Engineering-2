package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.JobApplicationDTOImpl;

public class JobApplicationDTOBuilder {

    private final JobApplicationDTOImpl application;

    public JobApplicationDTOBuilder() {
        this.application = new JobApplicationDTOImpl();
    }

    protected void addAllAttributes() {
        application.setText("Text");
        application.setResume(null);
    }

    public JobApplicationDTOBuilder buildDefaultUser() {
        addAllAttributes();
        return this;
    }

    public JobApplicationDTOBuilder buildId(int id) {
        application.setId(id);
        return this;
    }

    public JobApplicationDTOBuilder buildJobApplicationId(int jobApplicationId) {
        application.setId(jobApplicationId);
        return this;
    }

    public JobApplicationDTOBuilder buildText(String text) {
        application.setText(text);
        return this;
    }

    public JobApplicationDTOBuilder buildResume(byte[] resume) {
        application.setResume(resume);
        return this;
    }

    public JobApplicationDTOImpl done() {
        return application;
    }

}
