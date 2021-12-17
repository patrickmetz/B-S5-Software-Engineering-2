package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import java.util.Objects;


@Entity
@Table( name ="job_advertisement" , schema = "carlook" )
public class JobAdvertisement {

    private int id;
    protected String jobTitle;
    private String jobType;
    private String description;
    private LocalDate begin;
    private String tags;
    private List<User> users;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "job_title")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Basic
    @Column(name = "job_type")
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "begin_date")
    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.EAGER )
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobAdvertisement advertisement = (JobAdvertisement) o;
        return id == advertisement.id &&
                Objects.equals(jobTitle, advertisement.jobTitle) &&
                Objects.equals(jobType, advertisement.jobType) &&
                Objects.equals(description, advertisement.description) &&
                Objects.equals(begin, advertisement.begin) &&
                Objects.equals(tags, advertisement.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobTitle, jobType, description, begin, tags);
    }
}
