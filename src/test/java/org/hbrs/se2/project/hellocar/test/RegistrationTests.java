package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.registration.StudentDTO;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest

public class RegistrationTests {
    @Autowired
    private ManageUserControl userService;

    @Test
    void uniqueEmailTest() {
        StudentDTOImpl studentDTO = new StudentDTOImpl();
        studentDTO.setEmail("vince.br@gmail.com");
        try {
            userService.createUser(studentDTO, new String[]{"user", "student"});

            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTO,
                        new String[]{"user", "student"});
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }
}
