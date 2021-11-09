package org.hbrs.se2.project.hellocar.test;

import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CarDTO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

@SpringBootTest
class HellocarApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolleRepository roleRepository;

    @Autowired
    private CarRepository carRepository;

    private User testUser;
    private Student testStudent;

    @BeforeEach
    private void setUp() {
        // User
        testUser = new User();
        testUser.setUserid("TestID");
        testUser.setEmail("test@test.de");
        testUser.setPassword("TestPasswort");
        testUser.setFirstName("TestVorname");
        testUser.setLastName("TestNachname");
        userRepository.save(testUser);

        // Student
        testStudent = new Student();
        testStudent.setUserid("TestStudentID");
        testStudent.setEmail("test2@test.de");
        testStudent.setPassword("TestStudentPasswort");
        testStudent.setFirstName("TestStudentVorname");
        testStudent.setLastName("TestStudentNachname");
        userRepository.save(testStudent);
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
    }

    @Test
    void testUserDTOfindUserByUseridAndPassword() {
        assertNotNull(testUser);
        UserDTO userDTO = userRepository.findUserByUseridAndPassword(testUser.getUserid() , testUser.getPassword());
        assertNotNull(userDTO);
        assertEquals(testUser.getFirstName(), userDTO.getFirstName());
        assertEquals(testUser.getLastName(), userDTO.getLastName());
    }

    @Test
    void testFindUserWithJDBC() {
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

    @Test
    void testFindStudents() {
        List<Student> studentList = userRepository.findStudents();
        Student foundStudent = null;
        for( Student s : studentList )
        {
            if( s.getId() == testStudent.getId() )
            {
                System.out.println("Found");
                if( foundStudent == null )
                    foundStudent = s;
                else
                    assert(false); // Mehr als 1 objekt gefunden
            }
        }
        assertNotNull(foundStudent);
        assertEquals(testStudent.getFirstName(), foundStudent.getFirstName());
        assertEquals(testStudent.getLastName(), foundStudent.getLastName());
    }

    @Test
    void testUserListIntegrity() {
        List<UserDTO> userList = userRepository.getUsers();
        List<String> emailList = new ArrayList<String>();
        List<String> userIdList = new ArrayList<String>();

        for( UserDTO u : userList )
        {
            assertFalse("UserId ist leer", u.getUserid() != null && u.getUserid().isEmpty());
            assertFalse("E-Mail ist leer",u.getEmail() != null && u.getEmail().isEmpty());

            // Prüfe auf mehrfache emails
            assertFalse("E-Mail '"+u.getEmail()+"' kommt mehrfach vor", emailList.contains(u.getEmail()) );
            emailList.add( u.getEmail() );

            // Prüfe auf mehrfache userids
            assertFalse("UserId '"+u.getUserid()+"' kommt mehrfach vor", userIdList.contains(u.getUserid()) );
            userIdList.add( u.getUserid() );
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
    void testUserDTOByAttribute() {
        UserDTO personDTO = userRepository.getUserByOccupation("devaccount").get(0);
        System.out.println(personDTO.getFirstName());
        assertEquals("Team", personDTO.getFirstName());
        assertEquals(2 , personDTO.getId());
    }

    @Test
    void testUserDTOAndItsRoles() {
        UserDTO userDTO = userRepository.findUserByUseridAndPassword("teamx" , "123");
        System.out.println(userDTO.getFirstName());
        assertEquals("Team", userDTO.getFirstName());
        List<RolleDTO> list = userDTO.getRoles();
        System.out.println(list.size());
        assertEquals(1 , list.size());
    }

    @Test
    void testPersonLoad() {
        Optional<User> wrapper = userRepository.findById(1);
        if ( wrapper.isPresent() ) {
            User user = wrapper.get();
            assertEquals("Alda" , user.getLastName());
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

    @Test
    void testFindCarsAndTheirUsers() {
        List<Object[]> list = this.carRepository.findAllCarsAndTheirUsers();
        for (Object[] item: list) {
            System.out.println("Brand: " + item[0] );
            System.out.println("Model: " + item[1] );
            System.out.println("Price: " + item[2] );
            System.out.println("First Name: " + item[3] );
            System.out.println("Last Name: " + item[4] );
        }
        // Todo: Definition von passenden Assertions
    }

    @Test
    void testFindCarsWithMostImportantValues() {
        List<CarDTO> list = this.carRepository.findCarsByDateIsNotNull();
        for (CarDTO item: list) {
            System.out.println("Brand: " + item.getBrand() );
            System.out.println("Model: " + item.getModel() );
            System.out.println("Price: " + item.getPrice() );
            System.out.println("Phone: " + item.getPhone() );
        }
        // Todo: Definition von passenden Assertions
    }
}
