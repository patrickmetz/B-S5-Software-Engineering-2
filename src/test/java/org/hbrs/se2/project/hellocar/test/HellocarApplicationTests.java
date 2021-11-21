package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CarDTO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Rolle;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.entities.User;
import org.hbrs.se2.project.hellocar.repository.CarRepository;
import org.hbrs.se2.project.hellocar.repository.RolleRepository;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@SpringBootTest
class HellocarApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolleRepository roleRepository;

    private User testUser;
    private Student testStudent;
    private Company testCompany;

    @BeforeEach
    private void setUp() {
        // User
        testUser = new User();
        testUser.setUserid("TestID");
        testUser.setEmail("test@test.de");
        testUser.setPassword("TestPasswort");
        testUser.setFirstName("TestVorname");
        testUser.setLastName("TestNachname");
        testUser.setOccupation("TestOccupation");
        userRepository.save(testUser);

        // Student
        testStudent = new Student();
        testStudent.setUserid("TestStudentID");
        testStudent.setEmail("test2@test.de");
        testStudent.setPassword("TestStudentPasswort");
        testStudent.setFirstName("TestStudentVorname");
        testStudent.setLastName("TestStudentNachname");
        testStudent.setDateOfBirth(LocalDate.now());
        userRepository.save(testStudent);

        // Company
        testCompany = new Company();
        testCompany.setUserid("TestCompanyID");
        testCompany.setEmail("test3@test.de");
        testCompany.setPassword("TestCompanyPasswort");
        testCompany.setFirstName("TestCompanyVorname");
        testCompany.setLastName("TestCompanyNachname");
        testCompany.setCompanyName("TestCompany");
        testCompany.setDateOfBirth(LocalDate.now());
        userRepository.save(testCompany);
    }

    @AfterEach
    private void cleanupTest() {
        // User
        if( testUser != null ){
            userRepository.delete(testUser);
            testUser = null;
        }
        // Student
        if( testStudent != null ){
            userRepository.delete(testStudent);
            testStudent = null;
        }
        // Company
        if( testCompany != null ){
            userRepository.delete(testCompany);
            testCompany = null;
        }
    }

    @Test
    void testUserDTOfindUserByUseridAndPassword() {
        assertNotNull(testUser);
        UserDTO userDTO = userRepository.findUserByUseridAndPassword(testUser.getUserid() , testUser.getPassword());
        assertNotNull(userDTO);
        assertEquals(testUser.getFirstName(), userDTO.getFirstName());
        assertEquals(testUser.getLastName(), userDTO.getLastName());
        assertEquals(testUser.getDateOfBirth(), userDTO.getDateOfBirth());
    }

    @Test
    void testUserDTOgetUserByOccupation() {
        List<UserDTO> userDTOList = userRepository.getUserByOccupation(testUser.getOccupation());
        assertEquals(userDTOList.size(), 1);
        UserDTO userDTO = userDTOList.get(0);
        assertEquals(testUser.getFirstName(), userDTO.getFirstName());
        assertEquals(testUser.getLastName(), userDTO.getLastName());
        assertEquals(testUser.getDateOfBirth(), userDTO.getDateOfBirth());
    }

    @Disabled
    void testFindUserWithJDBC() {
        assertNotNull(testUser);
        UserDAO userDAO = new UserDAO();
        try {
            UserDTO userDTO = userDAO.findUserByUseridAndPassword(testUser.getUserid() , testUser.getPassword());
            assertNotNull(userDTO);
            assertEquals(testUser.getFirstName(), userDTO.getFirstName());
            assertEquals(testUser.getLastName(), userDTO.getLastName());
        } catch (DatabaseLayerException e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Disabled
    void testUserDatabaseIntegrity() {
        List<UserDTO> userList = userRepository.getUsers();
        List<String> emailList = new ArrayList<String>();
        List<String> userIdList = new ArrayList<String>();

        for( UserDTO u : userList )
        {
            assertNotNull(u);

            assertFalse("UserId ist leer", u.getUserid() != null && u.getUserid().isEmpty());
            assertFalse("E-Mail ist leer",u.getEmail() != null && u.getEmail().isEmpty());

            // Prüfe auf mehrfache emails
            assertFalse("E-Mail '"+u.getEmail()+"' kommt mehrfach vor", emailList.contains(u.getEmail()) );
            emailList.add( u.getEmail() );

            // Prüfe auf mehrfache userids
            assertFalse("UserId '"+u.getUserid()+"' kommt mehrfach vor", userIdList.contains(u.getUserid()) );
            userIdList.add( u.getUserid() );

            // Nicht null
            assertNotNull(u.getPassword());

            assertNotNull(u.getFirstName()); // Throws?
            assertNotNull(u.getLastName()); // Throws?
        }
    }

    @Test
    void testRolesOfUser() {
        Optional<User> wrapper = userRepository.findById(1);
        if ( wrapper.isPresent() ) {
            User user = wrapper.get();
            System.out.println("User: " + user.getLastName());
            List<Rolle> list = user.getRoles();
            assertEquals("Anzahl der Rollen", 2 , list.size() );
            Rolle rolle1 = list.get(0);
            assertEquals("admin" , rolle1.getBezeichhnung() );
        }
    }

    @Test
    void testRoleRepository() {
        List<Rolle> list = roleRepository.findAll();
        String[] soll = { "admin" , "user", "student", "company" };
        String[] ist = {};

        for (Rolle r : list) {
            System.out.println("Rolle: " + r.getBezeichhnung() );
            ist = Utils.append( ist , r.getBezeichhnung() );
        }
        assertArrayEquals( soll , ist );
    }
}
