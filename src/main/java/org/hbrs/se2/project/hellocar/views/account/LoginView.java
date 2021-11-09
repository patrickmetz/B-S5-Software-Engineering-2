package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.hellocar.control.LoginControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.account.RegistrationView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 */
@Route(value = "" )
@RouteAlias(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver  {

    @Autowired
    private LoginControl loginControl;

    public LoginView() {
        setSizeFull();
        LoginForm component = new LoginForm();
        component.setForgotPasswordButtonVisible(false);
        component.getElement().getStyle().set("border-radius", "2px");
        component.addLoginListener(e -> {
            boolean isAuthenticated = false;
            try {
                isAuthenticated = loginControl.authenticate(e.getUsername(), e.getPassword());
            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add(new Text(databaseException.getReason()));
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
            if (isAuthenticated) {
                grabAndSetUserIntoSession();
                navigateToMainPage();
                Utils.displayNotification(true, "Successfully logged in!");
            } else {
                component.setError(true);
            }
        });

        add(component);
        add(registerButton());
        this.setAlignItems(Alignment.CENTER);
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

    private Button registerButton() {
        Button registerButton = new Button("Create account");
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        registerButton.getElement().getStyle()
                .set("margin-top", "1.5rem")
                .set("cursor", "pointer")
                .set("height", "2.5rem")
                .set("width", "22.5rem");
        registerButton.addClickListener((event)-> UI.getCurrent().navigate(RegistrationView.class));
        return registerButton;
    }

    private void grabAndSetUserIntoSession() {
        UserDTO userDTO = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, userDTO );
    }

    private void navigateToMainPage() {
        // Navigation zur Startseite, hier auf die Teil-Komponente Show-Cars.
        // Die anzuzeigende Teil-Komponente kann man noch individualisieren, je nach Rolle,
        // die ein Benutzer besitzt
        UI.getCurrent().navigate(Globals.Pages.SHOW_CARS);

    }
}
