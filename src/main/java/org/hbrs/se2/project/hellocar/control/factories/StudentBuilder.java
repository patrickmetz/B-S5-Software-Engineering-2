package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;

public class StudentBuilder extends JobPortalUserBuilder {
    public StudentBuilder() {
        super(new StudentDTOImpl());
    }

    @Override
    public StudentDTOImpl done() {
        return (StudentDTOImpl) user;
    }

}
