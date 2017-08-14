package com.github.blausql.ui;

import com.github.blausql.core.connection.ConnectionDefinition;
import com.github.blausql.ui.components.CloseOnEscapeKeyPressWindow;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.PasswordBox;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.dialog.DialogResult;

public class CredentialsDialog extends CloseOnEscapeKeyPressWindow {

	private final TextBox userNameTextBox;
	private final PasswordBox passwordPasswordBox;
	
	private DialogResult dialogResult = DialogResult.CANCEL;
	
	public CredentialsDialog(ConnectionDefinition cd) {
		
		super("Enter credentials for " + cd.getConnectionName());

		addComponent(new Label("User name:"));
		addComponent(userNameTextBox =
				new TextBox(cd != null ? cd.getUserName() : null, 20));

		addComponent(new Label("Password:"));
		addComponent(passwordPasswordBox =
				 new PasswordBox(cd != null ? cd.getPassword() : null, 20));
		
        Button okButton = new Button("OK", new Action() {
            public void doAction()
            {
                dialogResult = DialogResult.OK;
                close();
            }
        });
        Button cancelButton = new Button("Cancel", new Action() {
            public void doAction()
            {
                dialogResult = DialogResult.CANCEL;
                close();
            }
        });
        
        int labelWidth = userNameTextBox.getPreferredSize().getColumns();
        Panel buttonPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
        int leftPadding = 0;
        int buttonsWidth = okButton.getPreferredSize().getColumns() +
                cancelButton.getPreferredSize().getColumns() + 1;
        if(buttonsWidth < labelWidth)
            leftPadding = (labelWidth - buttonsWidth) / 2;
        if(leftPadding > 0)
            buttonPanel.addComponent(new EmptySpace(leftPadding, 1));
        buttonPanel.addComponent(okButton);
        buttonPanel.addComponent(cancelButton);
        addComponent(new EmptySpace());
        addComponent(buttonPanel);
        setFocus(okButton);
	}
	
	public DialogResult getDialogResult() {
		return dialogResult;
	}
	
	public String getUserName() {
		return userNameTextBox.getText();
	}
	
	public String getPassword() {
		return passwordPasswordBox.getText();
	}
}