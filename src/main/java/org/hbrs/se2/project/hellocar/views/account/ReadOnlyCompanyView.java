package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.apache.commons.codec.binary.Base64;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.AppView;

@Route(value = Globals.Pages.READONLY_COMPANY_VIEW, layout = AppView.class)
@RouteAlias("viewcompany")
public class ReadOnlyCompanyView extends ReadOnlyViewBase<CompanyDTOImpl> implements HasUrlParameter<Integer>
{
	private TextField companyName;
	private TextField website;
	private int id;

	public ReadOnlyCompanyView(ManageUserControl userControl)
	{
		super(new Binder<>(CompanyDTOImpl.class), userControl);
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Integer id)
	{
		this.id = id;
	}

	@Override
	protected void setupView()
	{
		// no custom setup required
	}

	@Override
	protected void setupCustomElements()
	{
		super.setupCustomElements();

		title.setText("Company details");

		companyName = new TextField("Company Name");
		companyName.setReadOnly(true);
		website = new TextField("Homepage");
		website.setReadOnly(true);
	}

	@Override
	protected void setupSubmitButtons()
	{
		CompanyDTOImpl user = (CompanyDTOImpl)userService.readUser(id);
		binder.readBean(user);
		profilePicture.setSrc("data:image/png;base64,"
				+ Base64.encodeBase64String(user.getProfilePicture()));
	}

	@Override
	protected FormLayout setupForm()
	{
		FormLayout formLayout = new FormLayout();
		formLayout.add(
				title,
				companyName,
				street,
				zipCode,
				city,
				streetNumber,
				website,
				profilePicture,
				about
		);

		Utils.configureRegistrationForm(formLayout, title, null, null, about);

		return formLayout;
	}

	@Override
	protected void setupCustomRequiredIndicators()
	{
		// no custom setup needed
	}

	@Override
	protected void setupCustomValidation()
	{
		// no custom setup needed
	}
}
