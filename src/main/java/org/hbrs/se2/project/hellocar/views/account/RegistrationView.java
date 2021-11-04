package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
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

    H3 title = new H3("Register as");
    Select<String> role = new Select<>("Student", "Company");
    Button button = new Button("continue");

    public RegistrationView() {

        button.setEnabled(false);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.getElement().getStyle().set("margin-top", "20px");
        button.getElement().getStyle().set("cursor", "pointer");

        role.addValueChangeListener((event) -> button.setEnabled(true));

        button.addClickListener((event) -> {
            switch (role.getValue()) {
                case "Student":
                    UI.getCurrent().navigate(RegistrationStudentView.class);
                    break;
                case "Company":
                    UI.getCurrent().navigate(RegistrationCompanyView.class);
                    break;
                default:
                    button.setEnabled(false);
            }
        });

        VerticalLayout layout = new VerticalLayout(title, role, button);
        layout.setWidth("400px");
        add(layout);
        setHorizontalComponentAlignment(Alignment.CENTER, layout);
    }


}
