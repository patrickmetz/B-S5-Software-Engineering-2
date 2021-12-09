package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.factories.CarEntityFactory;
import org.hbrs.se2.project.hellocar.dtos.CarDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.entities.Car;
import org.hbrs.se2.project.hellocar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageCarControl {

    @Autowired
    private CarRepository repository;


    public void createCar( CarDTO carDTO , UserDTO userDTO ) {
        // Hier könnte man noch die Gültigkeit der Daten überprüfen
        // check( carDTO );

        //Erzeuge ein neues Car-Entity konsistent über eine Factory
        Car carEntity = CarEntityFactory.createCar(  carDTO , userDTO  );

        // Abspeicherung des Entity in die DB
        this.repository.save( carEntity );
    }

    public List<CarDTO> readAllCars() {
        return repository.findCarsByDateIsNotNull();
    }

}
