package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;
import org.hbrs.se2.project.hellocar.views.AppView;

@Route(value = Globals.Pages.UPDATE_STUDENT_VIEW, layout = AppView.class)
@RouteAlias("updatestudent")
public class UpdateStudentView extends UpdateViewBase<StudentDTOImpl>
{
	private TextField role;
	private DatePicker dateOfBirth;
	private TextField studyCourse;
	private TextField specialization;
	private IntegerField semester;
	private Select<String> degree;
	private Button submitButton;

	public UpdateStudentView(ManageUserControl userControl)
	{
		super(new Binder<>(StudentDTOImpl.class), userControl);
	}

	@Override
	protected void setupView()
	{
		// no custom setup needed
	}

	@Override
	protected void setupCustomElements()
	{
		title.setText("Update Account Details");

		dateOfBirth = new DatePicker();
		dateOfBirth.setLabel("Date of Birth");

		studyCourse = new TextField("Study Course");
		specialization = new TextField("Specialization");
		semester = new IntegerField("Semester");
		degree = new Select<>(Globals.Degrees.BACHELOR, Globals.Degrees.MASTER, Globals.Degrees.DOCTORAL_STUDENT);
		degree.setLabel("Degree");
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
				studentDTO.setProfilePicture(this.profilePicture);

				userService.updateUser(getCurrentUser().getId(), studentDTO);

				Utils.displayNotification(true, "Update succeeded");
			}
			catch (Exception e)
			{
				Utils.handleButtonException(e);
			}
		});

		setupDeleteButton();
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
				degree,
				studyCourse,
				specialization,
				semester,
				profilePictureUpload,
				about,
				submitButton,
				deleteButton
		);

		Utils.configureRegistrationForm(formLayout, title, submitButton, deleteButton, about);

		var user = (StudentDTOImpl)userService.readUser(getCurrentUser().getId());
		binder.readBean(user);
		passwordConfirm.setValue(password.getValue());
		profilePicture = user.getProfilePicture();

		return formLayout;
	}
}
