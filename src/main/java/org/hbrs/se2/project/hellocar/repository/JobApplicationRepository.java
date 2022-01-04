package org.hbrs.se2.project.hellocar.repository;

import org.hbrs.se2.project.hellocar.entities.JobAdvertisement;
import org.hbrs.se2.project.hellocar.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    @Query(
            value = "SELECT * FROM carlook.job_application",
            nativeQuery = true
    )
    List<JobApplication> getJobApplications();

}
