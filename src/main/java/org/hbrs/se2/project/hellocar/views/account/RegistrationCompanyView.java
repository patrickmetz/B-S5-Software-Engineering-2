package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

@Route(value = Globals.Pages.REGISTER_COMPANY_VIEW)
@RouteAlias("registercompany")
public class RegistrationCompanyView extends RegistrationViewBase<CompanyDTOImpl> implements BeforeEnterObserver
{
    // Company
    private TextField companyName;

    private Button submitButton;

    public RegistrationCompanyView(ManageUserControl userService)
    {
        super(new Binder<>(CompanyDTOImpl.class), userService);

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
    }

    @Override
    protected void setupCustomElements()
    {
        companyName = new TextField("Company Name");
    }

    @Override
    protected void setupSubmitButtons()
    {
        submitButton = new Button("Join us");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener((event) -> {
            try {
                // put form values into company dto
                CompanyDTOImpl companyDTO = new CompanyDTOImpl();
                binder.writeBean(companyDTO);

                // put company user and its roles into db
                userService.createUser(
                            companyDTO,
                            new String[]{Globals.Roles.COMPANY, Globals.Roles.USER}
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
    protected void setupCustomValidation()
    {
        setupRegistrationCommonValidation();

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

