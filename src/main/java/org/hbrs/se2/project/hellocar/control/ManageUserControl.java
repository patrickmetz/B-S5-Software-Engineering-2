package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.builders.CompanyDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.JobPortalUserDTOBuilder;
import org.hbrs.se2.project.hellocar.control.builders.StudentDTOBuilder;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.AbstractJobPortalUserFactory;
import org.hbrs.se2.project.hellocar.control.factories.CompanyFactoryImpl;
import org.hbrs.se2.project.hellocar.control.factories.StudentFactoryImpl;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.JobPortalUser;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.entities.User;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ManageUserControl {
    private AbstractJobPortalUserFactory factory;

    @Autowired
    private UserRepository userRepository;

    public int createUser(JobPortalUserDTO userDTO, String[] roles) throws DatabaseUserException {
        JobPortalUser userEntity = null;

        if (userDTO instanceof StudentDTO) {
            factory = new StudentFactoryImpl();
        } else if (userDTO instanceof CompanyDTO) {
            factory = new CompanyFactoryImpl();
        }

        userEntity = factory.create();
        factory.setupEntityByDto(userEntity, userDTO);

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

        return newPrimaryKey;
    }

    public JobPortalUserDTO readUser(int id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        JobPortalUser user = null;

        if (userOptional.isPresent()) {
            user = (JobPortalUser) userOptional.get();

            JobPortalUserDTOBuilder builder = null;

            if (user instanceof Company) {
                //company parts
                builder = new CompanyDTOBuilder();

                ((CompanyDTOBuilder) builder).buildCompanyName(
                        ((Company) user).getCompanyName()
                );

                ((CompanyDTOBuilder) builder).buildWebSite(
                        ((Company) user).getWebSite()
                );
            } else if (user instanceof Student) {
                // student parts
                builder = new StudentDTOBuilder();

                ((StudentDTOBuilder) builder).buildStudyCourse(
                        ((Student) user).getStudyCourse()
                );

                ((StudentDTOBuilder) builder).buildSpecialization(
                        ((Student) user).getSpezialization()
                );

                ((StudentDTOBuilder) builder).buildSemester(
                        ((Student) user).getSemester()
                );

                ((StudentDTOBuilder) builder).buildDegree(
                        ((Student) user).getDegree()
                );
            }

            if (builder != null) {
                // job portal user parts
                builder
                        .buildGender(user.getGender())
                        .buildCity(user.getCity())
                        .buildStreet(user.getStreet())
                        .buildStreetNumber(user.getStreetNumber())
                        .buildZipCode(user.getZipCode())
                        .buildAbout(user.getAbout())
                        .buildProfilePicture(user.getProfilePicture());

                // user parts
                builder
                        .buildId(user.getId())
                        .buildFirstname(user.getFirstName())
                        .buildLastname(user.getLastName())
                        .buildEmail(user.getEmail())
                        .buildPassword(user.getPassword())
                        .buildPhone(user.getPhone())
                        .buildUserId(user.getUserid())
                        .buildDateOfBirth(user.getDateOfBirth());

                return builder.done();
            }
        }

        return null;
    }

    public List<UserDTO> readAllUsers() {
        //todo: implement
        return null;
    }

    public boolean existsUserId(String userid) {
        Optional<User> userOptional = this.userRepository.findUserByUserid(userid);

        return userOptional.isPresent();
    }

    public boolean existsEmail(String email) {
        Optional<User> userOptional = this.userRepository.findUserByEmail(email);

        return userOptional.isPresent();
    }

    public void updateUser(int id, JobPortalUserDTO dto) {
        Optional<User> optional = this.userRepository.findById(id);
        JobPortalUser entity = null;

        if (optional.isPresent()) {
            entity = (JobPortalUser) optional.get();

            factory.setupEntityByDto(entity, dto);

            this.userRepository.save(entity);
        }
    }

    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
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
                throw new DatabaseUserException("A failure occured while...shit was done");
        }
    }

}
