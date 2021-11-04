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
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.control.UsersController;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.MainView;

import java.util.stream.Stream;

@Route(value = Globals.Pages.REGISTER_COMPANY_VIEW)
@RouteAlias("registercompany")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationCompanyView extends VerticalLayout {

    /* ToDo navigate to MainView if logged in */

    Binder<Company> companyBinder = new Binder<>(Company.class);

    private H3 title;

    private TextField firstNameContactPerson;

    private TextField lastNameContactPerson;

    private Select<String> genderContactPerson;

    private TextField userType;

    private EmailField email;

    private PasswordField password;
    private PasswordField passwordConfirm;

    private TextField street;
    private TextField zipCode;
    private TextField city;
    private TextField streetNumber;

    // Company
    private TextField nameContactPerson;

    private Button submitButton;

    public RegistrationCompanyView(UsersController usersController) {
        title = new H3("Sign Up");

        firstNameContactPerson = new TextField("First name (Contact Person)");
        lastNameContactPerson = new TextField("Last name (Contact Person)");

        genderContactPerson = new Select<>("Male", "Female");
        genderContactPerson.setLabel("Gender (Contact Person)");

        email = new EmailField("Email");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm Password");


        street = new TextField("Street");
        streetNumber = new TextField("Street Number");
        zipCode = new TextField("ZIP Code");
        city = new TextField("City");

        nameContactPerson = new TextField("Company Name");

        companyBinder.bindInstanceFields(this);

        submitButton = new Button("Join Us");
        submitButton.addClickListener((event) -> {
            if (validateInput()) {

                Company c = new Company();
                c.setUserType("Company");
                try {
                    companyBinder.writeBean(c);
                    System.out.println(c);
                    usersController.createStudent(c);
                } catch (ValidationException e) {
                    System.out.println("Something went wrong!");
                }

                Notification notification = new Notification(
                        "Registration succeeded"
                );
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setDuration(5000);
                notification.setPosition(Notification.Position.BOTTOM_CENTER);
                notification.open();
                companyBinder.getFields().forEach(HasValue::clear);

                UI.getCurrent().navigate(MainView.class);
            }
        });

        FormLayout companyForm = createCompanyForm();
        add(companyForm);

        setHorizontalComponentAlignment(Alignment.CENTER, companyForm);
        setRequiredIndicatorVisible(firstNameContactPerson, lastNameContactPerson, email, password, passwordConfirm);
    }

    public FormLayout createCompanyForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                title,
                firstNameContactPerson,
                lastNameContactPerson,
                email,
                genderContactPerson,
                password,
                passwordConfirm,
                street,
                zipCode,
                city,
                streetNumber,
                nameContactPerson,
                submitButton
        );

        Utils.configureForm(formLayout, title, submitButton);

        return formLayout;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private boolean validateInput() {
        return Utils.validateFrontendInput(firstNameContactPerson, lastNameContactPerson, email, password, passwordConfirm);
    }
}

