package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.apache.commons.codec.binary.Base64;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.AppView;

@Route(value = Globals.Pages.READONLY_STUDENT_VIEW, layout = AppView.class)
@RouteAlias("viewstudent")
public class ReadOnlyStudentView extends ReadOnlyViewBase<StudentDTOImpl> implements HasUrlParameter<Integer>
{
	private TextField role;
	private DatePicker dateOfBirth;
	private TextField studyCourse;
	private TextField specialization;
	private IntegerField semester;
	private Select<String> degree;

	public ReadOnlyStudentView(ManageUserControl userControl)
	{
		super(new Binder<>(StudentDTOImpl.class), userControl);
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Integer id)
	{
		StudentDTOImpl user = (StudentDTOImpl) userService.readUser(id);
		binder.readBean(user);
		profilePicture.setSrc("data:image/png;base64,"
				+ Base64.encodeBase64String(user.getProfilePicture()));
	}

	@Override
	protected void setupView()
	{
		// no custom setup required
	}

	@Override
	protected void setupCustomElements()
	{
		super.setupCustomElements();

		title.setText("Student details");

		dateOfBirth = new DatePicker("Date Of Birth");
		dateOfBirth.setReadOnly(true);
		studyCourse = new TextField("Study Course");
		studyCourse.setReadOnly(true);
		specialization = new TextField("Specialization");
		specialization.setReadOnly(true);
		semester = new IntegerField("Semester");
		semester.setReadOnly(true);
		degree = new Select<>(Globals.Degrees.BACHELOR, Globals.Degrees.MASTER, Globals.Degrees.DOCTORAL_STUDENT);
		degree.setLabel("Degree");
		degree.setReadOnly(true);
	}

	@Override
	protected void setupSubmitButtons()
	{
		// no buttons
	}

	@Override
	protected FormLayout setupForm()
	{
		FormLayout formLayout = new FormLayout();
		formLayout.add(
				title,
				city,
				studyCourse,
				specialization,
				semester,
				degree,
				profilePicture,
				about
		);

		Utils.configureRegistrationForm(formLayout, title, null, null, about);

		return formLayout;
	}

	@Override
	protected void setupCustomRequiredIndicators()
	{
		// no custom setup needed
	}

	@Override
	protected void setupCustomValidation()
	{
		// no custom setup needed
	}
}