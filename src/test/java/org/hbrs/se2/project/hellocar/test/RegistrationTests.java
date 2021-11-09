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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        StudentDTOImpl studentDTO = UserFactory.generateStudentDto();

        // just to make sure that the username is being compared and not the email
        studentDTO.setEmail("changed@company.de");
        try {
            userService.createUser(studentDTO, new String[]{Globals.Roles.USER, Globals.Roles.STUDENT});

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
    void firstNameTest() {
        /*Pattern p = Pattern.compile("^[a-zA-Z\\s]+"); //https://stackoverflow.com/questions/7362567/java-regex-for-full-name
        Matcher m = p.matcher(userDTOImpl.getFirstName());
        assertTrue(m.matches());*/
        userDTOImpl.setFirstName("");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getFirstName();
        });
        userDTOImpl.setFirstName("!?=)(/&%$§12344567890:_only_digits_are_allowed");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getFirstName();
        });
        userDTOImpl.setFirstName("aB:_upper_cases_only_at_first_place");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getFirstName();
        });
        userDTOImpl.setFirstName("a:_first_place_must_be_an_upper_case");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getFirstName();
        });
        userDTOImpl.setFirstName(" :_no_whitespaces");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getFirstName();
        });

    }

    @Test
    void lastNameTest() {
        /*Pattern p = Pattern.compile("^[a-zA-Z\\s]+"); //https://stackoverflow.com/questions/7362567/java-regex-for-full-name
        Matcher m = p.matcher(userDTOImpl.getLastName());
        assertTrue(m.matches());*/
        userDTOImpl.setLastName("");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getLastName();
        });
        userDTOImpl.setLastName("!?=)(/&%$§12344567890:_only_digits_are_allowed");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getLastName();
        });
        userDTOImpl.setLastName("aB:_upper_cases_only_at_first_place");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getLastName();
        });
        userDTOImpl.setLastName("a:_first_place_must_be_an_upper_case");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getLastName();
        });
        userDTOImpl.setLastName(" :_no_whitespaces");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getLastName();
        });
    }

    @Test
    void userIdTest() { //userId is equal to userName
        /*Pattern p = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$"); // https://javadeveloperzone.com/java-8/java-regular-expression-for-username/
        Matcher m = p.matcher(userDTOImpl.getUserid());
        assertTrue(m.matches());*/
        userDTOImpl.setUserid("");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getUserid();
        });
        userDTOImpl.setUserid(" :_whitespaces_are_not_allowed");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getUserid();
        });

    }

    @Test
    void emailTest() {
       /* Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE); // https://stackoverflow.com/questions/8204680/java-regex-email
        Matcher m = p.matcher(userDTOImpl.getEmail());
        assertTrue(m.matches());*/
        userDTOImpl.setEmail("");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getEmail();
        });
        userDTOImpl.setEmail(" :_whitespaces_are_not_allowed");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getEmail();
        });
    }

    @Test
    void passwordTest() {
        /*Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"); // https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
        Matcher m = p.matcher(userDTOImpl.getPassword());
        assertTrue(m.matches());*/
        userDTOImpl.setPassword("");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getPassword();
        });
        userDTOImpl.setPassword(" :_whitespaces_are_not_allowed");
        assertThrows(Exception.class, () -> {
            userDTOImpl.getPassword();
        });

    }

    @Test
    void cityTest() {
        /*Pattern p = Pattern.compile("/^[a-zA-Z\\u0080-\\u024F]+(?:([\\ \\-\\']|(\\.\\ ))[a-zA-Z\\u0080-\\u024F]+)*$/"); // https://stackoverflow.com/questions/11757013/regular-expressions-for-city-name
        Matcher m = p.matcher(jobPortalUserDTOImpl.getCity());
        assertTrue(m.matches());*/
        jobPortalUserDTOImpl.setCity("");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getPassword();
        });
        jobPortalUserDTOImpl.setCity(")?!@:_special_character_are_not_allowed");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getPassword();
        });
    }

    @Test
    void dateOfBirthTest() {
        /*Pattern p = Pattern.compile("^(0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])[-/.](19|20)\\d\\d$"); // https://stackoverflow.com/questions/22160079/date-of-birth-validation-by-using-regular-expression
        Matcher m = p.matcher("04/08/21998"); // can we chance public LocalDate getDateOfBirth() to public String getDateOfBirth()?
        assertTrue(m.matches());

        jobPortalUserDTOImpl.setDateOfBirth();
        assertThrows(Exception.class, ()->{jobPortalUserDTOImpl.getDateOfBirth();});*/
    }

    @Test
    void zipCodeTest() {
        /*Pattern p = Pattern.compile("^\\d{5}");
        Matcher m = p.matcher(jobPortalUserDTOImpl.getZipCode());
        assertTrue(m.matches());*/

        jobPortalUserDTOImpl.setZipCode("");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getZipCode();
        });
        jobPortalUserDTOImpl.setZipCode("only_5_numbers_are_allowed");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getZipCode();
        });
        jobPortalUserDTOImpl.setZipCode("1234");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getZipCode();
        });
        jobPortalUserDTOImpl.setZipCode("123456");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getZipCode();
        });
    }

    @Test
    void streetNumberTest() {
        /*Pattern p = Pattern.compile("^\\d{1,4}[a-z]?");
        Matcher m = p.matcher(jobPortalUserDTOImpl.getStreetNumber());
        assertTrue(m.matches());*/

        jobPortalUserDTOImpl.setStreetNumber("");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getStreetNumber();
        });
        jobPortalUserDTOImpl.setStreetNumber(" :_whitespaces_are_not_allowed");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getStreetNumber();
        });
        jobPortalUserDTOImpl.setStreetNumber("first_1-4_numbers_then_optional_a_lower_case");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getStreetNumber();
        });
    }

    @Test
    void streetTest() {
        jobPortalUserDTOImpl.setStreet("");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getStreet();
        });
        jobPortalUserDTOImpl.setStreet("!?@:_no_special_characters");
        assertThrows(Exception.class, () -> {
            jobPortalUserDTOImpl.getStreet();
        });
    }

    @Test
    void companyNameTest() {
        companyDTOImpl.setCompanyName("");
        assertThrows(Exception.class, () -> {
            companyDTOImpl.getCompanyName();
        });
    }

}
