package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.account.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.ConfirmationDialog;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;
import org.hbrs.se2.project.hellocar.views.AppView;

@Route(value = Globals.Pages.UPDATE_STUDENT_VIEW, layout = AppView.class)
@RouteAlias("updatestudent")
public class UpdateStudentView extends RegistrationViewBase<StudentDTOImpl> implements BeforeEnterObserver
{
	private TextField role;
	private DatePicker dateOfBirth;
	private Button submitButton;
	private Button deleteButton;
	private StudentDTOImpl studentDTO;

	public UpdateStudentView(ManageUserControl userControl)
	{
		super(new Binder<>(StudentDTOImpl.class), userControl);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		// @todo funktioniert nicht.
		if (getCurrentUser() == null)
			event.forwardTo(Globals.Pages.LOGIN_VIEW);
	}

	@Override
	protected void setupView() {

	}

	@Override
	protected void setupCustomElements()
	{
		title.setText("Update Account Details");

		dateOfBirth = new DatePicker();
		dateOfBirth.setLabel("Date of Birth");
	}

	@Override
	protected void setupSubmitButtons()
	{
		submitButton = new Button("Update");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		submitButton.addClickListener(event -> {
			try
			{
				var studentDTO = new StudentDTOImpl();
				binder.writeBean(studentDTO);

				userService.updateUser(getCurrentUser().getId(), studentDTO);

				Utils.displayNotification(true, "Update succeeded");
			}
			catch (ValidationException e)
			{
				Utils.displayNotification(false, "Please fill in the required fields");
			}
			catch (Exception e)
			{
				Utils.displayNotification(false, "Unknown error: " + e);
			}
		});

		deleteButton = new Button("Delete account");
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
		deleteButton.addClickListener(event -> {
			try
			{
				new ConfirmationDialog(
					"Confirm deletion",
					"Are you sure you want to delete your account?",
					new Button("Delete", confirmEvent -> {
						userService.deleteUser(getCurrentUser().getId());

						Utils.displayNotification(true, "Deletion succeeded");

						// Logout
						UI ui = this.getUI().get();
						ui.getSession().close();
						ui.getPage().setLocation("/");

						UI.getCurrent().navigate(LoginView.class);
					}),
					new Button("Cancel", cancelEvent -> { })
				).open();
			}
			catch (Exception e)
			{
				Utils.displayNotification(false, "Unknown error: " + e);
			}
		});
	}

	@Override
	protected FormLayout setupForm()
	{
		FormLayout formLayout = new FormLayout();
		formLayout.add(
				title,
				firstName,
				lastName,
				userid,
				email,
				gender,
				password,
				passwordConfirm,
				street,
				zipCode,
				city,
				streetNumber,
				dateOfBirth,
				submitButton,
				deleteButton
		);

		Utils.configureRegistrationForm(formLayout, title, submitButton, deleteButton);

		binder.readBean((StudentDTOImpl)userService.readStudent(getCurrentUser().getId()));
		passwordConfirm.setValue(password.getValue());

		return formLayout;
	}

	@Override
	protected void setupCustomRequiredIndicators()
	{

	}

	@Override
	protected void setupCustomValidation()
	{
		binder.forField(userid)
				.withValidator(
						AccountValidation::usernameValidator,
						AccountValidation.USERNAME_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getUserid, JobPortalUserDTOImpl::setUserid);

		binder.forField(email)
				.withValidator(new EmailValidator(AccountValidation.EMAIL_ERROR_MESSAGE))
				.bind(JobPortalUserDTOImpl::getEmail, JobPortalUserDTOImpl::setEmail);
	}
}
