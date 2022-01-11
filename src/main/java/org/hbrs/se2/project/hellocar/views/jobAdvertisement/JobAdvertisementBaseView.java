package org.hbrs.se2.project.hellocar.views.jobAdvertisement;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.jobAdvertisement.JobAdvertisementValidation;

import java.util.stream.Stream;

@CssImport("./styles/views/jobAdvertisement/jobAdvertisement.css")
public abstract class JobAdvertisementBaseView extends VerticalLayout implements AfterNavigationObserver
{
    protected Binder<JobAdvertisementDTOImpl> binder;
    protected ManageJobAdvertisementControl jobAdService;
    protected boolean rendered = false;

    protected H3 pageTitle;

    protected TextField jobTitle;
    protected TextField jobType;
    protected TextArea description;
    protected DatePicker begin;
    protected TextField tags;

    public JobAdvertisementBaseView(ManageJobAdvertisementControl jobAdService) {
        this.jobAdService = jobAdService;
        binder = new Binder<>(JobAdvertisementDTOImpl.class);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent)
    {
        if (rendered)
            return;

        setupElements();
        assembleView();

        rendered = true;
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

    protected abstract void assembleView();

    protected void setupValidation() {
        binder.forField(jobTitle)
                .withValidator(
                        JobAdvertisementValidation::notEmptyFieldValidator,
                        "Title " + JobAdvertisementValidation.IS_EMPTY_ERROR
                )
                .bind(JobAdvertisementDTOImpl::getJobTitle, JobAdvertisementDTOImpl::setJobTitle);

        binder.forField(jobType)
                .withValidator(
                        JobAdvertisementValidation::notEmptyFieldValidator,
                        "Type " + JobAdvertisementValidation.IS_EMPTY_ERROR
                )
                .bind(JobAdvertisementDTOImpl::getJobType, JobAdvertisementDTOImpl::setJobType);

        binder.forField(description)
                .withValidator(
                        JobAdvertisementValidation::notEmptyFieldValidator,
                        "Description " + JobAdvertisementValidation.IS_EMPTY_ERROR
                )
                .bind(JobAdvertisementDTOImpl::getDescription, JobAdvertisementDTOImpl::setDescription);

        binder.forField(begin)
                .withValidator(
                        JobAdvertisementValidation::notNullDateValidator,
                        "Starting date " + JobAdvertisementValidation.IS_EMPTY_ERROR
                )
                .bind(JobAdvertisementDTOImpl::getBegin, JobAdvertisementDTOImpl::setBegin);

        binder.forField(tags)
                .withValidator(
                        JobAdvertisementValidation::notEmptyFieldValidator,
                        "Tags " + JobAdvertisementValidation.IS_EMPTY_ERROR
                )
                .bind(JobAdvertisementDTOImpl::getTags, JobAdvertisementDTOImpl::setTags);
    }

    protected JobPortalUserDTO getCurrentUser() {
        return (JobPortalUserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    protected void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}
