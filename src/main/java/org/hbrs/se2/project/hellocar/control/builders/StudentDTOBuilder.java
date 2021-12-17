package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;

public class StudentDTOBuilder extends JobPortalUserDTOBuilder {

    public StudentDTOBuilder() {
        super(new StudentDTOImpl());
    }

    @Override
    public StudentDTOBuilder buildDefaultUser() {
        super.buildDefaultUser();
        ((StudentDTOImpl) user).setStudyCourse("computer science");
        ((StudentDTOImpl) user).setSpecialization("cyber security");
        ((StudentDTOImpl) user).setSemester(3);
        return this;
    }

    public StudentDTOBuilder buildStudyCourse(String studyCourse) {
        ((StudentDTOImpl) user).setStudyCourse(studyCourse);
        return this;
    }

    public StudentDTOBuilder buildSpecialization(String specialization) {
        ((StudentDTOImpl) user).setSpecialization(specialization);
        return this;
    }

    public StudentDTOBuilder buildSemester(Integer semester) {
        ((StudentDTOImpl) user).setSemester(semester);
        return this;
    }

    public StudentDTOBuilder buildDegree(String degree) {
        ((StudentDTOImpl) user).setDegree(degree);
        return this;
    }

    @Override
    public StudentDTOImpl done() {
        return (StudentDTOImpl) user;
    }

}
