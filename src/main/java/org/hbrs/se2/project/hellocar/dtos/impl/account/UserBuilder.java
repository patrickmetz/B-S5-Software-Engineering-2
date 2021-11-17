package org.hbrs.se2.project.hellocar.dtos.impl.account;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;

import java.time.LocalDate;
import java.util.List;

public class UserBuilder {
    protected UserDTOImpl userDTOImpl;

    public UserBuilder(UserDTOImpl userDTOImpl) {
        this.userDTOImpl = userDTOImpl;
    }

    protected void addAllAttributes() {
        userDTOImpl.setFirstName("firstname");
        userDTOImpl.setLastName("lastname");
        userDTOImpl.setLastName("lastname");
        userDTOImpl.setEmail("email@gmail.com");
        userDTOImpl.setUserid("userId");
        userDTOImpl.setPassword("password");
        userDTOImpl.setPhone("01573777777");
        userDTOImpl.setDateOfBirth(LocalDate.of(1989,10,3));


    }

    public UserBuilder buildDefaultUser(){
        addAllAttributes();
        return this;

    }

    public UserBuilder buildFirstname(String firstname) {
        userDTOImpl.setFirstName(firstname);
        return this;
    }

    public UserBuilder buildLastname(String lastname) {
        userDTOImpl.setLastName(lastname);
        return this;
    }

    public UserBuilder buildEmail(String email){
        userDTOImpl.setEmail(email);
        return this;
    }

    //id auslassen?


    public UserBuilder buildUserId(String userId) {
        userDTOImpl.setUserid(userId);
        return this;
    }

    public UserBuilder buildPassword(String password) {
        userDTOImpl.setPassword(password);
        return this;
    }

    public UserBuilder buildPhone(String phone) {
        userDTOImpl.setPhone(phone);
        return this;
    }

    public UserBuilder buildDateOfBirth(LocalDate dateOfBirth) {
        userDTOImpl.setDateOfBirth(dateOfBirth);
        return this;
    }

    public UserBuilder buildRoles(List<RolleDTO> roles) {
        userDTOImpl.setRoles(roles);
        return this;
    }

    public UserDTOImpl done() {
        return userDTOImpl;
    }
}
