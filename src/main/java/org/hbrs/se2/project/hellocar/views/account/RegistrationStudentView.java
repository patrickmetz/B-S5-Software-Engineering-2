package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
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
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

import java.util.stream.Stream;

@Route(value = Globals.Pages.REGISTER_STUDENT_VIEW)
@RouteAlias("registerstudent")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationStudentView extends RegistrationViewBase<StudentDTOImpl> implements BeforeEnterObserver
{
    /* ToDo Redirection to login after timeout */

    private TextField role;

    // Student
    private DatePicker dateOfBirth;

    private Button submitButton;

    public RegistrationStudentView(ManageUserControl userService)
    {
        super(new Binder<>(StudentDTOImpl.class), userService);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        if (getCurrentUser() != null)
            event.forwardTo(Globals.Pages.SHOW_CARS);
    }

    @Override
    protected void setupCustomElements()
    {
        dateOfBirth = new DatePicker();
        dateOfBirth.setLabel("Date Of Birth");
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
            } catch (DatabaseUserException e) {
                Utils.displayNotification(
                        false,
                        "Something went wrong with database! \n" +
                                "Please contact the support"
                );
            } catch (Exception e) { /* todo: this block should be removed later */
                Utils.displayNotification(
                        false,
                        "Something went wrong! Fix ur code :))"
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
                submitButton
        );

        Utils.configureRegistrationForm(formLayout, title, submitButton);

        return formLayout;
    }

    @Override
    protected void setupCustomRequiredIndicators()
    { }

    @Override
    protected void setupCustomValidation()
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
