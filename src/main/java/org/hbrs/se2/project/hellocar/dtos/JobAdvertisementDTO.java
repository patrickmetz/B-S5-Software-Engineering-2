package org.hbrs.se2.project.hellocar.dtos;

import java.time.LocalDate;

public interface JobAdvertisementDTO {

    int getJobAdvertisementId();

    String getJobTitle();

    String getJobType();

    String getDescription();

    LocalDate getBegin();

    String getTags();

    int getId();
}
