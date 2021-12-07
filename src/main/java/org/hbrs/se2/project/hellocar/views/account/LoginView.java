package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 */
@Route(value = "" )
@CssImport("./styles/views/login/login-view.css")
@CssImport("./styles/views/logo/logo.css")
@RouteAlias(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver  {

    @Autowired
    private LoginControl loginControl;

    public LoginView() {
        addClassName("login-view");

        //Hinzufügen des Logos
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Image image = new Image("images/logo.png", "HelloCar logo");
        image.setWidth("300px");
        image.setHeight("150px");

        Label label1 = new Label("Welcome to Coll@HBRS!");
        Label label2 = new Label("Welcome to the Coll@HBRS portal of the Bonn-Rhein-Sieg University of Applied Sciences." +
                " Here students can take advantage of job offers from companies and companies can post job offers.");

        label1.addClassName("welcome-text");
        label2.addClassName("welcome2-text");

        logoLayout.add(image);
        add(logoLayout, label1, label2);


        setSizeFull();
        LoginForm component = new LoginForm();
        component.getElement().getStyle().set("padding-top", "20px");
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

    private Button registerButton() {
        Button registerButton = new Button("Create account");
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        registerButton.getElement().getStyle();

        registerButton.addClickListener((event)-> UI.getCurrent().navigate(RegistrationChoiceView.class));
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
