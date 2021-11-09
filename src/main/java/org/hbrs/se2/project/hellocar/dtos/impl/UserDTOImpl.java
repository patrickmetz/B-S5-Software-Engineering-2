package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;

import java.time.LocalDate;
import java.util.List;

public class UserDTOImpl implements UserDTO {

    private int id;
    private String firstname;
    private String lastname;

    // needed for job portal
    private String email;
    private String userid;
    private String password;
    private String phone;
    private LocalDate dateOfBirth;

    private List<RolleDTO> roles;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public void setRoles(List<RolleDTO> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {this.email = email;}

    public void setUserid(String userid) {this.userid = userid;}

    public void setPassword(String password) {this.password = password;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getFirstName() {
        return this.firstname;
    }

    @Override
    public String getLastName() {
        return this.lastname;
    }

    @Override
    public List<RolleDTO> getRoles() {
        return this.roles;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUserid() {
        return userid;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public LocalDate getDateOfBirth() { // can i change this to a String matches better with regular expressions
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", roles=" + roles +
                '}';
    }
}
