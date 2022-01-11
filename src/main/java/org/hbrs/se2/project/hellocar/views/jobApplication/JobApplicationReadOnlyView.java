package org.hbrs.se2.project.hellocar.views.jobApplication;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.hbrs.se2.project.hellocar.control.ManageJobApplicationControl;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.views.AppView;

import java.io.ByteArrayInputStream;

@Route(value = Globals.Pages.VIEW_JOB_APP, layout = AppView.class)
@PageTitle("Job Application")
public class JobApplicationReadOnlyView extends VerticalLayout
	implements HasUrlParameter<Integer>, AfterNavigationObserver
{
	private Binder<JobApplicationDTOImpl> binder;
	private ManageJobApplicationControl jobAppService;
	private boolean rendered = false;
	private int id;

	private TextArea text;
	private Anchor resumeDownload;

	public JobApplicationReadOnlyView(ManageJobApplicationControl jobAppService)
	{
		this.jobAppService = jobAppService;
		binder = new Binder<>(JobApplicationDTOImpl.class);
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Integer id)
	{
		this.id = id;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent)
	{
		if (rendered)
			return;

		addClassName("job-application");

		text = new TextArea("Text");
		text.setClassName("job-application-text");
		text.setReadOnly(true);

		resumeDownload = new Anchor();
		resumeDownload.setText("Download resume");

		binder.bindInstanceFields(this);
		var dto = (JobApplicationDTOImpl)jobAppService.readJobApplication(id);
		if (dto == null)
			throw new NullPointerException("JobApplicationDTO was null.");

		binder.readBean(dto);
		if (dto.getResume() != null && dto.getResume().length > 0)
			resumeDownload.setHref(new StreamResource(
				"resume.pdf",
				() -> new ByteArrayInputStream(dto.getResume()))
			);

		var form = new FormLayout();
		form.add(text, resumeDownload);
		add(form);

		rendered = true;
	}
}
