package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table( name ="job_advertisement" , schema = "carlook" )
public class JobAdvertisement {
    private int jobAdvertismentId;
    private String jobTitle;
    private String jobType;
    private String description;
    private LocalDate begin;
    private String tags;

    private User user;

    @Id
    @GeneratedValue
    @Column(name = "job_advertisement_id")
    public int getJobAdvertismentId() {
        return jobAdvertismentId;
    }

    public void setJobAdvertismentId(int jobAdvertismentId) {
        this.jobAdvertismentId = jobAdvertismentId;
    }

    @Basic
    @Column(name = "job_title", nullable = false)
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Basic
    @Column(name = "job_type", nullable = false)
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "begin_date", nullable = false)
    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "tags", nullable = false)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobAdvertisement advertisement = (JobAdvertisement) o;
        return jobAdvertismentId == advertisement.jobAdvertismentId &&
                Objects.equals(jobTitle, advertisement.jobTitle) &&
                Objects.equals(jobType, advertisement.jobType) &&
                Objects.equals(description, advertisement.description) &&
                Objects.equals(begin, advertisement.begin) &&
                Objects.equals(tags, advertisement.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobAdvertismentId, jobTitle, jobType, description, begin, tags);
    }

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
