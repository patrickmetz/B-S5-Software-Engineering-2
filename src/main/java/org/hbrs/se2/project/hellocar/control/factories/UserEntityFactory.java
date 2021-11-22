package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;

import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.entities.*;

public class UserEntityFactory {
    private UserEntityFactory() {
        // factories have no usable constructors
    }

    public static JobPortalUser createUser(JobPortalUserDTO dto) {
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

    public static void addUserParts(JobPortalUser entity, JobPortalUserDTO dto) {
        entity.setUserid(dto.getUserid()); // it's the username, not the primary key
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());

        entity.setGender(dto.getGender());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());

        entity.setStreet(dto.getStreet());
        entity.setStreetNumber(dto.getStreetNumber());
        entity.setZipCode(dto.getZipCode());
        entity.setCity(dto.getCity());

        entity.setPhone(dto.getPhone());
        entity.setDateOfBirth(dto.getDateOfBirth());
    }

    public static void addCompanyParts(Company entity, CompanyDTO dto) {
        entity.setCompanyName(dto.getCompanyName());
    }

    public static void addStudentParts(Student entity, StudentDTO dto) {
        // there are no student specific entity attributes, yet
    }
}
