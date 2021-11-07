package org.hbrs.se2.project.hellocar.dtos.impl.registration;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.StudentDTO;

import java.time.LocalDate;
import java.util.List;

public class StudentDTOImpl extends JobPortalUserDTOImpl implements StudentDTO {

    LocalDate dateOfBirth;

    public void setDateOfBirth(LocalDate date){ dateOfBirth = date; }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}

