package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.builders.CompanyDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.JobAdvertisementDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.StudentDTOBuilder;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class JobAdvertisementTest {

    @Autowired
    private ManageJobAdvertisementControl advertisementService;

    @Autowired
    private ManageUserControl userService;

    private static final String[] STUDENT_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.STUDENT};

    private static final String[] COMPANY_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.COMPANY};

    JobAdvertisementDTOBuilder builder;
    int studentId;
    int companyId;

    @BeforeEach
    void set() {
        builder = new JobAdvertisementDTOBuilder();

        StudentDTOBuilder builderA = new StudentDTOBuilder();
        CompanyDTOBuilder builderB = new CompanyDTOBuilder();

        StudentDTOImpl dtoA = builderA.buildDefaultUser().done();
        CompanyDTOImpl dtoB = builderB.buildDefaultUser().done();

        try {
            studentId = userService.createUser(dtoA, STUDENT_ROLES);
            companyId = userService.createUser(dtoB, COMPANY_ROLES);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @AfterEach
    void reset() {
        builder = null;
        userService.deleteUser(studentId);
        userService.deleteUser(companyId);

    }


    @Test
    void createJobAdvertisement() {   // auch für Studenten?
        createAdvertisementTest(companyId);
    }

    @Test
    void readJobAdvertisement() {
        readAdvertisementTest(companyId);
    }

    @Test
    void updateJobAdvertisement() {
        updateJobAdvertisementTest(companyId);
    }


    @Test
    void deleteJobAdvertisement() {
        deleteJobAdvertisementTest(companyId);

    }

    @Test
    void jobTitleCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().buildJobTitle(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void jobTypeCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().buildJobType(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void beginCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().buildBegin(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void tagsCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().buildTags(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void descriptionCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().buildDescription(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }


    Integer createAdvertisement(int userId, JobAdvertisementDTOImpl dto) {
        try {
            return advertisementService.createJobAdvertisement(dto, userId);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
        return null;
    }

    void createAdvertisementTest(int userId) {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().done();
        int id = createAdvertisement(userId, dto);
        JobAdvertisementDTO dtoB = advertisementService.readJobAdvertisement(id);
        assertEquals(dtoB.getId(), id);
        advertisementService.deleteJobAdvertisement(id);
    }

    void readAdvertisementTest(int userId) {
        JobAdvertisementDTOImpl dtoA = builder.buildDefaultUser().done();
        int id = createAdvertisement(userId, dtoA);
        JobAdvertisementDTO dtoB = advertisementService.readJobAdvertisement(id);
        assertEquals(dtoB.getId(), id);
        advertisementService.deleteJobAdvertisement(id);
    }

    private void updateJobAdvertisementTest(int userId) {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().done();
        int id = createAdvertisement(userId, dto);

        String update = "update";

        dto = builder.
                buildJobType(dto.getJobType() + update).
                buildJobTitle(dto.getJobTitle() + update).
                buildBegin(dto.getBegin().plusDays(1)).
                buildTags(dto.getTags() + update).
                buildDescription(dto.getDescription() + update).
                done();

        advertisementService.updateJobAdvertisement(dto, id);

        JobAdvertisementDTO updated = advertisementService.readJobAdvertisement(id);

        assertEquals(dto.getJobType(), updated.getJobType());
        assertEquals(dto.getJobTitle(), updated.getJobTitle());
        assertEquals(dto.getBegin(), updated.getBegin());
        assertEquals(dto.getTags(), updated.getTags());
        assertEquals(dto.getDescription(), updated.getDescription());

        advertisementService.deleteJobAdvertisement(id);

    }

    void deleteJobAdvertisementTest(int userId) {
        JobAdvertisementDTOImpl dto = builder.buildDefaultUser().done();
        int id = createAdvertisement(userId, dto);
        advertisementService.deleteJobAdvertisement(id);
        assertNull(advertisementService.readJobAdvertisement(id));
    }

    void fieldCantHaveThisValue(int userId, JobAdvertisementDTOImpl dto) {
        assertThrows(Exception.class, () -> {
            int id = createAdvertisement(userId, dto);
            advertisementService.deleteJobAdvertisement(id);
        });
    }


}