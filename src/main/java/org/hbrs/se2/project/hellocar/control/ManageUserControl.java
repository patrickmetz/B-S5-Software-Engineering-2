package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.UserEntityFactory;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
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
import java.util.Optional; // ?

@Component
public class ManageUserControl {

    @Autowired
    private UserRepository userRepository;

    public int createUser(JobPortalUserDTO userDTO, String[] roles) throws DatabaseUserException {

        //todo: use vincent's builder here
        User userEntity = UserEntityFactory.create(userDTO);
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

    public StudentDTO readStudent(int id) {
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

    public CompanyDTO readCompany(int id) {
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

            //Company
            companyDTO.setCompanyName(companyEntity.getCompanyName());
        }

        return companyDTO;
    }

    public List<UserDTO> readAllUsers() {
        //todo: implement
        return null;
    }

    public User readUserByUserId(String userid) {
        //todo: return dto instead of entity!
        Optional<User> userOptional = this.userRepository.findUserByUserid(userid);
        return userOptional.orElse(null);
    }

    public User readUserByEmail(String email) {
        //todo: return dto instead of entity!
        Optional<User> userOptional = this.userRepository.findUserByEmail(email);
        return userOptional.orElse(null);
    }

    public void updateStudent(int id, StudentDTO dto) {
        //todo: provide only one shared method for both student AND company
        Optional<User> optional = this.userRepository.findById(id);
        Student entity = null;

        if (optional.isPresent()) {
            entity = (Student) optional.get();

            setUserPartsOfEntity(entity, dto);
            setStudentPartsOfEntity(entity, dto);

            this.userRepository.save(entity);
        }
    }

    public void updateCompany(int id, CompanyDTO dto) {
        //todo: provide only one shared method for both student AND company
        Optional<User> optional = this.userRepository.findById(id);

        Company entity = null;

        if (optional.isPresent()) {
            entity = (Company) optional.get();

            setUserPartsOfEntity(entity, dto);
            setCompanyPartsOfEntity(entity, dto);

            this.userRepository.save(entity);
        }
    }

    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
    }

    private void setUserPartsOfEntity(JobPortalUser entity, JobPortalUserDTO dto) {
        entity.setUserid(dto.getUserid()); // it's the username, not the primary key
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());

        entity.setGender(dto.getGender());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());

        entity.setStreet(dto.getStreet());
        entity.setStreetNumber(dto.getStreetNumber());
        entity.setZipCode(dto.getZipCode());
        entity.setCity(dto.getCity());

        entity.setPhone(dto.getPhone());
        entity.setDateOfBirth(dto.getDateOfBirth());
    }

    private void setCompanyPartsOfEntity(Company entity, CompanyDTO dto) {
        entity.setCompanyName(dto.getCompanyName());
    }

    private void setStudentPartsOfEntity(Student entity, StudentDTO dto) {
        // there are no student specific entity attributes, yet
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
