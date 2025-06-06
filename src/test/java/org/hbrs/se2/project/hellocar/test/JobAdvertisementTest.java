package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.builders.CompanyDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.JobAdvertisementDTOBuilder;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
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
    // int studentId;
    int companyId;

    @BeforeEach
    void setUp() {
        builder = new JobAdvertisementDTOBuilder();

        //  StudentDTOBuilder builderA = new StudentDTOBuilder();
        CompanyDTOBuilder builderB = new CompanyDTOBuilder();

        //  StudentDTOImpl dtoA = builderA.buildDefaultUser().done();
        CompanyDTOImpl dtoB = builderB.buildDefaultUser().done();

        try {
            //studentId = userService.createUser(dtoA, STUDENT_ROLES);
            companyId = userService.createUser(dtoB, COMPANY_ROLES);
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @AfterEach
    void tearDown() {
        builder = null;
        // userService.deleteUser(studentId);
        userService.deleteUser(companyId);
    }


    @Test
    void createJobAdvertisement() {         // auch für Studenten?
        JobAdvertisementDTOImpl dtoA = builder.buildDefaultAdvertisement().done();
        int id = createAdvertisement(companyId, dtoA);
        JobAdvertisementDTO dtoB = advertisementService.readJobAdvertisement(id);
        assertEquals(dtoB.getJobAdvertisementId(), id);
        advertisementService.deleteJobAdvertisement(id);
    }

    @Test
    void updateJobAdvertisement() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().done();
        int id = createAdvertisement(companyId, dto);

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


    @Test
    void deleteJobAdvertisement() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().done();
        int id = createAdvertisement(companyId, dto);
        advertisementService.deleteJobAdvertisement(id);
        assertNull(advertisementService.readJobAdvertisement(id));

    }

    @Test
    void jobTitleCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().buildJobTitle(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void jobTypeCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().buildJobType(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void beginCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().buildBegin(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void tagsCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().buildTags(null).done();
        fieldCantHaveThisValue(companyId, dto);
    }

    @Test
    void descriptionCantBeNull() {
        JobAdvertisementDTOImpl dto = builder.buildDefaultAdvertisement().buildDescription(null).done();
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


    void fieldCantHaveThisValue(int userId, JobAdvertisementDTOImpl dto) {
        assertThrows(Exception.class, () -> {
            int id = createAdvertisement(userId, dto);
            advertisementService.deleteJobAdvertisement(id);
        });
    }


}