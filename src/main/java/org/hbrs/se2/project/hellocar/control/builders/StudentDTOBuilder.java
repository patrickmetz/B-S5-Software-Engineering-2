package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;

public class StudentDTOBuilder extends JobPortalUserDTOBuilder {
    public StudentDTOBuilder() {
        super(new StudentDTOImpl());
    }

    @Override
    public StudentDTOImpl done() {
        return (StudentDTOImpl) user;
    }

}
