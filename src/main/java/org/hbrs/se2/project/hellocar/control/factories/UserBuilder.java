package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;

import java.time.LocalDate;
import java.util.List;

public class UserBuilder {
    protected UserDTOImpl user;

    public UserBuilder(UserDTOImpl user) {
        this.user = user;
    }

    protected void addAllAttributes() {
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setLastName("lastname");
        user.setEmail("email@gmail.com");
        user.setUserid("userId");
        user.setPassword("password");
        user.setPhone("01573777777");
        user.setDateOfBirth(LocalDate.of(1989, 10, 3));
    }

    public UserBuilder buildDefaultUser() {
        addAllAttributes();
        return this;

    }

    public UserBuilder buildFirstname(String firstname) {
        user.setFirstName(firstname);
        return this;
    }

    public UserBuilder buildLastname(String lastname) {
        user.setLastName(lastname);
        return this;
    }

    public UserBuilder buildEmail(String email) {
        user.setEmail(email);
        return this;
    }

    //id auslassen?


    public UserBuilder buildUserId(String userId) {
        user.setUserid(userId);
        return this;
    }

    public UserBuilder buildPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder buildPhone(String phone) {
        user.setPhone(phone);
        return this;
    }

    public UserBuilder buildDateOfBirth(LocalDate dateOfBirth) {
        user.setDateOfBirth(dateOfBirth);
        return this;
    }

    public UserBuilder buildRoles(List<RolleDTO> roles) {
        user.setRoles(roles);
        return this;
    }

    public UserDTOImpl done() {
        return user;
    }
}
