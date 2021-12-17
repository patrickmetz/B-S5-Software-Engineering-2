package org.hbrs.se2.project.hellocar.dtos;

import java.time.LocalDate;

public interface JobAdvertisementDTO {

    public int getId();

    public String getJobTitle();

    public String getJobType();

    public String getDescription();

    public LocalDate getBegin();

    public String getTags();
}
