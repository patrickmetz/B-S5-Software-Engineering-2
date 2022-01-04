package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.entities.JobAdvertisement;

public abstract class AbstractJobAdvertisementFactory {
    public abstract JobAdvertisement create();

    public void setupEntityByDto(JobAdvertisement entity, JobAdvertisementDTO dto) {
        entity.setJobTitle(dto.getJobTitle());
        entity.setJobType(dto.getJobType());
        entity.setDescription(dto.getDescription());
        entity.setBegin(dto.getBegin());
        entity.setTags(dto.getTags());
    }
}
