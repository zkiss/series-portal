package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Regisztráció panel.
 * 
 * @author zoli
 */
public class RegisterPanel extends VerticalPanel{
	
	private Label loginNameLabel = new Label();
	private TextBox loginNameField = new TextBox();
	private Label passwordLabel = new Label();
	private PasswordTextBox passwordField = new PasswordTextBox();
	private Label passwordRetypeLabel = new Label();
	private PasswordTextBox passwordRetypeField = new PasswordTextBox();
	private Label emailLabel = new Label();
	private TextBox emailField = new TextBox();
	
	private Label errorMessage = new Label();
	
	public RegisterPanel() {
		initComponents();
	}
	
	private void initComponents() {
		errorMessage.setText("");
		errorMessage.addStyleName("errorMessage");
		
		loginNameLabel.setText("Login name");
		passwordLabel.setText("Password");
		passwordRetypeLabel.setText("Retype password");
		emailLabel.setText("Email");
		
		Grid grid = new Grid(4, 2);
		grid.setWidget(0, 0, loginNameLabel);
		grid.setWidget(0, 1, loginNameField);
		grid.setWidget(1, 0, passwordLabel);
		grid.setWidget(1, 1, passwordField);
		grid.setWidget(2, 0, passwordRetypeLabel);
		grid.setWidget(2, 1, passwordRetypeField);
		grid.setWidget(3, 0, emailLabel);
		grid.setWidget(3, 1, emailField);
		
		add(grid);
		add(errorMessage);
	}
	
	public String getLoginName() {
		return loginNameField.getText();
	}
	
	public String getPassword() {
		return passwordField.getText();
	}
	
	public String getEmail() {
		return emailField.getText();
	}
	
	public boolean isValid() {
		
		boolean loginValid = !getLoginName().isEmpty(); 
		boolean passwordValid = passwordField.getText().equals(
				passwordRetypeField.getText()) && !getPassword().isEmpty();
		//TODO jobb implementáció
		boolean emailValid = !getEmail().isEmpty();
		
		boolean isValid = loginValid && passwordValid && emailValid;
		
		if(!loginValid) {
			setErrorMessage("Login name is required!");
		} else if(!passwordValid) {
			setErrorMessage("Passwords are required and must match!");
		} else if(!emailValid) {
			setErrorMessage("Email is required!");
		}
		
		return isValid;
	}
	
	public void setErrorMessage(String message) {
		this.errorMessage.setText(message);
	}

}
