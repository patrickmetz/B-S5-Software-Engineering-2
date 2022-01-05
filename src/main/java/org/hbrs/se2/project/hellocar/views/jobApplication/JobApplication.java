package org.hbrs.se2.project.hellocar.views.jobApplication;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import org.apache.commons.compress.utils.IOUtils;
import org.hbrs.se2.project.hellocar.control.ManageJobApplicationControl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.hbrs.se2.project.hellocar.views.account.LoginView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Route(value = Globals.Pages.JOB_APPLICATION, layout = AppView.class)
public class JobApplication extends VerticalLayout implements HasUrlParameter<Integer> {
    Binder<JobApplicationDTOImpl> binder;
    protected ManageJobApplicationControl manageJobApplicationService;

    private int jobAdvertisementId;

    protected H3 title;

    private TextArea text;
    protected Upload uploadResume;
    private byte[] resume;

    protected Button submitButton;

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer jobId) {
        this.jobAdvertisementId = jobId;
    }

    public JobApplication(ManageJobApplicationControl manageJobApplicationService) {
        this.binder = new Binder<>(JobApplicationDTOImpl.class);

        this.manageJobApplicationService = manageJobApplicationService;

        this.title = new H3("Apply to Job"); /* todo add job title */
        this.text = new TextArea("Message");
        MemoryBuffer buffer = new MemoryBuffer();
        uploadResume = new Upload(buffer);
        Button uploadButton = new Button("Upload Resume");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadResume.setDropAllowed(false);
        uploadResume.setAcceptedFileTypes(".pdf");
        uploadResume.addSucceededListener(event -> {
            InputStream inputStream = buffer.getInputStream();
            try {
                resume = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.submitButton = new Button("Send Application");

        FormLayout form = setupForm();
        add(form);

        setupSubmitButton();
        binder.bindInstanceFields(this);
    }

    protected FormLayout setupForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                title,
                text,
                uploadResume,
                submitButton
        );

        return formLayout;
    }

    protected void setupSubmitButton() {
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener((event) -> {
            try {
                // put form values into student dto
                JobApplicationDTOImpl jobApplicationDTO = new JobApplicationDTOImpl();
                binder.writeBean(jobApplicationDTO);
                jobApplicationDTO.setResume(resume);
                jobApplicationDTO.setJobAdvertisementId(jobAdvertisementId);

                // put student user and its roles into db
                manageJobApplicationService.createJobApplication(
                        jobApplicationDTO,
                        this.getCurrentUser().getId()
                );

                Utils.displayNotification(true, "Application succeeded");
                binder.getFields().forEach(HasValue::clear);

            } catch (ValidationException e) {
                Utils.displayNotification(false, "Please fill in the required fields");
            } catch (Exception e) {
                Utils.displayNotification(
                        false,
                        "Sorry, an unexpected error occured.\n" +
                                "Please contact the support at admin@coll-hbrs.de."
                );
            }
        });
    }

    protected UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
