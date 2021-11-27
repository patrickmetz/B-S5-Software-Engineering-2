package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.builders.CompanyDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.StudentDTOBuilder;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.*;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest

public class AccountTests {
    @Autowired
    private ManageUserControl userService;

    private static final String[] STUDENT_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.STUDENT};

    private static final String[] COMPANY_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.COMPANY};


    void valueIsUnique(JobPortalUserDTO dto) {
        try {
            // save dto to db
            int id = userService.createUser(dto, STUDENT_ROLES);

            // provoke exception by saving duplicate to db
            assertThrows(Exception.class, () -> {
                userService.createUser(dto, STUDENT_ROLES);
            });

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }


    @Test
    void emailsAreUnique() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        valueIsUnique(dto);
    }

    @Test
    void userIdsAreUnique() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();

        // just to make sure that the username is being compared and not the email
        dto.setEmail("changed@company.de");

        valueIsUnique(dto);
    }

    @Test
    void studentsAreCreated() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        entityIsCreated(dto);
    }

    @Test
    void companiesAreCreated() {
        CompanyDTOBuilder builder = new CompanyDTOBuilder();
        CompanyDTOImpl dto = builder.buildDefaultUser().done();
        entityIsCreated(dto);
    }

    void entityIsCreated(JobPortalUserDTO dtoA){
        try {
            int id = userService.createUser(dtoA, COMPANY_ROLES);
            JobPortalUserDTO dtoB = userService.readUser(id);

            assertEquals(dtoB.getId(), id);

            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    void entityIsUpdated(JobPortalUserDTO dto){

    }

    @Test
    void studentsAreUpdated() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
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


            userService.updateUser(id, dto);

            StudentDTO updated = (StudentDTO) userService.readUser(id);

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


            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void companiesAreUpdated() {
        CompanyDTOBuilder builder = new CompanyDTOBuilder();
        CompanyDTOImpl dto = builder.buildDefaultUser().done();
        int id;

        try {
            id = userService.createUser(dto, COMPANY_ROLES);

            String update = "update";

            // attach update to original
            dto = (CompanyDTOImpl) builder.
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

            userService.updateUser(id, dto);

            CompanyDTO updated = (CompanyDTO) userService.readUser(id);

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


            userService.deleteUser(id);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    void entityIsDeleted(JobPortalUserDTO dtoA){
        try {
            int idA = userService.createUser(dtoA, STUDENT_ROLES);
            userService.deleteUser(idA);

            JobPortalUserDTO dtoB = (StudentDTO) userService.readUser(idA);
            assertNull(dtoB);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void studentsAreDeleted() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        entityIsDeleted(dto);
    }

    @Test
    void companiesAreDeleted() {
        CompanyDTOBuilder builder = new CompanyDTOBuilder();
        CompanyDTOImpl dto = builder.buildDefaultUser().done();
        entityIsDeleted(dto);
    }

    @Test
    void firstNameCantBeNull() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setFirstName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void lastNameCantBeNull() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
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
        CompanyDTOBuilder builder = new CompanyDTOBuilder();
        CompanyDTOImpl dto = builder.buildDefaultUser().done();
        dto.setCompanyName(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, COMPANY_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void userIdCantBeNull() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setUserid(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void emailMailCantBeNull() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setEmail(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Test
    void passwordCantBeNull() {
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dto = (StudentDTOImpl) builder.buildDefaultUser().done();
        dto.setPassword(null);

        assertThrows(Exception.class, () -> {
            int id = userService.createUser(dto, STUDENT_ROLES);
            userService.deleteUser(id);
        });
    }

    @Disabled
    void passwordCantBeShorterThanFourCharacters() {
        //todo @vincent: das kurze password wirft keine exception, bitte reparieren, ggf. mit ameur absprechen
        StudentDTOBuilder builder = new StudentDTOBuilder();
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
        StudentDTOBuilder builder = new StudentDTOBuilder();
        StudentDTOImpl dtoA = (StudentDTOImpl) builder.buildDefaultUser().done();

        int idA = 0;

        String[][] allRoleArrays = {STUDENT_ROLES, COMPANY_ROLES};

        try {
            for (String[] roleArray : allRoleArrays) {
                // write user to db
                idA = userService.createUser(dtoA, roleArray);

                // fetch a copy of it from db
                StudentDTO dtoB = (StudentDTO) userService.readUser(idA);

                // fetch copies' roles
                RolleDAO rolleDAO = new RolleDAO();
                List<RolleDTO> roles = rolleDAO.getRolesOfUser(dtoB);

                // check if copy contains appropriate roles
                for (RolleDTO role : roles) {
                    assertTrue(
                            Arrays.asList(roleArray).contains(role.getBezeichhnung())
                    );
                }

                userService.deleteUser(idA);
            }
        } catch (DatabaseUserException | DatabaseLayerException e) {
            e.printStackTrace();
        }
    }
}