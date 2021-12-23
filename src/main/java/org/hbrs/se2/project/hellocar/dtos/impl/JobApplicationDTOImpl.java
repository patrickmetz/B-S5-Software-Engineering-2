package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;

public class JobApplicationDTOImpl implements JobApplicationDTO {

    private String text;
    private byte[] resume;

    @Override
    public String getText() { return text; }


    public void setText(String text) { this.text = text; }

    @Override
    public byte[] getResume() { return resume; }

    public void setResume(byte[] resume) { this.resume = resume; }
}
