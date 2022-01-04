package org.hbrs.se2.project.hellocar.views.jobAdvertisement;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.hbrs.se2.project.hellocar.views.ShowCarsView;
import org.hbrs.se2.project.hellocar.views.account.LoginView;

@Route(value = Globals.Pages.CREATE_JOB_AD, layout = AppView.class)
@RouteAlias("createjob")
public class JobAdvertisementCreateView extends JobAdvertisementBaseView {

    protected Button createButton;

    public JobAdvertisementCreateView(ManageJobAdvertisementControl jobAdService) {
        super(jobAdService);
        binder.bindInstanceFields(this);
        createButton = setupCreateButton();
        FormLayout form = setupForm();
        add(form);
    }

    @Override
    protected FormLayout setupForm() {

        FormLayout formLayout = new FormLayout();
        formLayout.add(pageTitle, jobTitle, jobType, description, begin, tags, createButton);
        formLayout.addClassName("job-form");

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        formLayout.setColspan(pageTitle, 2);
        formLayout.setColspan(createButton, 2);
        formLayout.setColspan(description, 2);


        return formLayout;
    }

    private Button setupCreateButton() {
        Button tmpBtn = new Button("Create Job Advertisement");
        tmpBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        tmpBtn.addClassName("create-button");
        tmpBtn.addClickListener((event) -> {
            try {
                // put form values into jobAd dto
                JobAdvertisementDTOImpl jobAdvertisementDTO = new JobAdvertisementDTOImpl();
                binder.writeBean(jobAdvertisementDTO);
                jobAdService.createJobAdvertisement(
                        jobAdvertisementDTO,
                        this.getCurrentUser().getId()
                );

                Utils.displayNotification(true, "Job advertisement created successfully");
                UI.getCurrent().navigate(ShowCarsView.class); /* todo replace with the advertisement view later */
                binder.getFields().forEach(HasValue::clear);

            } catch (ValidationException e) {
                Utils.displayNotification(false, "Please fill in the required fields");
            } catch (Exception e) {
                e.printStackTrace();
                Utils.displayNotification(
                        false,
                        "Sorry, an unexpected error occured.\n" +
                                "Please contact the support at admin@coll-hbrs.de."
                );
            }
        });

        return tmpBtn;
    }
}
