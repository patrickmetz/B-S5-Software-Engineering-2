package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.Globals;

@Route(value = Globals.Pages.REGISTER_VIEW)
@RouteAlias("register")
public class RegistrationChoiceView extends VerticalLayout implements BeforeEnterObserver {

    H3 title = new H3("Register as a...");
    Select<String> role = new Select<>("Student", "Company");
    Button studentButton = new Button("Student");
    Button companyButton = new Button("Company");

    public RegistrationChoiceView() {
        setSizeFull();
        studentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        studentButton.getElement().getStyle().set("height", "3rem").set("width", "100%");
        companyButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        companyButton.getElement().getStyle().set("height", "3rem").set("width", "100%");

        studentButton.addClickListener((event) -> UI.getCurrent().navigate(RegistrationStudentView.class));
        companyButton.addClickListener((event) -> UI.getCurrent().navigate(RegistrationCompanyView.class));

        HorizontalLayout buttonsLayout = new HorizontalLayout(studentButton, companyButton);
        buttonsLayout.setWidth("400px");

        VerticalLayout layout = new VerticalLayout(title, buttonsLayout);
        layout.setWidth("400px");

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
