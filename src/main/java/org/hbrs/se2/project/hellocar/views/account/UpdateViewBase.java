package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.ConfirmationDialog;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

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

	@Override
	protected void setupCustomRequiredIndicators()
	{
		// no custom setup needed
	}

	@Override
	protected void setupCustomValidation()
	{
		binder.forField(userid)
				.withValidator(
						AccountValidation::usernameValidator,
						AccountValidation.USERNAME_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getUserid, JobPortalUserDTOImpl::setUserid);

		binder.forField(email)
				.withValidator(new EmailValidator(AccountValidation.EMAIL_ERROR_MESSAGE))
				.bind(JobPortalUserDTOImpl::getEmail, JobPortalUserDTOImpl::setEmail);
	}
}
