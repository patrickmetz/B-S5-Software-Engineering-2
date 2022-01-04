package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.control.builders.JobApplicationDTOBuilder;
import org.hbrs.se2.project.hellocar.control.factories.AbstractJobApplicationFactory;
import org.hbrs.se2.project.hellocar.control.factories.JobApplicationFactoryImpl;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.entities.JobApplication;
import org.hbrs.se2.project.hellocar.entities.User;
import org.hbrs.se2.project.hellocar.repository.JobApplicationRepository;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ManageJobApplicationControl {
    private AbstractJobApplicationFactory factory;

    @Autowired
    private JobApplicationRepository repository;

    @Autowired
    private UserRepository userRepository;

    public int createJobApplication(JobApplicationDTO dto, int userId) {
        factory = new JobApplicationFactoryImpl();

        JobApplication entity = factory.create();
        factory.setupEntityByDto(entity, dto);
        entity.setUser(this.getUser(userId));

        this.repository.save(entity);

        return entity.getJobApplicationId(); // new primary key
    }

    public JobApplicationDTO readJobApplication(int id) {
        Optional<JobApplication> optional = this.repository.findById(id);

        if (optional.isPresent()) {
            JobApplication entity = optional.get();

            JobApplicationDTOBuilder builder = new JobApplicationDTOBuilder();

            builder.buildId(entity.getJobApplicationId());
            builder.buildText(entity.getText());
            builder.buildResume(entity.getResume());

            return builder.done();
        }

        return null;
    }

    public void updateJobApplication(JobApplicationDTO dto, int id) {
        Optional<JobApplication> optional = repository.findById(id);
        JobApplication entity = null;

        if (optional.isPresent()) {
            entity = optional.get();
            factory.setupEntityByDto(entity, dto);
            repository.save(entity);
        }
    }

    public void deleteJobApplication(int id) {
        repository.deleteById(id);
    }

    public List<JobApplicationDTO> readAllJobApplications() {
        List<JobApplication> jobApplications = this.repository.getJobApplications();
        List<JobApplicationDTO> jobApplicationDTOS = new ArrayList<>();

        jobApplications.forEach(entity -> {
            JobApplicationDTOBuilder builder = new JobApplicationDTOBuilder();
            builder.buildId(entity.getJobApplicationId());
            builder.buildText(entity.getText());
            builder.buildResume(entity.getResume());

            jobApplicationDTOS.add(builder.done());
        });

        return jobApplicationDTOS;
    }

    private User getUser(int userId){
        User user = null;

        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        return user;
    }
}
