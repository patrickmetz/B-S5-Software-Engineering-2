package org.hbrs.se2.project.hellocar.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;

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

    public static void configureRegistrationForm(
            FormLayout formLayout,
            H3 title,
            Button submitButton) {
        configureRegistrationForm(formLayout, title, submitButton, null, null);
    }

    public static void configureRegistrationForm(
            FormLayout formLayout,
            H3 title,
            Button submitButton,
            Button secondaryButton,
            TextArea about
    ) {
        if (submitButton != null)
        {
            submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // @todo überschreibt was in den view klassen gesetzt wurde!?
            submitButton.addClassName("registerButton");
        }

        formLayout.addClassName("registerForm");
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        formLayout.setColspan(title, 2);
        formLayout.setColspan(about, 2);

        // These components always take full width
        if (secondaryButton != null) {
            secondaryButton.addClassName("goBackButton");
            formLayout.setColspan(submitButton, 2);
            formLayout.setColspan(secondaryButton, 2);
        } else if (submitButton != null) {
            formLayout.setColspan(submitButton, 2);
        }
    }

    public static void displayNotification(boolean success, String message) {
        displayNotification(success, new Text(message));
    }

    public static void displayNotification(boolean success, Component component) {
        Notification notification = new Notification(component);
        notification.addThemeVariants(success ? NotificationVariant.LUMO_SUCCESS : NotificationVariant.LUMO_ERROR);
        notification.setDuration(5000);
        notification.setPosition(Notification.Position.BOTTOM_CENTER);
        notification.open();
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
