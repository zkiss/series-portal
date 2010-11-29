package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateEpisodeForm extends VerticalPanel {
	
	Label titleLabel = new Label("Title");
	TextBox titleField = new TextBox();
	Label descriptionLabel = new Label("Description");
	TextArea descriptionField = new TextArea();
	Label lengthLabel = new Label("Length");
	TextBox lengthField = new TextBox();
	
	private Label errorMessage = new Label("");
	
	public CreateEpisodeForm() {
		initComponents();
	}
	
	public void initComponents() {
		errorMessage.addStyleName("errorMessage");
		
		Grid grid = new Grid(3, 2);
		
		grid.setWidget(0, 0, titleLabel);
		grid.setWidget(0, 1, titleField);
		grid.setWidget(1, 0, descriptionLabel);
		grid.setWidget(1, 1, descriptionField);
		grid.setWidget(2, 0, lengthLabel);
		grid.setWidget(2, 1, lengthField);
		
		add(grid);
		add(errorMessage);
	}
	
	public boolean isValid() {
		boolean titleValid = !titleField.getText().isEmpty();
		boolean descriptionValid = !descriptionField.getText().isEmpty();
		boolean lengthValid = !lengthField.getText().isEmpty();
		
		try{
			Integer.valueOf(lengthField.getText());
		} catch (NumberFormatException e) {
			setErrorMessage("Length must be a number!");
			return false;
		}
		
		if(!titleValid) {
			setErrorMessage("Title is required!");
		} else if(!descriptionValid) {
			setErrorMessage("Description is required!");
		} else if(!lengthValid) {
			setErrorMessage("Length is required!");
		}
		
		return titleValid && descriptionValid && lengthValid;
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

}
