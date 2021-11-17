package org.hbrs.se2.project.hellocar.dtos.impl.account;

public class JobPortalUserBuilder extends UserBuilder{

    public JobPortalUserBuilder(JobPortalUserDTOImpl jobPortalUserDTOImpl) {
        super(jobPortalUserDTOImpl);
    }
    @Override
    public JobPortalUserBuilder buildDefaultUser(){
        super.buildDefaultUser();
        ((JobPortalUserDTOImpl)userDTOImpl).setGender("male");
        ((JobPortalUserDTOImpl)userDTOImpl).setStreet("testStreet");
        ((JobPortalUserDTOImpl)userDTOImpl).setStreetNumber("53b");
        ((JobPortalUserDTOImpl)userDTOImpl).setZipCode("50823");
        ((JobPortalUserDTOImpl)userDTOImpl).setCity("cologne");
        return this;
    }

    public JobPortalUserBuilder buildGender(String gender){
        ((JobPortalUserDTOImpl)userDTOImpl).setGender(gender);
        return this;
    }

    public JobPortalUserBuilder builStreet(String street){
        ((JobPortalUserDTOImpl) super.userDTOImpl).setStreetNumber(street);
        return this;
    }

    public JobPortalUserBuilder buildStreetNumber(String streetNumber){
        ((JobPortalUserDTOImpl) super.userDTOImpl).setStreetNumber(streetNumber);
        return this;
    }

    public JobPortalUserBuilder buildZipCode(String zipCode){
        ((JobPortalUserDTOImpl) super.userDTOImpl).setZipCode(zipCode);
        return this;
    }

    public JobPortalUserBuilder buildCity(String city){
        ((JobPortalUserDTOImpl) super.userDTOImpl).setCity(city);
        return this;
    }
    @Override
    public JobPortalUserDTOImpl done(){

        return (JobPortalUserDTOImpl)userDTOImpl;
    }

}





