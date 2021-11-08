package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
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
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.registration.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.MainView;

import java.util.stream.Stream;

@Route(value = Globals.Pages.REGISTER_COMPANY_VIEW)
@RouteAlias("registercompany")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationCompanyView extends VerticalLayout {

    /* ToDo navigate to MainView if logged in */

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
        title = new H3("Sign Up");

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

        submitButton = new Button("Join Us");
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

                UI.getCurrent().navigate(MainView.class);
            }
        });

        FormLayout companyForm = createCompanyForm();
        add(companyForm);

        setHorizontalComponentAlignment(Alignment.CENTER, companyForm);
        setRequiredIndicatorVisible(firstName, lastName, email, password, passwordConfirm);
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

