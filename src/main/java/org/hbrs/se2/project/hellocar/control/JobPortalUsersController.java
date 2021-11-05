package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.entities.JobPortalUser;
import org.hbrs.se2.project.hellocar.repository.account.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobPortalUsersController {

    @Autowired
    private UsersRepository usersRepository;

    public <T extends JobPortalUser> T createPortalUser(T portalUser) {
        return usersRepository.save(portalUser);
    }

}
