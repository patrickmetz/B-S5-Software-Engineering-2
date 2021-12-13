package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.entities.JobPortalUser;

public abstract class AbstractlUserFactory {
    public abstract JobPortalUser create();

    public void setupEntityByDto(JobPortalUser entity, JobPortalUserDTO dto) {
        // User parts
        entity.setEmail(dto.getEmail());
        entity.setUserid(dto.getUserid());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setDateOfBirth(dto.getDateOfBirth());
    }
}
