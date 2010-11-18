package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Bejelentkezés panel.
 * 
 * @author zoli
 */
public class LoginPanel extends VerticalPanel{
	
	Label loginNameLabel = new Label();
	TextBox loginNameField = new TextBox();
	Label passwordLabel = new Label();
	PasswordTextBox passwordField = new PasswordTextBox();
	
	Label errorMessage = new Label(); 
	
	public LoginPanel() {
		initComponents();
	}
	
	/**
	 * Komponensek inicializálása.
	 */
	public void initComponents() {
		loginNameLabel.setText("Login name");
		passwordLabel.setText("Password");
		
		errorMessage.addStyleName("errorMessage");
		errorMessage.setText("");
		
		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, loginNameLabel);
		grid.setWidget(0, 1, loginNameField);
		grid.setWidget(1, 0, passwordLabel);
		grid.setWidget(1, 1, passwordField);
		
		add(grid);
		add(errorMessage);
		
	}
	
	public String getLoginName() {
		return loginNameField.getText();
	}
	
	public String getPassword() {
		return passwordField.getText();
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

}
