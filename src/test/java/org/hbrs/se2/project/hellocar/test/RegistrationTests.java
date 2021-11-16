package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.UserEntityFactory;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest

public class RegistrationTests {
    @Autowired
    private ManageUserControl userService;

    private static final String[] STUDENT_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.STUDENT};

    private static final String[] COMPANY_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.COMPANY};

    @Test
    void emailsAreUnique() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();

        try {
            int id = userService.createUser(dto, STUDENT_ROLES);

            assertThrows(Exception.class, () -> {
                userService.createUser(dto, STUDENT_ROLES);
            });

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userIdsAreUnique() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();

        // just to make sure that the username is being compared and not the email
        dto.setEmail("changed@company.de");

        try {
            int id = userService.createUser(dto, STUDENT_ROLES);

            assertThrows(Exception.class, () -> {
                userService.createUser(dto, STUDENT_ROLES);
            });

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void studentsAreCreated() {
        StudentDTOImpl dtoA = UserEntityFactory.createTestStudentDTOImpl();
        StudentDTO dtoB;
        int idA;

        try {
            idA = userService.createUser(dtoA, STUDENT_ROLES);
            dtoB = userService.readStudent(idA);

            assertEquals(dtoB.getId(), idA);

            userService.deleteUser(idA);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void companiesAreCreated() {
        CompanyDTOImpl dtoA = UserEntityFactory.createTestCompanyDTOimpl();
        CompanyDTO dtoB;
        int idA;

        try {
            idA = userService.createUser(dtoA, COMPANY_ROLES);
            dtoB = userService.readCompany(idA);

            assertEquals(dtoB.getId(), idA);

            userService.deleteUser(idA);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void studentsAreUpdated() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();
        int id;

        try {
            id = userService.createUser(dto, STUDENT_ROLES);

            String update = "update";

            // attach update to original
            dto.setFirstName(dto.getFirstName() + update);
            dto.setLastName(dto.getLastName() + update);
            dto.setEmail(dto.getEmail() + update);
            dto.setUserid(dto.getUserid() + update);
            dto.setGender(dto.getGender() + update);
            dto.setCity(dto.getCity() + update);
            dto.setZipCode(dto.getZipCode() + update);
            dto.setStreetNumber(dto.getStreetNumber() + update);
            dto.setStreet(dto.getStreet() + update);
            dto.setPassword(dto.getPassword() + update);

            //todo @vincent das klappt nicht, weil in getDateOfBirth noch kein Datum ist
            //todo du musst hier selbst ein passendes objekt erzeugen für den setter
            //dto.setDateOfBirth(dto.getDateOfBirth().plusDays(1));

            userService.updateStudent(id, dto);

            StudentDTO updated = userService.readStudent(id);

            // compare updated original with its updated data in the database
            assertEquals(dto.getFirstName(), updated.getFirstName());
            assertEquals(dto.getLastName(), updated.getLastName());
            assertEquals(dto.getEmail(), updated.getEmail());
            assertEquals(dto.getUserid(), updated.getUserid());
            assertEquals(dto.getGender(), updated.getGender());
            assertEquals(dto.getCity(), updated.getCity());
            assertEquals(dto.getZipCode(), updated.getZipCode());
            assertEquals(dto.getStreetNumber(), updated.getStreetNumber());
            assertEquals(dto.getStreet(), updated.getStreet());
            assertEquals(dto.getPassword(), updated.getPassword());

            //todo @vincent das klappt wieder nicht, sie todo weiter oben
            //assertEquals(dto.getDateOfBirth().plusDays(1), updated.getDateOfBirth());

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void companiesAreUpdated() {
        CompanyDTOImpl dto = UserEntityFactory.createTestCompanyDTOimpl();
        int id;

        try {
            id = userService.createUser(dto, COMPANY_ROLES);

            String update = "update";

            // attach update to original
            dto.setFirstName(dto.getFirstName() + update);
            dto.setLastName(dto.getLastName() + update);
            dto.setEmail(dto.getEmail() + update);
            dto.setUserid(dto.getUserid() + update);
            dto.setGender(dto.getGender() + update);
            dto.setCity(dto.getCity() + update);
            dto.setZipCode(dto.getZipCode() + update);
            dto.setStreetNumber(dto.getStreetNumber() + update);
            dto.setStreet(dto.getStreet() + update);
            dto.setPassword(dto.getPassword() + update);

            dto.setCompanyName(dto.getCompanyName() + update);

            //todo @vincent das klappt nicht, weil in getDateOfBirth noch kein Datum ist
            //todo du musst hier selbst ein passendes objekt erzeugen für den setter
            //dto.setDateOfBirth(dto.getDateOfBirth().plusDays(1));

            userService.updateCompany(id, dto);

            CompanyDTO updated = userService.readCompany(id);

            // compare updated original with its updated data in the database
            assertEquals(dto.getFirstName(), updated.getFirstName());
            assertEquals(dto.getLastName(), updated.getLastName());
            assertEquals(dto.getEmail(), updated.getEmail());
            assertEquals(dto.getUserid(), updated.getUserid());
            assertEquals(dto.getGender(), updated.getGender());
            assertEquals(dto.getCity(), updated.getCity());
            assertEquals(dto.getZipCode(), updated.getZipCode());
            assertEquals(dto.getStreetNumber(), updated.getStreetNumber());
            assertEquals(dto.getStreet(), updated.getStreet());
            assertEquals(dto.getPassword(), updated.getPassword());

            //todo @vincent das klappt wieder nicht, sie todo weiter oben
            //assertEquals(dto.getDateOfBirth().plusDays(1), updated.getDateOfBirth());

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void studentsAreDeleted() {
        StudentDTOImpl dtoA = UserEntityFactory.createTestStudentDTOImpl();
        StudentDTO dtoB;

        int idA;

        try {
            idA = userService.createUser(dtoA, STUDENT_ROLES);
            userService.deleteUser(idA);

            dtoB = userService.readStudent(idA);
            assertNull(dtoB);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void companiesAreDeleted() {
        CompanyDTOImpl dtoA = UserEntityFactory.createTestCompanyDTOimpl();
        CompanyDTO dtoB;

        int idA;

        try {
            idA = userService.createUser(dtoA, COMPANY_ROLES);
            userService.deleteUser(idA);

            dtoB = userService.readCompany(idA);
            assertNull(dtoB);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void firstNameCantBeNull() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();
        dto.setFirstName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void lastNameCantBeNull() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();
        dto.setLastName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void companyNameCantBeNull() {
        //todo @vincent: bitte test reparieren, ggf. mit ameur absprechen
        CompanyDTOImpl dto = UserEntityFactory.createTestCompanyDTOimpl();
        dto.setCompanyName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, COMPANY_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void userIdCantBeNull() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();
        dto.setUserid(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void emailMailCantBeNull() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();
        dto.setEmail(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void passwordCantBeNull() {
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();
        dto.setPassword(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void passwordCantBeShorterThanFourCharacters() {
        //todo @vincent: das kurze password wirft keine exception, bitte reparieren, ggf. mit ameur absprechen
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();

        // length == 4
        dto.setPassword("abcd");
        assertDoesNotThrow(() -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });

        // length < 4
        dto.setPassword("abc");
        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void rolesAreCreated() {
        //todo @alex kleemann
        //todo: a) der test funktioniert leider noch nicht
        //todo bitte reparieren
        StudentDTOImpl dto = UserEntityFactory.createTestStudentDTOImpl();

        String[] testRoles = STUDENT_ROLES;
        int id = 0;
        try {
            id = userService.createUser(dto, testRoles);

            // Testing RolleDAO::getRolesOfUser
            RolleDAO rolleDAO = new RolleDAO();
            List<RolleDTO> roles = rolleDAO.getRolesOfUser(dto);
            assertEquals(roles.size(), testRoles.length);
            for (int i = 0; i < roles.size(); i++) {
                RolleDTO r1 = roles.get(i);
                assertEquals(r1.getBezeichhnung(), testRoles[i]);
            }
        } catch (DatabaseUserException | DatabaseLayerException e) {
            e.printStackTrace();
        } finally {
            if( id != 0 ){
                userService.deleteUser(id);
            }
        }
    }
}