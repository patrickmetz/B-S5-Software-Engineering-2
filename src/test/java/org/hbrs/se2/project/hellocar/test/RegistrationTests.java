package org.hbrs.se2.project.hellocar.test;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.registration.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.StudentDTO;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.SocketOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@SpringBootTest

public class RegistrationTests {
    @Autowired
    private ManageUserControl userService;

    StudentDTOImpl studentDTOImpl;
    CompanyDTOImpl companyDTOImpl;
    JobPortalUserDTOImpl jobPortalUserDTOImpl;
    UserDTOImpl userDTOImpl;


    @BeforeEach
    void set() {
        studentDTOImpl = new StudentDTOImpl();
        companyDTOImpl = new CompanyDTOImpl();
        jobPortalUserDTOImpl = new JobPortalUserDTOImpl();
        userDTOImpl = new UserDTOImpl();

    }

    @AfterEach
    void reset() {
        studentDTOImpl = null;
        companyDTOImpl = null;
        jobPortalUserDTOImpl = null;
        userDTOImpl = null;
    }

    @Test
    void uniqueEmailTest() {
        studentDTOImpl.setEmail("test@gmail.com");
        try {
            userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTOImpl,
                        new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void uniqueUserIdTest() {
        studentDTOImpl.setUserid("patrick");
        try {
            userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

            assertThrows(Exception.class, () -> {
                userService.createUser(studentDTOImpl,
                        new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            });
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void createStudentTest() {
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

    @Test
    void updateStudentTest() {
        studentDTOImpl.setFirstName("Bob");
        int id;
        try {
            id = userService.createUser(studentDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.updateStudent(id);
            assertEquals(studentDTOImpl.getFirstName(), "Alex");
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @Test
    void updateCompanyTest() {
        companyDTOImpl.setFirstName("Bob");
        int id;
        try {
            id = userService.createUser(companyDTOImpl, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});
            userService.updateCompany(id);
            assertEquals(companyDTOImpl.getFirstName(), "Alex");
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }

    }

    @Test
    void deleteStudentByIdTest() {


    }

    @Test
    void deleteCompanyByIdTest() {


    }

    @Test
    void mandatoryFieldTest() {
        assertNotNull(studentDTOImpl.getFirstName());
        assertNotNull(studentDTOImpl.getLastName());
        assertNotNull(studentDTOImpl.getUserid());
        assertNotNull(studentDTOImpl.getEmail());
        assertNotNull(studentDTOImpl.getPassword());
    }

    @Test
    void firstNameTest() {
        Pattern p = Pattern.compile("^[a-zA-Z\\s]+"); //https://stackoverflow.com/questions/7362567/java-regex-for-full-name
        Matcher m = p.matcher(userDTOImpl.getFirstName());
        assertTrue(m.matches());
    }

    @Test
    void lastNameTest() {
        Pattern p = Pattern.compile("^[a-zA-Z\\s]+"); //https://stackoverflow.com/questions/7362567/java-regex-for-full-name
        Matcher m = p.matcher(userDTOImpl.getLastName());
        assertTrue(m.matches());
    }

    @Test
    void userIdTest() { //userId is equal to userName
        Pattern p = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$"); // https://javadeveloperzone.com/java-8/java-regular-expression-for-username/
        Matcher m = p.matcher(userDTOImpl.getUserid());
        assertTrue(m.matches());
    }

    @Test
    void emailTest() {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE); // https://stackoverflow.com/questions/8204680/java-regex-email
        Matcher m = p.matcher(userDTOImpl.getEmail());
        assertTrue(m.matches());
    }

    @Test
    void passwordTest() {
        Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"); // https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
        Matcher m = p.matcher(userDTOImpl.getPassword());
        assertTrue(m.matches());
}

    @Test
    void cityTest(){
        Pattern p = Pattern.compile("/^[a-zA-Z\\u0080-\\u024F]+(?:([\\ \\-\\']|(\\.\\ ))[a-zA-Z\\u0080-\\u024F]+)*$/"); // https://stackoverflow.com/questions/11757013/regular-expressions-for-city-name
        Matcher m = p.matcher(jobPortalUserDTOImpl.getCity());
        assertTrue(m.matches());
    }

    @Test
    void dateOfBirthTest(){
        Pattern p = Pattern.compile( "^(0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])[-/.](19|20)\\d\\d$"); // https://stackoverflow.com/questions/22160079/date-of-birth-validation-by-using-regular-expression
        Matcher m = p.matcher("04/08/21998"); // can we chance public LocalDate getDateOfBirth() to public String getDateOfBirth()?
        assertTrue(m.matches());
    }






}
