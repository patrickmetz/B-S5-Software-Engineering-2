package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;

import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.entities.*;

public class UserEntityFactory {
    private UserEntityFactory(){

    }

    public static JobPortalUser create(JobPortalUserDTO dto) {
        JobPortalUser entity = null;

        // class hierarchy: User <|-- JobPortalUser <|-- Student/Company

        // Student parts
        if (dto instanceof StudentDTO) {
            entity = new Student();
        }

        // Company parts
        else if (dto instanceof CompanyDTO) {
            entity = new Company();

            ((Company) entity).setCompanyName(
                    ((CompanyDTO) dto).getCompanyName()
            );
        }

        if (entity != null) {
            // JobPortalUser parts
            entity.setGender(dto.getGender());
            entity.setStreet(dto.getStreet());
            entity.setStreetNumber(dto.getStreetNumber());
            entity.setCity(dto.getCity());
            entity.setZipCode(dto.getZipCode());

            // User parts
            entity.setEmail(dto.getEmail());
            entity.setUserid(dto.getUserid());
            entity.setPassword(dto.getPassword());
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setPhone(dto.getPhone());
            entity.setDateOfBirth(dto.getDateOfBirth());
        }

        return entity;
    }
}
