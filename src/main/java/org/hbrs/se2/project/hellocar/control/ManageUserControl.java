package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.UserFactory;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.entities.User;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageUserControl {

    @Autowired
    private UserRepository userRepository;

    public void createUser(JobPortalUserDTO jobPortalUserDTO, String[] roles) throws DatabaseUserException {
        validatelUser(jobPortalUserDTO);

        User userEntity = UserFactory.createUser(jobPortalUserDTO);
        this.userRepository.save(userEntity);

        int newPrimaryKey = userEntity.getId();
        RolleDAO rolleDAO = new RolleDAO();

        for (String role : roles) {
            try {
                rolleDAO.setRoleToUser(newPrimaryKey, role);
            } catch (DatabaseLayerException e) {
                handleDbException(e);
            }
        }
    }

    public UserDTO readUser(String id) {
        //todo: implement this.repositoty.blaBla(...);
        return null;
    }

    public List<UserDTO> readAllUsers() {
        //todo: implement this.repositoty.blaBla(...);
        return null;
    }

    public void updateUser(String id) {
        // not the userid!! it really is THE id (primary key)
        //todo: implement this.repositoty.blaBla(...);
    }

    public void deleteUser(String id) {
        // not the userid!! it really is THE id (primary key)
        //todo: implement this.repositoty.blaBla(...);
    }

    private void validatelUser(JobPortalUserDTO jobPortalUserDTO) {
        // todo: implement server side validation here
    }

    private void handleDbException(DatabaseLayerException e) throws DatabaseUserException {
        // chain of responsibility pattern
        String reason = e.getReason();

        switch (reason) {
            case Globals.Errors.NOUSERFOUND:
                throw new DatabaseUserException("No User could be found! Please check your credentials!");
            case (Globals.Errors.SQLERROR):
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            case (Globals.Errors.DATABASE):
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC. " +
                        "Please contact the admin");
            default:
                throw new DatabaseUserException("A failure occured while");
        }
    }
}
