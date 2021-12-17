package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.entities.JobAdvertisement;

public class JobAdvertisementFactoryImpl extends AbstractJobAdvertisementFactory {
    @Override
    public JobAdvertisement create() {
        return new JobAdvertisement();
    }
}
