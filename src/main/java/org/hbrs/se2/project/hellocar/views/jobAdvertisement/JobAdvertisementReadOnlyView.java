package org.hbrs.se2.project.hellocar.views.jobAdvertisement;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.time.LocalDate;

@Route(value = Globals.Pages.VIEW_JOB_AD, layout = AppView.class)
@PageTitle("Job Advertisement")
public class JobAdvertisementReadOnlyView extends JobAdvertisementBaseView
	implements HasUrlParameter<Integer>
{
	private int id;

	public JobAdvertisementReadOnlyView(ManageJobAdvertisementControl jobAdService)
	{
		super(jobAdService);
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Integer id)
	{
		this.id = id;
	}

	@Override
	protected void assembleView()
	{
		jobTitle.setReadOnly(true);
		jobType.setReadOnly(true);
		description.setReadOnly(true);
		begin.setReadOnly(true);
		tags.setReadOnly(true);

		binder.bindInstanceFields(this);
		JobAdvertisementDTOImpl jobad = (JobAdvertisementDTOImpl)jobAdService.readJobAdvertisement(id);
		binder.readBean(jobad);

		FormLayout form = setupForm();
		add(form);
	}

	@Override
	protected FormLayout setupForm()
	{
		FormLayout formLayout = new FormLayout();
		formLayout.add(pageTitle, jobTitle, jobType, description, begin, tags);
		formLayout.addClassName("job-form");

		formLayout.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);

		formLayout.setColspan(pageTitle, 2);
		formLayout.setColspan(description, 2);

		return formLayout;
	}
}
