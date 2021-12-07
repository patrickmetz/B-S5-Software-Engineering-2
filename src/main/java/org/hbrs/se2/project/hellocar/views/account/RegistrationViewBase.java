package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;

public abstract class RegistrationViewBase<T extends JobPortalUserDTOImpl> extends AccountViewBase<T>
{
	public RegistrationViewBase(Binder<T> customBinder, ManageUserControl userService)
	{
		super(customBinder, userService);
	}

	protected void setupRegistrationCommonValidation()
	{
		binder.forField(userid)
				.withValidator(
						AccountValidation::usernameValidator,
						AccountValidation.USERNAME_ERROR_MESSAGE
				)
				.withValidator(
						username -> AccountValidation.usernameAvailableValidator(username, userService),
						AccountValidation.USERNAME_IN_USE_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getUserid, JobPortalUserDTOImpl::setUserid);

		binder.forField(email)
				.withValidator(new EmailValidator(AccountValidation.EMAIL_ERROR_MESSAGE))
				.withValidator(
						email -> AccountValidation.emailAvailableValidator(email, userService),
						AccountValidation.EMAIL_IN_USE_ERROR_MESSAGE
				)
				.bind(JobPortalUserDTOImpl::getEmail, JobPortalUserDTOImpl::setEmail);
	}
}
