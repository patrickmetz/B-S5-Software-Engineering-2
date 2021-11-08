package org.hbrs.se2.project.hellocar.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("student")
public class Student extends JobPortalUser {

}
