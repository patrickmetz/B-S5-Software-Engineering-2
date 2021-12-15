package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("student")
public class Student extends JobPortalUser {

    @Basic
    @Column(name = "study_course")
    private String studyCourse;

    @Basic
    @Column(name = "specialization")
    private String spezialization;

    @Basic
    @Column(name = "semester")
    private Integer semester;

    @Basic
    @Column(name = "degree")
    private String degree;

    public String getStudyCourse() {
        return studyCourse;
    }

    public void setStudyCourse(String studyCourse) {
        this.studyCourse = studyCourse;
    }

    public String getSpezialization() {
        return spezialization;
    }

    public void setSpezialization(String spezialization) {
        this.spezialization = spezialization;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
