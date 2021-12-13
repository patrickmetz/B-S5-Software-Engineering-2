package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;

@Route(value = Globals.Pages.REGISTER_STUDENT_VIEW)
@RouteAlias("registerstudent")
public class RegistrationStudentView extends RegistrationViewBase<StudentDTOImpl> implements BeforeEnterObserver
{
    /* ToDo Redirection to login after timeout */

    private TextField role;

    // Student
    private DatePicker dateOfBirth;
    private TextField studyCourse;
    private TextField specialization;
    private IntegerField semester;

    private Button submitButton;

    public RegistrationStudentView(ManageUserControl userService)
    {
        super(new Binder<>(StudentDTOImpl.class), userService);

        HorizontalLayout backLayout = new HorizontalLayout(new RouterLink("Go back", RegistrationChoiceView.class));
        add(backLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        if (getCurrentUser() != null)
            event.forwardTo(Globals.Pages.SHOW_CARS);
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

    @Override
    protected void setupCustomElements()
    {
        dateOfBirth = new DatePicker("Date Of Birth");
        studyCourse = new TextField("Study Course");
        specialization = new TextField("Specialization");
        semester = new IntegerField("Semester");
    }

    @Override
    protected void setupSubmitButtons()
    {
        submitButton = new Button("Join us");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener((event) -> {
            try {
                // put form values into student dto
                StudentDTOImpl studentDTO = new StudentDTOImpl();
                binder.writeBean(studentDTO);
                studentDTO.setProfilePicture(this.profilePicture);

                // put student user and its roles into db
                userService.createUser(
                        studentDTO,
                        new String[]{Globals.Roles.STUDENT, Globals.Roles.USER}
                );

                Utils.displayNotification(true, "Registration succeeded");
                binder.getFields().forEach(HasValue::clear);

                UI.getCurrent().navigate(LoginView.class);

            } catch (ValidationException e) {
                Utils.displayNotification(false, "Please fill in the required fields");
            } catch (Exception e) {
                Utils.displayNotification(
                        false,
                        "Sorry, an unexpected error occured.\n" +
                                "Please contact the support at admin@coll-hbrs.de."
                );
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
                studyCourse,
                specialization,
                semester,
                profilePictureUpload,
                about,
                submitButton
        );

        Utils.configureRegistrationForm(formLayout, title, submitButton, null,  about);

        return formLayout;
    }

    @Override
    protected void setupCustomRequiredIndicators()
    { }

    @Override
    protected void setupCustomValidation()
    {
        setupRegistrationCommonValidation();
    }
}
