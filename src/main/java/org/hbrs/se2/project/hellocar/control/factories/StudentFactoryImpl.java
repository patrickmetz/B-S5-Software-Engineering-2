package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;
import org.hbrs.se2.project.hellocar.entities.JobPortalUser;
import org.hbrs.se2.project.hellocar.entities.Student;

public class StudentFactoryImpl extends AbstractJobPortalUserFactory {
    public Student create() {
        return new Student();
    }

    @Override
    public void setupEntityByDto(JobPortalUser entity, JobPortalUserDTO dto) {
        super.setupEntityByDto(entity, dto);

        ((Student) entity).setStudyCourse(
                ((StudentDTO) dto).getStudyCourse()
        );

        ((Student) entity).setSpezialization(
                ((StudentDTO) dto).getSpecialization()
        );

        ((Student) entity).setSemester(
                ((StudentDTO) dto).getSemester()
        );
    }
}
