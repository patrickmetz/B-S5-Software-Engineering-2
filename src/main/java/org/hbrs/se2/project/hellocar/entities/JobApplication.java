package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import java.util.Objects;


@Entity
@Table( name ="job_application" , schema = "carlook" )
public class JobApplication {

    private int jobApplicationId;
    private String text;
    private byte[] resume;
    private User user;

    @Id
    @GeneratedValue
    @Column(name = "job_application_id")
    public int getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(int jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "resume")
    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplication application = (JobApplication) o;
        return jobApplicationId == application.jobApplicationId &&
                Objects.equals(text, application.text) &&
                Objects.equals(resume, application.resume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobApplicationId, text, resume);
    }
}
