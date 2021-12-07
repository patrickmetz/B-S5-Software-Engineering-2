package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.Globals;

@Route(value = Globals.Pages.REGISTER_VIEW)
@RouteAlias("register")
@CssImport("./styles/views/registration/registration-view.css")
@CssImport("./styles/views/logo/logo.css")
public class RegistrationChoiceView extends VerticalLayout implements BeforeEnterObserver {

    H3 title = new H3("Register as a...");
    Select<String> role = new Select<>("Student", "Company");
    Button studentButton = new Button("Student");
    Button companyButton = new Button("Company");

    public RegistrationChoiceView() {
        setSizeFull();

        addClassName("registration-view");

        //Logo
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Image image = new Image("images/logo.png", "HelloCar logo");
        image.addClassName("logo");

        image.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW));

        logoLayout.add(image);
        add(logoLayout);

        studentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        studentButton.addClassName("choiceButton");
        companyButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        companyButton.addClassName("choiceButton");

        studentButton.addClickListener((event) -> UI.getCurrent().navigate(RegistrationStudentView.class));
        companyButton.addClickListener((event) -> UI.getCurrent().navigate(RegistrationCompanyView.class));

        HorizontalLayout buttonsLayout = new HorizontalLayout(studentButton, companyButton);
        buttonsLayout.addClassName("choiceButtonLayout");

        VerticalLayout layout = new VerticalLayout(title, buttonsLayout);
        layout.addClassName("choiceButtonLayout");

        HorizontalLayout backLayout = new HorizontalLayout(new RouterLink("Go back", LoginView.class));

        add(layout);
        add(backLayout);

        setHorizontalComponentAlignment(Alignment.CENTER, layout);
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
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

}
