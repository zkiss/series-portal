package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ModifyProfilForm extends VerticalPanel {
	
	private Label pwdLabel = new Label("Password");
	private TextBox pwdField = new TextBox();
	private Label newPwdLabel = new Label("New password");
	private TextBox newPwdField = new TextBox();
	private Label rePwdLabel = new Label("Retype password");
	private TextBox rePwdField = new TextBox();
	
	private Label errorMessage = new Label("");
	
	public ModifyProfilForm() {
		initComponents();
	}
	
	public void initComponents() {
		errorMessage.addStyleName("errorMessage");
		
		Grid grid = new Grid(3, 2);
		
		grid.setWidget(0, 0, pwdLabel);
		grid.setWidget(0, 1, pwdField);
		grid.setWidget(1, 0, newPwdLabel);
		grid.setWidget(1, 1, newPwdField);
		grid.setWidget(2, 0, rePwdLabel);
		grid.setWidget(2, 1, rePwdField);
		
		add(grid);
		add(errorMessage);
	}
	
	public String getNewPassword() {
		return rePwdField.getText();
	}
	
	public boolean isValid() {
		boolean pwdValid = !pwdField.getText().isEmpty();
		boolean pwd2Valid = !newPwdField.getText().isEmpty();
		boolean pwd3Valid = !rePwdField.getText().isEmpty();
		boolean matchesValid = newPwdField.getText().equals(rePwdField.getText());
		
		if(!pwdValid) {
			setErrorMessage("Password is required!");
		} else if(!pwd2Valid) {
			setErrorMessage("New password is required!");
		} else if(!pwd3Valid) {
			setErrorMessage("Retype password is required!");
		} else if(!matchesValid) {
			setErrorMessage("New passwords not match!");
		}
		
		return pwdValid && pwd2Valid && pwd3Valid && matchesValid;
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

}
