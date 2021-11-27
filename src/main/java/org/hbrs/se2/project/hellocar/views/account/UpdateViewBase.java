package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.ConfirmationDialog;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;

public abstract class UpdateViewBase<T extends JobPortalUserDTOImpl> extends AccountViewBase<T> implements BeforeEnterObserver
{
	protected Button deleteButton;

	public UpdateViewBase(Binder<T> customBinder, ManageUserControl userService)
	{
		super(customBinder, userService);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		// @todo funktioniert nicht.
		if (getCurrentUser() == null)
			event.forwardTo(Globals.Pages.LOGIN_VIEW);
	}

	protected void setupDeleteButton()
	{
		deleteButton = new Button("Delete account");
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
		deleteButton.addClickListener(event -> {
			try
			{
				new ConfirmationDialog(
						"Confirm deletion",
						"Are you sure you want to delete your account?",
						new Button("Delete", confirmEvent -> {
							userService.deleteUser(getCurrentUser().getId());

							Utils.displayNotification(true, "Deletion succeeded");

							// Logout
							UI ui = this.getUI().get();
							ui.getSession().close();
							ui.getPage().setLocation("/");

							UI.getCurrent().navigate(LoginView.class);
						}),
						new Button("Cancel", cancelEvent -> { })
				).open();
			}
			catch (Exception e)
			{
				Utils.displayNotification(false, "Unknown error: " + e);
			}
		});
	}
}
