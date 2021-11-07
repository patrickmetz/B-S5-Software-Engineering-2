package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.repository.account.JobPortalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobPortalUsersController {

    @Autowired
    private JobPortalUserRepository jobPortalUserRepository;

    public <T extends org.hbrs.se2.project.hellocar.entities.JobPortalUser> T createPortalUser(T portalUser) {
        return jobPortalUserRepository.save(portalUser);
    }

}
