package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.registration.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.StudentDTO;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.junit.jupiter.api.Disabled;
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
            userService.createUser(studentDTO, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTO,
                        new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void uniqueUserIdTest() {
        StudentDTOImpl studentDTO = new StudentDTOImpl();
        studentDTO.setUserid("patrick");
        try {
            userService.createUser(studentDTO, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTO,
                        new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void createStudentTest() {
        StudentDTOImpl studentDTOImpl = new StudentDTOImpl();
        studentDTOImpl.setUserid("testID");
        StudentDTO studentDTO;
        int id;
        try {
            id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            studentDTO = userService.readStudentById(id);
            assertEquals(studentDTO.getId(), id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createCompanyTest() {
        CompanyDTOImpl companyDTOImpl = new CompanyDTOImpl();
        companyDTOImpl.setUserid("testID");
        CompanyDTO companyDTO;
        int id;
        try {
            id = userService.createUser(companyDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            companyDTO = userService.readCompanyById(id);
            assertEquals(companyDTO.getId(), id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }
}
