package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;

import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.entities.*;

public class UserFactory {
    public static JobPortalUser createUser(JobPortalUserDTO userDTO) {
        JobPortalUser user = null;

        // class hierarchy: User <|-- JobPortalUser <|-- Student/Company

        // Student parts
        if (userDTO instanceof StudentDTO) {
            user = new Student();
        }
        // Company parts
        else if (userDTO instanceof CompanyDTO) {
            user = new Company();

            ((Company) user).setCompanyName(
                    ((CompanyDTO) userDTO).getCompanyName()
            );
        }

        // JobPortalUser parts
        user.setGender(userDTO.getGender());
        user.setStreet(userDTO.getStreet());
        user.setStreetNumber(userDTO.getStreetNumber());
        user.setCity(userDTO.getCity());
        user.setZipCode(userDTO.getZipCode());

        // User parts
        user.setEmail(userDTO.getEmail());
        user.setUserid(userDTO.getUserid());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        return user;
    }

    public static StudentDTOImpl generateStudentDto() {
        StudentDTOImpl studentDTO = new StudentDTOImpl();

        studentDTO.setFirstName("generatedStudent");
        studentDTO.setLastName("generatedStudent");
        studentDTO.setUserid("generatedStudent");
        studentDTO.setEmail("generated@student.de");
        studentDTO.setPassword("generatedStudent");

        return studentDTO;
    }

    public static CompanyDTOImpl generateCompanyDto() {
        CompanyDTOImpl companyDTO = new CompanyDTOImpl();

        companyDTO.setCompanyName("generatedCompany");
        companyDTO.setFirstName("generatedCompany");
        companyDTO.setLastName("generatedCompany");
        companyDTO.setUserid("generatedCompany");
        companyDTO.setEmail("generated@company.de");
        companyDTO.setPassword("generatedCompany");

        return companyDTO;
    }
}
