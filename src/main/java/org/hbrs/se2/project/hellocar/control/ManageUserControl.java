package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.UserFactory;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.registration.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.registration.StudentDTO;
import org.hbrs.se2.project.hellocar.entities.Company;
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

    @Autowired
    private UserRepository userRepository;

    public int createUser(JobPortalUserDTO jobPortalUserDTO, String[] roles) throws DatabaseUserException {
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

        return newPrimaryKey;
    }

    public StudentDTO readStudentById(int id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        Student user = null;
        StudentDTOImpl studentDTO = null;

        if (userOptional.isPresent()) {
            user = (Student) userOptional.get();

            studentDTO = new StudentDTOImpl();

            // User
            studentDTO.setId(user.getId());
            studentDTO.setFirstName(user.getFirstName());
            studentDTO.setLastName(user.getLastName());
            studentDTO.setEmail(user.getEmail());
            studentDTO.setPassword(user.getPassword());
            studentDTO.setPhone(user.getPhone());
            studentDTO.setUserid(user.getUserid());
            studentDTO.setDateOfBirth(user.getDateOfBirth());

            // JobPortalUser
            studentDTO.setGender(user.getGender());
            studentDTO.setCity(user.getCity());
            studentDTO.setStreet(user.getStreet());
            studentDTO.setStreetNumber(user.getStreetNumber());
            studentDTO.setZipCode(user.getZipCode());
        }

        // Student
        // has nothing

        return studentDTO;
    }

    public CompanyDTO readCompanyById(int id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        Company companyEntity = null;
        CompanyDTOImpl companyDTO = null;

        if (userOptional.isPresent()) {
            companyEntity = (Company) userOptional.get();

            companyDTO = new CompanyDTOImpl();

            // User
            companyDTO.setId(companyEntity.getId());
            companyDTO.setFirstName(companyEntity.getFirstName());
            companyDTO.setLastName(companyEntity.getLastName());
            companyDTO.setEmail(companyEntity.getEmail());
            companyDTO.setPassword(companyEntity.getPassword());
            companyDTO.setPhone(companyEntity.getPhone());
            companyDTO.setUserid(companyEntity.getUserid());
            companyDTO.setDateOfBirth(companyEntity.getDateOfBirth());

            // JobPortalUser
            companyDTO.setGender(companyEntity.getGender());
            companyDTO.setCity(companyEntity.getCity());
            companyDTO.setStreet(companyEntity.getStreet());
            companyDTO.setStreetNumber(companyEntity.getStreetNumber());
            companyDTO.setZipCode(companyEntity.getZipCode());
        }

        // Company
        companyDTO.setCompanyName(companyEntity.getCompanyName());

        return companyDTO;
    }

    public List<UserDTO> readAllUsers() {
        //todo: implement this.repositoty.blaBla(...);
        return null;
    }

    public void updateUser(int id) { //one update methode for student and company?
        // not the userid!! it really is THE id (primary key)
        //todo: implement this.repositoty.blaBla(...);
    }

    public void updateStudent(int id) {
        // not the userid!! it really is THE id (primary key)
        //todo: implement this.repositoty.blaBla(...);
    }

    public void updateCompany(int id) {
        // not the userid!! it really is THE id (primary key)
        //todo: implement this.repositoty.blaBla(...);
    }

    public void deleteUser(int id) {
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
                throw new DatabaseUserException("A failure occured while...shit was done");
        }
    }
}
