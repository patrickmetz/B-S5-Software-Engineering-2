package org.hbrs.se2.project.hellocar.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Utils {

    /**
     * Nützliche Methdode zur Erweiterung eines bestehendes Arrays
     * Oma hätte gesagt, so eine Methode 'fällt nicht durch' ;-)
     * <p>
     * https://stackoverflow.com/questions/2843366/how-to-add-new-elements-to-an-array
     */
    public static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;

    }

    public static void configureRegistrationForm(FormLayout formLayout, H3 title, Button submitButton) {
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.getElement().getStyle().set("margin-top", "20px");
        submitButton.getElement().getStyle().set("cursor", "pointer");

        formLayout.setMaxWidth("500px");

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        formLayout.setColspan(title, 2);
        formLayout.setColspan(submitButton, 2);
    }


    public static boolean validateRegistrationInput(
            TextField firstName,
            TextField lastName,
            EmailField email,
            PasswordField password,
            PasswordField passwordConfirm
    ) {
        boolean valid = true;
        UnorderedList errorList = new UnorderedList();

        if (firstName.getValue().trim().isEmpty()) {
            ListItem error = new ListItem("First name cannot be empty");
            errorList.add(error);
            valid = false;
        }

        if (lastName.getValue().trim().isEmpty()) {
            ListItem error = new ListItem("Last name cannot be empty");
            errorList.add(error);
            valid = false;
        }

        if (email.getValue().trim().isEmpty()) {
            ListItem error = new ListItem("Email cannot be empty");
            errorList.add(error);
            valid = false;
        }

        if (password.getValue().trim().isEmpty()) {
            ListItem error = new ListItem("Password cannot be empty");
            errorList.add(error);
            valid = false;
        }

        if (!password.getValue().equals(passwordConfirm.getValue())) {
            ListItem error = new ListItem("Passwords do not match");
            errorList.add(error);
            valid = false;
        }

        if (!valid) {
            Notification notification = new Notification(errorList);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
            notification.setDuration(5000);
            notification.open();
        }

        return valid;
    }

}
