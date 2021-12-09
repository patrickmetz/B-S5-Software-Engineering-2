package org.hbrs.se2.project.hellocar.control.builders;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;

import java.time.LocalDate;
import java.util.List;

public class UserDTOBuilder {
    protected UserDTOImpl user;

    public UserDTOBuilder(UserDTOImpl user) {
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

    public UserDTOBuilder buildDefaultUser() {
        addAllAttributes();
        return this;

    }

    public UserDTOBuilder buildFirstname(String firstname) {
        user.setFirstName(firstname);
        return this;
    }

    public UserDTOBuilder buildLastname(String lastname) {
        user.setLastName(lastname);
        return this;
    }

    public UserDTOBuilder buildEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserDTOBuilder buildId(int id) {
        user.setId(id);
        return this;
    }
    public UserDTOBuilder buildUserId(String userId) {
        user.setUserid(userId);
        return this;
    }

    public UserDTOBuilder buildPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserDTOBuilder buildPhone(String phone) {
        user.setPhone(phone);
        return this;
    }

    public UserDTOBuilder buildDateOfBirth(LocalDate dateOfBirth) {
        user.setDateOfBirth(dateOfBirth);
        return this;
    }

    public UserDTOBuilder buildRoles(List<RolleDTO> roles) {
        user.setRoles(roles);
        return this;
    }

    public UserDTOImpl done() {
        return user;
    }
}
