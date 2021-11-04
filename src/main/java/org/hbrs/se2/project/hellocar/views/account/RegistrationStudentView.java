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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.control.UsersController;
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

    Binder<Student> studentBinder = new Binder<>(Student.class);

    private H3 title;

    private TextField firstName;

    private TextField lastName;

    private final String[] genderList = {"Male", "Female"};
    private Select<String> gender = new Select<>();

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
    private TextField studyCourse;
    private IntegerField semester;
    private TextField specialization;

    private Button submitButton;

    public RegistrationStudentView(UsersController usersController) {
        title = new H3("Sign Up");

        firstName = new TextField("First name");
        lastName = new TextField("Last name");

        gender.setItems(genderList);
        gender.setLabel("Gender");

        email = new EmailField("Email");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm Password");


        street = new TextField("Street");
        streetNumber = new TextField("Street Number");
        zipCode = new TextField("ZIP Code");
        city = new TextField("City");

        dateOfBirth.setLabel("Date Of Birth");

        studyCourse = new TextField("Course of Study");
        semester = new IntegerField("Semester");
        specialization = new TextField("Specialization");

        studentBinder.bindInstanceFields(this);

        submitButton = new Button("Join Us");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener((event) -> {
            if (validateInput()) {

                Student s = new Student();
                s.setUserType("Student");
                try {
                    studentBinder.writeBean(s);
                    System.out.println(s);
                    usersController.createStudent(s);
                } catch (ValidationException e) {
                    System.out.println("Something went wrong!");
                }

                Notification notification = new Notification("Registration succeeded");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setDuration(5000);
                notification.setPosition(Notification.Position.BOTTOM_CENTER);
                notification.open();
                studentBinder.getFields().forEach(HasValue::clear);

                UI.getCurrent().navigate(MainView.class);
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

        Utils.configureForm(formLayout, title, submitButton);

        return formLayout;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private boolean validateInput() {
        return Utils.validateFrontendInput(firstName, lastName, email, password, passwordConfirm);
    }


}
