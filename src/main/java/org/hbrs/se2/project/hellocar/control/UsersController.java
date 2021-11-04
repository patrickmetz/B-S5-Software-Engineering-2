package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.entities.Users;
import org.hbrs.se2.project.hellocar.repository.account.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    public <T extends  Users> void  createStudent(T user) {
        usersRepository.save(user);
        System.out.println("xxxx");
    }

}
