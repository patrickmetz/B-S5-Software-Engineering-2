package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.JobApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.entities.User;

public class JobApplicationDTOBuilder {

    private final JobApplicationDTOImpl application;
    byte[] b = {1,1};

    public JobApplicationDTOBuilder() {
        this.application = new JobApplicationDTOImpl();
    }

    protected void addAllAttributes() {
        application.setText("Text");
        application.setResume(b);
    }

    public JobApplicationDTOBuilder buildDefaultUser() {
        addAllAttributes();
        return this;
    }

    public JobApplicationDTOBuilder buildId(int id) {
        application.setJobApplicationId(id);
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

    public JobApplicationDTOBuilder buildUser(User user) {
        application.setId(user.getId());
        return this;
    }

    public JobApplicationDTOImpl done() {
        return application;
    }

}
