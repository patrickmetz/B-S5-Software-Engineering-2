package org.hbrs.se2.project.hellocar.repository.account;

import org.hbrs.se2.project.hellocar.entities.JobPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JobPortalUserRepository extends JpaRepository<JobPortalUser, Integer> {

}
