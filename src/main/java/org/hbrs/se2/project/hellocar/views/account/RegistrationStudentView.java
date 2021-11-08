package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.control.JobPortalUsersController;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dao.RolleDAO;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.MainView;

import java.util.stream.Stream;

@Route(value = Globals.Pages.REGISTER_STUDENT_VIEW)
@RouteAlias("registerstudent")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationStudentView extends VerticalLayout {

    /* ToDo navigate to MainView if logged in / Redirection to login after timeout */

    Binder<StudentDTOImpl> binder = new Binder(StudentDTOImpl.class);

    private H3 title;

    private TextField firstName;

    private TextField lastName;

    private TextField userid;

    private Select<String> gender;

    private TextField role;

    private EmailField email;

    private PasswordField password;
    private PasswordField passwordConfirm;

    private TextField street;
    private TextField zipCode;
    private TextField city;
    private TextField streetNumber;

    // Student
    private DatePicker dateOfBirth = new DatePicker();

    private Button submitButton;

    public RegistrationStudentView(ManageUserControl userService) {
        title = new H3("Sign Up");

        firstName = new TextField("First name");
        lastName = new TextField("Last name");

        userid = new TextField("Username");

        email = new EmailField("Email");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm Password");

        gender = new Select<>("Male", "Female");
        gender.setLabel("Gender");

        street = new TextField("Street");
        streetNumber = new TextField("Street Number");
        zipCode = new TextField("ZIP Code");
        city = new TextField("City");

        dateOfBirth.setLabel("Date Of Birth");

        binder.bindInstanceFields(this);

        submitButton = new Button("Join Us");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener((event) -> {
            if (validateInput()) {
                try {
                    // put form values into student dto
                    StudentDTOImpl studentDTO = new StudentDTOImpl();
                    binder.writeBean(studentDTO);

                    // put student user and its roles into db
                    userService.createUser(studentDTO, new String[]{"user", "student"});

                    Utils.displayNotification(true, "Registration succeeded");
                    binder.getFields().forEach(HasValue::clear);

                    UI.getCurrent().navigate(MainView.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        FormLayout studentForm = createStudentForm();
        add(studentForm);

        setHorizontalComponentAlignment(Alignment.CENTER, studentForm);
        setRequiredIndicatorVisible(firstName, lastName, email, password, passwordConfirm);
    }

    public FormLayout createStudentForm() {
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
                submitButton
        );

        Utils.configureRegistrationForm(formLayout, title, submitButton);

        return formLayout;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private boolean validateInput() {
        return Utils.validateRegistrationInput(firstName, lastName, email, password, passwordConfirm);
    }

}
