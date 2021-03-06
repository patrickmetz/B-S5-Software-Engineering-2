package org.hbrs.se2.project.hellocar.repository;

import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.entities.*;
import org.hbrs.se2.project.hellocar.dtos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
/**
 * JPA-Repository für die Abfrage von registrierten User. Die Bezeichnung einer Methode
 * bestimmt dabei die Selektionsbedingung (den WHERE-Teil). Der Rückgabewert einer
 * Methode bestimmt den Projectionsbedingung (den SELECT-Teil).
 * Mehr Information über die Entwicklung von Queries in JPA:
 * https://www.baeldung.com/spring-data-jpa-projections
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUserid(String userid);
    Optional<User> findUserByEmail(String email);

    // SELECT firstname, lastname, id
    // FROM User p
    // WHERE p.occupation = [StringValueOf( occupation )]
    List<UserDTO> getUserByOccupation( String occupation );

    // SELECT firstname, lastname, id
    // FROM User p
    // WHERE p.userid = [StringValueOf( userid )] AND p.password = [StringValueOf( password )]
    JobPortalUserDTO findUserByUseridAndPassword (String userid , String password);

    @Query(
            value = "SELECT * FROM carlook.user WHERE dtype = 'student'",
            nativeQuery = true
    )
    List<Student> findStudents();

    @Query(
            value = "SELECT * FROM carlook.user WHERE dtype = 'company'",
            nativeQuery = true
    )
    List<Company> findCompanies();

    @Query(
            value = "SELECT * FROM carlook.user",
            nativeQuery = true
    )
    List<UserDTO> getUsers();

}
