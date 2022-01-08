package org.hbrs.se2.project.hellocar.views.jobAdvertisement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.hbrs.se2.project.hellocar.util.ConfirmationDialog;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import javax.validation.Valid;

@Route(value = Globals.Pages.UPDATE_JOB_AD, layout = AppView.class)
@PageTitle("Update Job Advertisement")
public class JobAdvertisementUpdateView extends JobAdvertisementBaseView
	implements HasUrlParameter<Integer>, BeforeEnterObserver
{
	protected Button updateButton;
	protected Button deleteButton;
	private JobAdvertisementDTOImpl jobAd;
	private int id;

	public JobAdvertisementUpdateView(ManageJobAdvertisementControl jobAdService) {
		super(jobAdService);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		// @todo abort if current user isn't a company or doesn't own the ad
		// setParameter might not have been called yet.
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Integer id)
	{
		this.id = id;
	}

	@Override
	protected void assembleView()
	{
		setupValidation();
		setRequiredIndicatorVisible(jobTitle, jobType, description, begin, tags);

		binder.bindInstanceFields(this);
		jobAd = (JobAdvertisementDTOImpl)jobAdService.readJobAdvertisement(id);
		binder.readBean(jobAd);

		updateButton = setupUpdateButton();
		deleteButton = setupDeleteButton();
		FormLayout form = setupForm();
		add(form);
	}

	@Override
	protected FormLayout setupForm()
	{
		FormLayout formLayout = new FormLayout();
		formLayout.add(pageTitle, jobTitle, jobType, description, begin, tags, updateButton, deleteButton);
		formLayout.addClassName("job-form");

		formLayout.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("490px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);

		formLayout.setColspan(pageTitle, 2);
		formLayout.setColspan(description, 2);

		return formLayout;
	}

	private Button setupUpdateButton()
	{
		Button btn = new Button("Update");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClassName("update-button");
		btn.addClickListener(event -> {
			try
			{
				binder.writeBean(jobAd);
				jobAdService.updateJobAdvertisement(jobAd, id);

				Utils.displayNotification(true, "Job advertisement updated successfully");
			}
			catch (ValidationException e)
			{
				Utils.displayNotification(false, "Please fill in the required fields");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				Utils.displayNotification(
						false,
						"Sorry, an unexpected error occured.\n" +
								"Please contact the support at admin@coll-hbrs.de."
				);
			}
		});
		return btn;
	}

	private Button setupDeleteButton()
	{
		Button btn = new Button("Delete");
		btn.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btn.addClickListener(event -> {
			try
			{
				new ConfirmationDialog(
						"Confirm deletion",
						"Are you sure you want to delete this job advertisement?",
						new Button("Delete", confirmEvent -> {
							jobAdService.deleteJobAdvertisement(id);

							Utils.displayNotification(true, "Deletion succeeded");

							UI.getCurrent().navigate(JobAdvertisementListView.class);
						}),
						new Button("Cancel", cancelEvent -> {})
				).open();
			}
			catch (Exception e)
			{
				Utils.displayNotification(false, "Unknown error: " + e);
			}
		});
		return btn;
	}
}
