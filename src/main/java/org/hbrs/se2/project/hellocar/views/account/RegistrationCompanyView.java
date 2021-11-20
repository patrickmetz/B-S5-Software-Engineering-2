package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
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
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

import java.util.stream.Stream;

@Route(value = Globals.Pages.REGISTER_COMPANY_VIEW)
@RouteAlias("registercompany")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationCompanyView extends RegistrationViewBase<CompanyDTOImpl>
{
    // Company
    private TextField companyName;

    private Button submitButton;

    public RegistrationCompanyView(ManageUserControl userService)
    {
        super(new Binder<>(CompanyDTOImpl.class), userService);
    }

    @Override
    protected void setupCustomElements()
    {
        companyName = new TextField("Company Name");
    }

    @Override
    protected void setupSubmitButtons(ManageUserControl userService)
    {
        submitButton = new Button("Join us");
        submitButton.addClickListener((event) -> {
            if (validateInput()) {
                try {
                    // put form values into company dto
                    CompanyDTOImpl companyDTO = new CompanyDTOImpl();
                    binder.writeBean(companyDTO);

                    // put company user and its roles into db
                    userService.createUser(
                            companyDTO,
                            new String[]{Globals.Roles.COMPANY, Globals.Roles.USER}
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Utils.displayNotification(true, "Registration succeeded");
                binder.getFields().forEach(HasValue::clear);

                UI.getCurrent().navigate(LoginView.class);
            }
        });
    }

    @Override
    protected FormLayout setupForm()
    {
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

    @Override
    protected void setupCustomRequiredIndicators()
    {
        setRequiredIndicatorVisible(companyName);
    }

    @Override
    protected void setupCustomValidation(ManageUserControl userService)
    {
        binder.forField(companyName)
                .withValidator(
                        AccountValidation::companyNameValidator,
                        AccountValidation.COMPANY_NAME_ERROR_MESSAGE
                )
                .bind(CompanyDTOImpl::getCompanyName, CompanyDTOImpl::setCompanyName);
    }

    private boolean validateInput() {
        return Utils.validateRegistrationInput(firstName, lastName, email, password, passwordConfirm);
    }
}

