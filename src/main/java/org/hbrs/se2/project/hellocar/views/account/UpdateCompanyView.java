package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.util.account.AccountValidation;
import org.hbrs.se2.project.hellocar.views.AppView;

@Route(value = Globals.Pages.UPDATE_COMPANY_VIEW, layout = AppView.class)
@RouteAlias("updatecompany")
public class UpdateCompanyView extends UpdateViewBase<CompanyDTOImpl>
{
	private TextField companyName;
	private TextField website;
	private Button submitButton;

	public UpdateCompanyView(ManageUserControl userControl)
	{
		super(new Binder<>(CompanyDTOImpl.class), userControl);
	}

	@Override
	protected void setupView()
	{
		// no custom setup required
	}

	@Override
	protected void setupCustomElements()
	{
		title.setText("Update Account Details");

		companyName = new TextField("Company Name");
		website = new TextField("Homepage");
	}

	@Override
	protected void setupSubmitButtons()
	{
		submitButton = new Button("Update");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		submitButton.addClickListener(event -> {
			try
			{
				var companyDTO = new CompanyDTOImpl();
				binder.writeBean(companyDTO);
				companyDTO.setProfilePicture(this.profilePicture);

				userService.updateUser(getCurrentUser().getId(), companyDTO);

				Utils.displayNotification(true, "Update succeeded");
			}
			catch (Exception e)
			{
				Utils.handleButtonException(e);
			}
		});

		setupDeleteButton();
	}

	@Override
	protected FormLayout setupForm()
	{
		FormLayout formLayout = new FormLayout();
		formLayout.add(
				title,
				companyName,
				userid,
				firstName,
				lastName,
				email,
				gender,
				password,
				passwordConfirm,
				street,
				zipCode,
				city,
				streetNumber,
				website,
				profilePictureUpload,
				about,
				submitButton,
				deleteButton
		);

		Utils.configureRegistrationForm(formLayout, title, submitButton, deleteButton, about);

		var user = (CompanyDTOImpl)userService.readUser(getCurrentUser().getId());
		binder.readBean(user);
		passwordConfirm.setValue(password.getValue());
		profilePicture = user.getProfilePicture();

		return formLayout;
	}
}
