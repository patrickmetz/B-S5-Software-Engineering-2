package org.hbrs.se2.project.hellocar.views.jobAdvertisement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;

@CssImport("./styles/views/jobAdvertisement/jobAdvertisement.css")
public abstract class JobAdvertisementBaseView extends VerticalLayout {

    protected Binder<JobAdvertisementDTOImpl> binder;
    protected ManageJobAdvertisementControl jobAdService;

    protected H3 pageTitle;

    protected TextField jobTitle;
    protected TextField jobType;
    protected TextArea description;
    protected DatePicker begin;
    protected TextField tags;

    public JobAdvertisementBaseView(ManageJobAdvertisementControl jobAdService) {
        this.jobAdService = jobAdService;
        binder = new Binder<>(JobAdvertisementDTOImpl.class);
        setupElements();
    }

    protected void setupElements() {
        addClassName("job-advertisement");

        pageTitle = new H3("Job Advertisement");
        pageTitle.addClassName("page-title");

        jobTitle = new TextField("Title");
        jobTitle.addClassName("job-title");

        jobType = new TextField("Type");
        jobType.addClassName("job-type");

        description = new TextArea("Description");
        description.addClassName("job-description");

        begin = new DatePicker("Starting Date");
        begin.addClassName("job-begin");

        tags = new TextField("Keywords");
        tags.setPlaceholder("java design developer frontend backend etc...");
        tags.addClassName("job-tags");
    }

    protected abstract FormLayout setupForm();

    protected void setupValidation() {

    }

    protected JobPortalUserDTO getCurrentUser() {
        return (JobPortalUserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

}
