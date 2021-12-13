package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.entities.JobPortalUser;

public abstract class AbstractJobPortalUserFactory {
    public abstract JobPortalUser create();

    public void setupEntityByDto(JobPortalUser entity, JobPortalUserDTO dto) {
        // JobPortalUser parts
        entity.setGender(dto.getGender());
        entity.setStreet(dto.getStreet());
        entity.setStreetNumber(dto.getStreetNumber());
        entity.setCity(dto.getCity());
        entity.setZipCode(dto.getZipCode());
        entity.setProfilePicture(dto.getProfilePicture());
        entity.setAbout(dto.getAbout());

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
