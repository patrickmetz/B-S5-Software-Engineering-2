package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.control.ManageJobApplicationControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.builders.CompanyDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.JobApplicationDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.StudentDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.JobAdvertisementDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.JobApplicationDTOBuilder;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.JobApplicationDTOImpl;
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
class JobApplicationTest {

    @Autowired
    private ManageJobApplicationControl applicationService;

    @Autowired
    private ManageUserControl userService;

    private static final String[] STUDENT_ROLES
            = new String[]{Globals.Roles.USER, Globals.Roles.STUDENT};


    JobApplicationDTOBuilder builder;
    int studentId;

    @BeforeEach
    void set() {
        builder = new JobApplicationDTOBuilder();

        StudentDTOBuilder builder = new StudentDTOBuilder();


        StudentDTOImpl dto = builder.buildDefaultUser().done();

        try {
            studentId = userService.createUser(dto, STUDENT_ROLES);

        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }


    }

    @AfterEach
    void reset() {
        builder = null;
        userService.deleteUser(studentId);

    }

    @Test
    void createJobApplication() {
        JobApplicationDTOImpl dtoA = builder.buildDefaultUser().done();
        int id = createApplication(studentId, dtoA);
        JobApplicationDTO dtoB = applicationService.readJobApplication(id);
        assertEquals(dtoB.getId(), id);
        applicationService.deleteJobApplication(id);
    }

    @Test
    void updateJobApplication() {
        JobApplicationDTOImpl dto = builder.buildDefaultUser().done();
        int id = createApplication(studentId, dto);

        dto = builder.buildText(dto.getText() + "update").done();

        applicationService.updateJobApplication(dto, id);

        JobApplicationDTO updated = applicationService.readJobApplication(id);

        assertEquals(dto.getText(), updated.getText());

        applicationService.deleteJobApplication(id);
    }

    @Test
    void deleteJobApplication(){
        JobApplicationDTOImpl dto = builder.buildDefaultUser().done();
        int id = createApplication(studentId, dto);
        applicationService.deleteJobApplication(id);
        assertNull(applicationService.readJobApplication(id));
    }

    @Test
    void testCantBeNull(){
        JobApplicationDTOImpl dto = builder.buildDefaultUser().buildText(null).done();
        fieldCantHaveThisValue(studentId, dto);
    }

    Integer createApplication(int studentId, JobApplicationDTO dto) {

        return applicationService.createJobApplication(dto, studentId);
    }

    void fieldCantHaveThisValue(int studentId, JobApplicationDTOImpl dto) {
        assertThrows(Exception.class, () -> {
            int id = createApplication(studentId, dto);
            applicationService.deleteJobApplication(id);
        });
    }


}
