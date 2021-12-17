package org.hbrs.se2.project.hellocar.dtos.impl.account;

import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;

public class StudentDTOImpl extends JobPortalUserDTOImpl implements StudentDTO {

    private String studyCourse;
    private String specialization;
    private Integer semester;
    private String degree;

    public void setStudyCourse(String studyCourse) { this.studyCourse = studyCourse; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String getStudyCourse() {
        return studyCourse;
    }

    @Override
    public String getSpecialization() {
        return specialization;
    }

    @Override
    public Integer getSemester() {
        return semester;
    }

    @Override
    public String getDegree() {
        return degree;
    }


}

