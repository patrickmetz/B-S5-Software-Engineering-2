package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import org.apache.commons.compress.utils.IOUtils;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

@CssImport("./styles/views/registration/registration-view.css")
@CssImport("./styles/views/logo/logo.css")
public abstract class AccountViewBase<T extends JobPortalUserDTOImpl> extends VerticalLayout
{
	protected Binder<T> binder;
	protected ManageUserControl userService;

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
	protected byte[] profilePicture;
	protected Upload profilePictureUpload;
	protected TextArea about;

	public AccountViewBase(Binder<T> customBinder, ManageUserControl userService)
	{

		addClassName("account-view");

		binder = customBinder;
		this.userService = userService;

		setupView();

		setupCommonElements();
		setupCustomElements();

		binder.bindInstanceFields(this);

		setupSubmitButtons();

		FormLayout form = setupForm();
		add(form);

		setHorizontalComponentAlignment(Alignment.CENTER, form);
		setupCommonRequiredIndicators();
		setupCustomRequiredIndicators();

		setupCommonValidation();
		setupCustomValidation();
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

		MemoryBuffer buffer = new MemoryBuffer();
		profilePictureUpload = new Upload(buffer);
		Button uploadButton = new Button("Upload Image");
		uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		profilePictureUpload.setUploadButton(uploadButton);
		profilePictureUpload.setDropAllowed(false);
		profilePictureUpload.setAcceptedFileTypes(".png");
		profilePictureUpload.addSucceededListener(event -> {
			InputStream inputStream = buffer.getInputStream();
			try {
				profilePicture = IOUtils.toByteArray(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		// @todo do not erase profile picture if profile is updated without a new upload.

		about = new TextArea("Tell us more about you!");

	}

	private void setupCommonRequiredIndicators()
	{
		setRequiredIndicatorVisible(firstName, lastName, userid, email, password, passwordConfirm);
	}

	private void setupCommonValidation()
	{
		binder.forField(firstName)
				.withValidator(
						AccountValidation::firstnameValidator,
						AccountValidation.FIRST_NAME_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTO::getFirstName, JobPortalUserDTOImpl::setFirstName);

		binder.forField(lastName)
				.withValidator(
						AccountValidation::lastnameValidator,
						AccountValidation.LAST_NAME_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getLastName, JobPortalUserDTOImpl::setLastName);

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
	}

	protected abstract void setupView();

	protected abstract void setupCustomElements();

	protected abstract void setupSubmitButtons();

	protected abstract FormLayout setupForm();

	protected abstract void setupCustomRequiredIndicators();

	protected abstract void setupCustomValidation();

	protected void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}

	protected UserDTO getCurrentUser() {
		return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
	}
}
