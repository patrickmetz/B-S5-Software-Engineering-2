package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
// import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table( name ="user" , schema = "carlook" )
public class User {
    private int id;
    private LocalDate dateOfBirth;
    private String email;
    private String firstName;
    private String lastName;
    private String occupation;
    private String password;
    private String phone;
    private String userid;
    private List<Rolle> roles;

    private List<JobAdvertisement> advertisements = new ArrayList<>();
    private List<JobApplication> applications = new ArrayList<>();

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
    @Column(name = "date_of_birth")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "occupation")
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Basic
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "userid", nullable = false, updatable = true, unique = true)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_rolle", catalog = "demouser",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichhnung", nullable = false))
    public List<Rolle> getRoles() {
        return roles;
    }

    public void setRoles(List<Rolle> roles) {
        this.roles = roles;
    }


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_to_job_advertisement", catalog = "demouser",
//            schema = "carlook",
//            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false))

    @OneToMany(mappedBy = "user")
    public List<JobAdvertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<JobAdvertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @OneToMany(mappedBy = "user")
    public List<JobApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<JobApplication> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(occupation, user.occupation) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(userid, user.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfBirth, email, firstName, lastName, occupation, password, phone, userid);
    }
}
