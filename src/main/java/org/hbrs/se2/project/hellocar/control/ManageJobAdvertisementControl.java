package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.builders.JobAdvertisementDTOBuilder;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.AbstractJobAdvertisementFactory;
import org.hbrs.se2.project.hellocar.control.factories.JobAdvertisementFactoryImpl;
import org.hbrs.se2.project.hellocar.dao.JobAdvertisementDAO;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.entities.JobAdvertisement;
import org.hbrs.se2.project.hellocar.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManageJobAdvertisementControl {
    private AbstractJobAdvertisementFactory factory;

    @Autowired
    private JobAdvertisementRepository repository;

    public int createJobAdvertisement(JobAdvertisementDTO dto, int userId) throws DatabaseUserException {
        factory = new JobAdvertisementFactoryImpl();

        JobAdvertisement entity = factory.create();
        factory.setupEntityByDto(entity, dto);
        this.repository.save(entity);

        JobAdvertisementDAO dao = new JobAdvertisementDAO();
        int advertisementId = entity.getId();

        try {
            dao.setJobAdvertisementToUser(userId, advertisementId);
        } catch (DatabaseLayerException e) {
            this.handleDbException(e);
        }

        return advertisementId; // new primary key
    }

    public JobAdvertisementDTO readJobAdvertisement(int id) {
        Optional<JobAdvertisement> optional = this.repository.findById(id);

        if (optional.isPresent()) {
            JobAdvertisement entity = optional.get();

            JobAdvertisementDTOBuilder builder = new JobAdvertisementDTOBuilder();

            builder.buildId(entity.getId());
            builder.buildJobTitle(entity.getJobTitle());
            builder.buildJobType(entity.getJobType());
            builder.buildDescription(entity.getDescription());
            builder.buildBegin(entity.getBegin());
            builder.buildTags(entity.getTags());

            return builder.done();
        }

        return null;
    }

    public void updateJobAdvertisement(JobAdvertisementDTO dto, int id) {
        Optional<JobAdvertisement> optional = repository.findById(id);
        JobAdvertisement entity = null;

        if (optional.isPresent()) {
            entity = optional.get();
            factory.setupEntityByDto(entity, dto);
            repository.save(entity);
        }
    }

    public void deleteJobAdvertisement(int id) {
        repository.deleteById(id);
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
