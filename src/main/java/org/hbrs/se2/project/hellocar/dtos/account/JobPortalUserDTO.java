package org.hbrs.se2.project.hellocar.dtos.account;

import org.hbrs.se2.project.hellocar.dtos.UserDTO;

public interface JobPortalUserDTO extends UserDTO {
    public String getGender();

    public String getStreet();

    public String getStreetNumber();

    public String getZipCode();

    public String getCity();
}

