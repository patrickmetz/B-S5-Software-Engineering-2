package org.hbrs.se2.project.hellocar.views.account;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.impl.account.JobPortalUserDTOImpl;
import org.hbrs.se2.project.hellocar.util.ConfirmationDialog;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;

public abstract class ReadOnlyViewBase<T extends JobPortalUserDTOImpl> extends AccountViewBase<T>
{
	protected Image profilePicture;

	public ReadOnlyViewBase(Binder<T> customBinder, ManageUserControl userService)
	{
		super(customBinder, userService);
	}

	@Override
	protected void setupCustomElements()
	{
		profilePicture = new Image();

		firstName.setReadOnly(true);
		lastName.setReadOnly(true);
		userid.setReadOnly(true);
		gender.setReadOnly(true);
		email.setReadOnly(true);
		password.setReadOnly(true);
		passwordConfirm.setReadOnly(true);
		street.setReadOnly(true);
		zipCode.setReadOnly(true);
		city.setReadOnly(true);
		streetNumber.setReadOnly(true);
		about.setReadOnly(true);
	}
}
