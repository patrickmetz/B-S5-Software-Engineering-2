package org.hbrs.se2.project.hellocar.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmationDialog extends Dialog
{
	private final Label title;
	private final Label question;
	private final Button confirm;
	private final Button cancel;

	public ConfirmationDialog(
			String title,
			String question,
			Button confirm,
			Button cancel)
	{
		this.title = new Label(title);
		this.question = new Label(question);
		this.confirm = confirm;
		this.confirm.addClickListener(confirmEvent -> close());
		this.cancel = cancel;
		this.cancel.addClickListener(cancelEvent -> close());

		var header = new HorizontalLayout();
		header.add(this.title);
		header.setFlexGrow(1, this.title);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		add(header);

		var content = new VerticalLayout();
		content.add(this.question);
		content.setPadding(false);
		add(content);

		var footer = new HorizontalLayout();
		footer.add(cancel, confirm);
		footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
		add(footer);
	}
}
