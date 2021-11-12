package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.UserFactory;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest

public class RegistrationTests {
    @Autowired
    private ManageUserControl userService; // !

    @Test
    void uniqueEmailTest() {
        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        studentDTOImpl.setEmail("test@gmail.com");
        try {
            int id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTOImpl,
                        new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            });
            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void uniqueUserIdTest() {
        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();

        // just to make sure that the username is being compared and not the email
        studentDTOImpl.setEmail("changed@company.de");
        try {
            int id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTOImpl,
                        new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            });
            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }

    }

    @Test
    void createStudentTest() {
        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        StudentDTO studentDTO;
        int id;
        try {
            id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            studentDTO = userService.readStudentById(id);
            assertEquals(studentDTO.getId(), id);
            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createCompanyTest() {
        CompanyDTOImpl companyDTOImpl = UserFactory.generateCompanyDto();
        CompanyDTO companyDTO;
        int id;
        try {
            id = userService.createUser(companyDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            companyDTO = userService.readCompanyById(id);
            assertEquals(companyDTO.getId(), id);
            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateStudentTest() {
        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        int id;
        try {
            id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            studentDTOImpl.setFirstName(studentDTOImpl.getFirstName()+"x");
            studentDTOImpl.setLastName(studentDTOImpl.getLastName()+"x");
            studentDTOImpl.setEmail(studentDTOImpl.getEmail()+"x");
            studentDTOImpl.setUserid(studentDTOImpl.getUserid()+"x");
            studentDTOImpl.setGender(studentDTOImpl.getGender()+"x");
            studentDTOImpl.setCity(studentDTOImpl.getCity()+"x");
            studentDTOImpl.setZipCode(studentDTOImpl.getZipCode()+"x");
            studentDTOImpl.setStreetNumber(studentDTOImpl.getStreetNumber()+"x");
            studentDTOImpl.setStreet(studentDTOImpl.getStreet()+"x");
            studentDTOImpl.setPassword(studentDTOImpl.getPassword()+"x");
            studentDTOImpl.setDateOfBirth(studentDTOImpl.getDateOfBirth()+1);

            userService.updateStudent(id, studentDTOImpl);
            StudentDTO updateStudent = userService.readStudentById(id);

            assertEquals(studentDTOImpl.getFirstName()+"x", updateStudent.getFirstName());
            assertEquals(studentDTOImpl.getLastName()+"x", updateStudent.getLastName());
            assertEquals(studentDTOImpl.getEmail()+"x", updateStudent.getEmail());
            assertEquals(studentDTOImpl.getUserid()+"x", updateStudent.getUserid());
            assertEquals(studentDTOImpl.getGender()+"x", updateStudent.getGender());
            assertEquals(studentDTOImpl.getCity()+"x", updateStudent.getCity());
            assertEquals(studentDTOImpl.getZipCode()+"x", updateStudent.getZipCode());
            assertEquals(studentDTOImpl.getStreetNumber()+"x", updateStudent.getStreetNumber());
            assertEquals(studentDTOImpl.getStreet()+"x", updateStudent.getStreet());
            assertEquals(studentDTOImpl.getPassword()+"x", updateStudent.getPassword());
            assertEquals(studentDTOImpl.getDateOfBirth()+1, updateStudent.getDateOfBirth());

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void updateCompanyTest() {
        CompanyDTOImpl companyDTOImpl1 = UserFactory.generateCompanyDto();
        int id;
        try {
            id = userService.createUser(companyDTOImpl1, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            companyDTOImpl1.setFirstName(companyDTOImpl1.getFirstName()+"x"); // update other attributes
            userService.updateCompany(id, companyDTOImpl1);
            CompanyDTO updatedCompany = userService.readCompanyById(id);
            assertEquals(companyDTOImpl1.getFirstName()+"x", updatedCompany.getFirstName());
            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }

    }

    @Test
    void deleteStudentByIdTest() {
        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        int id;
        try {
            id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.deleteUser(id);
            assertThrows(Exception.class, () -> {
                studentDTOImpl.getId();
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteCompanyByIdTest() {
        CompanyDTOImpl companyDTOImpl = UserFactory.generateCompanyDto();
        int id;
        try {
            id = userService.createUser(companyDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.COMPANY});
            userService.deleteUser(id);
            assertThrows(Exception.class, () -> {
                companyDTOImpl.getId();
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }

    }


    @Test
    void firstNameTest() {

        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        studentDTOImpl.setFirstName(null);
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.deleteUser(id);
        });


    }

    @Test
    void lastNameTest() {

        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        studentDTOImpl.setLastName(null);
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
        });
    }

    @Test
    void companyNameTest() {
        CompanyDTOImpl companyDTOImpl = UserFactory.generateCompanyDto();
        companyDTOImpl.setCompanyName(null);
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(companyDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.deleteUser(id);
        });
    }

    @Test
    void userIdTest() {
        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        studentDTOImpl.setUserid(null);
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.deleteUser(id);
        });

    }

    @Test
    void emailTest() {

        StudentDTOImpl studentDTOImpl = UserFactory.generateStudentDto();
        studentDTOImpl.setEmail(null);
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.deleteUser(id);
        });
    }

    @Test
    void passwordTest() {
        StudentDTOImpl studentDTOImpl1 = UserFactory.generateStudentDto();
        studentDTOImpl1.setPassword(null);
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(studentDTOImpl1, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.deleteUser(id);
        });
        // length > 4
        StudentDTOImpl studentDTOImpl2 = UserFactory.generateStudentDto();
        studentDTOImpl2.setPassword("abc");
        assertThrows(Exception.class, () -> {
            userService.createUser(studentDTOImpl2, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
        });
    }
}