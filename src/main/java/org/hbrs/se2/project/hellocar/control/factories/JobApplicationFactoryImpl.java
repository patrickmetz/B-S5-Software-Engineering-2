package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.entities.JobApplication;

public class JobApplicationFactoryImpl extends AbstractJobApplicationFactory {
    @Override
    public JobApplication create() {
        return new JobApplication();
    }
}
