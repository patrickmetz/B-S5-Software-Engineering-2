package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

import java.util.stream.Stream;

@Route(value = Globals.Pages.REGISTER_COMPANY_VIEW)
@RouteAlias("registercompany")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationCompanyView extends VerticalLayout implements BeforeEnterObserver {

    Binder<CompanyDTOImpl> binder = new Binder(CompanyDTOImpl.class);

    private H3 title;

    private TextField firstName;

    private TextField lastName;

    private TextField userid;

    private Select<String> gender;

    private EmailField email;

    private PasswordField password;
    private PasswordField passwordConfirm;

    private TextField street;
    private TextField zipCode;
    private TextField city;
    private TextField streetNumber;

    // Company
    private TextField companyName;

    private Button submitButton;

    public RegistrationCompanyView(ManageUserControl userService) {
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

        companyName = new TextField("Company Name");

        binder.bindInstanceFields(this);

        submitButton = new Button("Join us");
        submitButton.addClickListener((event) -> {
            if (validateInput()) {
                try {
                    // put form values into company dto
                    CompanyDTOImpl companyDTO = new CompanyDTOImpl();
                    binder.writeBean(companyDTO);

                    // put company user and its roles into db
                    userService.createUser(companyDTO, new String[]{"user", "company"});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Utils.displayNotification(true, "Registration succeeded");
                binder.getFields().forEach(HasValue::clear);

                UI.getCurrent().navigate(LoginView.class);
            }
        });

        FormLayout companyForm = createCompanyForm();
        add(companyForm);

        setHorizontalComponentAlignment(Alignment.CENTER, companyForm);
        setRequiredIndicatorVisible(firstName, lastName, userid, email, password, passwordConfirm, companyName);

        // validation

        binder.forField(firstName)
                .withValidator(
                        AccountValidation::firstnameValidator,
                        AccountValidation.FIRST_NAME_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getFirstName, CompanyDTOImpl::setFirstName);

        binder.forField(lastName)
                .withValidator(
                        AccountValidation::lastnameValidator,
                        AccountValidation.LAST_NAME_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getLastName, CompanyDTOImpl::setLastName);

        binder.forField(userid)
                .withValidator(
                        AccountValidation::usernameValidator,
                        AccountValidation.USERNAME_ERROR_MESSAGE
                )
                .withValidator(
                        username -> AccountValidation.usernameAvailableValidator(username, userService),
                        AccountValidation.USERNAME_IN_USE_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getUserid, CompanyDTOImpl::setUserid);

        binder.forField(password)
                .withValidator(
                        AccountValidation::passwordValidator,
                        AccountValidation.PASSWORD_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getPassword, CompanyDTOImpl::setPassword);

        binder.forField(passwordConfirm)
                .withValidator(
                        pw -> AccountValidation.confirmPassowrdValidator(pw, password.getValue()),
                        AccountValidation.CONFIRM_PASSWORD_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getPassword, CompanyDTOImpl::setPassword);

        binder.forField(email)
                .withValidator(new EmailValidator(AccountValidation.EMAIL_ERROR_MESSAGE))
                .withValidator(
                        email -> AccountValidation.emailAvailableValidator(email, userService),
                        AccountValidation.EMAIL_IN_USE_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getEmail, CompanyDTOImpl::setEmail);

        binder.forField(companyName)
                .withValidator(
                        AccountValidation::companyNameValidator,
                        AccountValidation.COMPANY_NAME_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getCompanyName, CompanyDTOImpl::setCompanyName);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(getCurrentUser() != null) {
            event.forwardTo(Globals.Pages.SHOW_CARS);
            return;
        }
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public FormLayout createCompanyForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                title,
                companyName,
                userid,
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

