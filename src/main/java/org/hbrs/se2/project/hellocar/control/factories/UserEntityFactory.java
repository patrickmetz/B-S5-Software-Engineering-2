package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;

import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.entities.*;

public class UserEntityFactory {
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

        return entity;
    }

    public static StudentDTOImpl createTestStudentDTOImpl() {
        StudentDTOImpl dtoImpl = new StudentDTOImpl();

        String identifier = "test-student";

        dtoImpl.setUserid(identifier);
        dtoImpl.setPassword(identifier);
        dtoImpl.setEmail(identifier + "@test.de");
        dtoImpl.setPhone(identifier);

        dtoImpl.setGender(identifier);
        dtoImpl.setFirstName(identifier);
        dtoImpl.setLastName(identifier);

        dtoImpl.setStreet(identifier);
        dtoImpl.setStreetNumber(identifier);
        dtoImpl.setZipCode(identifier);
        dtoImpl.setCity(identifier);

        return dtoImpl;
    }

    public static CompanyDTOImpl createTestCompanyDTOimpl() {
        CompanyDTOImpl dtoImpl = new CompanyDTOImpl();

        String identifier = "test-company";

        dtoImpl.setCompanyName(identifier);
        dtoImpl.setFirstName(identifier);
        dtoImpl.setLastName(identifier);
        dtoImpl.setUserid(identifier);
        dtoImpl.setPassword(identifier);
        dtoImpl.setEmail(identifier + "@test.de");

        return dtoImpl;
    }
}
