package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.UserEntityFactory;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.*;
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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();

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
    void emailsAreUniqueTest() {
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl)builder.buildDefaultUser().done();
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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();

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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dtoA = (StudentDTOImpl) builder.buildDefaultUser().done();
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
        CompanyBuilder builder = new CompanyBuilder();
        CompanyDTOImpl dtoA = builder.buildDefaultUser().done();
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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        int id;

        try {
            id = userService.createUser(dto, STUDENT_ROLES);

            String update = "update";

            // attach update to original
           dto = (StudentDTOImpl) builder.
                    buildGender(dto.getGender() + update).
                    buildCity(dto.getCity() + update).
                    buildStreet(dto.getStreet() + update).
                    buildStreetNumber(dto.getStreetNumber() + update).
                    buildZipCode(dto.getZipCode() + update).
                    buildFirstname(dto.getFirstName() + update).
                    buildLastname(dto.getLastName() + update).
                    buildEmail(dto.getEmail() + update).
                    buildUserId(dto.getUserid() + update).
                    buildPassword(dto.getPassword() + update).
                    buildDateOfBirth(dto.getDateOfBirth().plusDays(1)).
                    done();



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
        CompanyBuilder builder = new CompanyBuilder();
        CompanyDTOImpl dto = builder.buildDefaultUser().done();
        int id;

        try {
            id = userService.createUser(dto, COMPANY_ROLES);

            String update = "update";

            // attach update to original
            dto = (CompanyDTOImpl)builder.
                    buildCompanyName(dto.getCompanyName() + update).
                    buildGender(dto.getGender() + update).
                    buildCity(dto.getCity() + update).
                    buildStreet(dto.getStreet() + update).
                    buildStreetNumber(dto.getStreetNumber() + update).
                    buildZipCode(dto.getZipCode() + update).
                    buildFirstname(dto.getFirstName() + update).
                    buildLastname(dto.getLastName() + update).
                    buildEmail(dto.getEmail() + update).
                    buildUserId(dto.getUserid() + update).
                    buildPassword(dto.getPassword() + update).
                    buildDateOfBirth(dto.getDateOfBirth().plusDays(1)).
                    done();


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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dtoA = (StudentDTOImpl) builder.buildDefaultUser().done();
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
        CompanyBuilder builder = new CompanyBuilder();
        CompanyDTOImpl dtoA = builder.buildDefaultUser().done();
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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setFirstName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void lastNameCantBeNull() {
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setLastName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }
    @Disabled
    void companyNameCantBeNull() {
        //todo @vincent: bitte test reparieren, ggf. mit ameur absprechen
        CompanyBuilder builder = new CompanyBuilder();
        CompanyDTOImpl dto = builder.buildDefaultUser().done();
        dto.setCompanyName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, COMPANY_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void userIdCantBeNull() {
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setUserid(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void emailMailCantBeNull() {
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setEmail(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void passwordCantBeNull() {
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setPassword(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void passwordCantBeShorterThanFourCharacters() {
        //todo @vincent: das kurze password wirft keine exception, bitte reparieren, ggf. mit ameur absprechen
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();

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
        StudentBuilder builder = new StudentBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();

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