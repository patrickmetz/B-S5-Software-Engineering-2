package org.hbrs.se2.project.hellocar.dtos.impl.account;

import org.hbrs.se2.project.hellocar.util.Globals;

public class StudentBuilder extends JobPortalUserBuilder{
    public StudentBuilder() {
        super(new StudentDTOImpl());
    }

    @Override
    public StudentDTOImpl done(){
        return (StudentDTOImpl)userDTOImpl;
    }

}
