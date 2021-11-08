package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.util.Globals;

@Route(value = Globals.Pages.REGISTER_VIEW)
@RouteAlias("register")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationView extends VerticalLayout {

    /* ToDo navigate to MainView if logged in */


    H3 title = new H3("Register as a...");
    Select<String> role = new Select<>("Student", "Company");
    Button studentButton = new Button("Student");
    Button companyButton = new Button("Company");

    public RegistrationView() {

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
        add(layout);

        setHorizontalComponentAlignment(Alignment.CENTER, layout);
    }


}
