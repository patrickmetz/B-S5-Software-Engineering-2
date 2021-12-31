package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.entities.JobApplication;

public abstract class AbstractJobApplicationFactory {
    public abstract JobApplication create();

    public void setupEntityByDto(JobApplication entity, JobApplicationDTO dto) {
        entity.setResume(dto.getResume());
        entity.setText(dto.getText());
    }
}
