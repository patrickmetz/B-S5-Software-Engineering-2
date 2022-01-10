package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

public abstract class RegistrationViewBase<T extends JobPortalUserDTOImpl> extends AccountViewBase<T>
{
	public RegistrationViewBase(Binder<T> customBinder, ManageUserControl userService)
	{
		super(customBinder, userService);
	}

	@Override
	protected void setupView() {
		setSizeFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(JustifyContentMode.CENTER);

		//Logo
		HorizontalLayout logoLayout = new HorizontalLayout();
		logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

		Image image = new Image("images/logo.png", "HelloCar logo");
		image.addClassName("logo");

		image.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW));

		logoLayout.add(image);
		add(logoLayout);
	}

	protected void setupRegistrationCommonValidation()
	{
		binder.forField(userid)
				.withValidator(
						AccountValidation::usernameValidator,
						AccountValidation.USERNAME_ERROR_MESSAGE
				)
				.withValidator(
						username -> AccountValidation.usernameAvailableValidator(username, userService),
						AccountValidation.USERNAME_IN_USE_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getUserid, JobPortalUserDTOImpl::setUserid);

		binder.forField(email)
				.withValidator(new EmailValidator(AccountValidation.EMAIL_ERROR_MESSAGE))
				.withValidator(
						email -> AccountValidation.emailAvailableValidator(email, userService),
						AccountValidation.EMAIL_IN_USE_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getEmail, JobPortalUserDTOImpl::setEmail);
	}
}
