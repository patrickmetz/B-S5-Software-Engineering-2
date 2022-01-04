package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.builders.JobAdvertisementDTOBuilder;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.control.factories.AbstractJobAdvertisementFactory;
import org.hbrs.se2.project.hellocar.control.factories.JobAdvertisementFactoryImpl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.entities.JobAdvertisement;
import org.hbrs.se2.project.hellocar.entities.User;
import org.hbrs.se2.project.hellocar.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ManageJobAdvertisementControl {
    private AbstractJobAdvertisementFactory factory;

    @Autowired
    private JobAdvertisementRepository repository;

    @Autowired
    private UserRepository userRepository;

    public int createJobAdvertisement(JobAdvertisementDTO dto, int userId) throws DatabaseUserException {
        factory = new JobAdvertisementFactoryImpl();

        JobAdvertisement entity = factory.create();
        factory.setupEntityByDto(entity, dto);
        entity.setUser(this.getUser(userId));

        this.repository.save(entity);

        return entity.getJobAdvertismentId(); // new primary key
    }

    private User getUser(int userId){
        User user = null;

        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        return user;
    }

    public JobAdvertisementDTO readJobAdvertisement(int id) {
        Optional<JobAdvertisement> optional = this.repository.findById(id);

        if (optional.isPresent()) {
            JobAdvertisement entity = optional.get();

            JobAdvertisementDTOBuilder builder = new JobAdvertisementDTOBuilder();

            builder.buildId(entity.getJobAdvertismentId());
            builder.buildUser(entity.getUser());
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

    public List<JobAdvertisementDTO> readAllJobAdvertisements() {
        List<JobAdvertisement> jobAdvertisements = this.repository.getJobAdvertisements();
        List<JobAdvertisementDTO> jobAdvertisementDTOS = new ArrayList<>();

        jobAdvertisements.forEach(entity -> {
            JobAdvertisementDTOBuilder builder = new JobAdvertisementDTOBuilder();
            builder.buildId(entity.getJobAdvertismentId());
            builder.buildUser(entity.getUser());
            builder.buildJobTitle(entity.getJobTitle());
            builder.buildJobType(entity.getJobType());
            builder.buildDescription(entity.getDescription());
            builder.buildBegin(entity.getBegin());
            builder.buildTags(entity.getTags());
            jobAdvertisementDTOS.add(builder.done());
        });

        return jobAdvertisementDTOS;
    }

}
