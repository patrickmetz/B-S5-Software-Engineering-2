package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

import java.util.stream.Stream;

public abstract class RegistrationViewBase<T extends JobPortalUserDTOImpl> extends VerticalLayout implements BeforeEnterObserver
{
	protected Binder<T> binder;

	protected H3 title;
	protected TextField firstName;
	protected TextField lastName;
	protected TextField userid;
	protected Select<String> gender;
	protected EmailField email;
	protected PasswordField password;
	protected PasswordField passwordConfirm;
	protected TextField street;
	protected TextField zipCode;
	protected TextField city;
	protected TextField streetNumber;

	public RegistrationViewBase(Binder<T> customBinder, ManageUserControl userService)
	{
		binder = customBinder;

		setupCommonElements();
		setupCustomElements();

		binder.bindInstanceFields(this);

		setupSubmitButtons(userService);

		FormLayout form = setupForm();
		add(form);

		setHorizontalComponentAlignment(Alignment.CENTER, form);
		setupCommonRequiredIndicators();
		setupCustomRequiredIndicators();

		setupCommonValidation(userService);
		setupCustomValidation(userService);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		if (getCurrentUser() != null)
			event.forwardTo(Globals.Pages.SHOW_CARS);
	}

	private void setupCommonElements()
	{
		title = new H3("Sign up");

		firstName = new TextField("First name (Contact Person)");
		lastName = new TextField("Last name (Contact Person)");

		userid = new TextField("Username");

		gender = new Select<>("Male", "Female");
		gender.setLabel("Gender (Contact Person)");

		email = new EmailField("Email");

		password = new PasswordField("Password");
		passwordConfirm = new PasswordField("Confirm Password");

		street = new TextField("Street");
		streetNumber = new TextField("Street Number");
		zipCode = new TextField("ZIP Code");
		city = new TextField("City");
	}

	private void setupCommonRequiredIndicators()
	{
		setRequiredIndicatorVisible(firstName, lastName, userid, email, password, passwordConfirm);
	}
	
	private void setupCommonValidation(ManageUserControl userService)
	{
		binder.forField(firstName)
				.withValidator(
						AccountValidation::firstnameValidator,
						AccountValidation.FIRST_NAME_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getFirstName, JobPortalUserDTOImpl::setFirstName);

		binder.forField(lastName)
				.withValidator(
						AccountValidation::lastnameValidator,
						AccountValidation.LAST_NAME_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getLastName, JobPortalUserDTOImpl::setLastName);

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

		binder.forField(password)
				.withValidator(
						AccountValidation::passwordValidator,
						AccountValidation.PASSWORD_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getPassword, JobPortalUserDTOImpl::setPassword);

		binder.forField(passwordConfirm)
				.withValidator(
						pw -> AccountValidation.confirmPassowrdValidator(pw, password.getValue()),
						AccountValidation.CONFIRM_PASSWORD_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getPassword, JobPortalUserDTOImpl::setPassword);

		binder.forField(email)
				.withValidator(new EmailValidator(AccountValidation.EMAIL_ERROR_MESSAGE))
				.withValidator(
						email -> AccountValidation.emailAvailableValidator(email, userService),
						AccountValidation.EMAIL_IN_USE_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getEmail, JobPortalUserDTOImpl::setEmail);
	}

	protected abstract void setupCustomElements();

	protected abstract void setupSubmitButtons(ManageUserControl userService);

	protected abstract FormLayout setupForm();

	protected abstract void setupCustomRequiredIndicators();

	protected abstract void setupCustomValidation(ManageUserControl userService);

	protected void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}

	protected UserDTO getCurrentUser() {
		return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
	}
}
