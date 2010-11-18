package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateSeriePanel extends VerticalPanel{
	
	private Label titleLabel = new Label("Title");
	private TextBox titleField = new TextBox();
	private Label summaryLabel = new Label("Summary");
	private TextArea summaryField = new TextArea();
	private Label descriptionLabel = new Label("Description");
	private TextArea descriptionField = new TextArea();
	private Label imdbUrlLabel = new Label("IMDB URL");
	private TextBox imdbUrlField = new TextBox();
	
	private Label errorMessage = new Label("");
	
	public CreateSeriePanel() {
		initComponents();
	}
	
	private void initComponents() {
		
		errorMessage.addStyleName("errorMessage");
		
		Grid grid = new Grid(4, 2);
		
		grid.setWidget(0, 0, titleLabel);
		grid.setWidget(0, 1, titleField);
		grid.setWidget(1, 0, summaryLabel);
		grid.setWidget(1, 1, summaryField);
		grid.setWidget(2, 0, descriptionLabel);
		grid.setWidget(2, 1, descriptionField);
		grid.setWidget(3, 0, imdbUrlLabel);
		grid.setWidget(3, 1, imdbUrlField);
		
		add(grid);
		add(errorMessage);
	}
	
	public boolean isValid() {
		boolean titleValid = !titleField.getText().isEmpty();
		boolean summaryValid = !summaryField.getText().isEmpty();
		boolean descriptionValid = !descriptionField.getText().isEmpty();
		boolean imdbUrlValid = !imdbUrlField.getText().isEmpty();
		
		if(!titleValid) {
			setErrorMessage("Title is required!");
		} else if(!summaryValid) {
			setErrorMessage("Summary is required!");
		} else if(!descriptionValid) {
			setErrorMessage("Description is required!");
		} else if(!imdbUrlValid) {
			setErrorMessage("IMDB URL is required!");
		}
		
		return titleValid && summaryValid && descriptionValid && imdbUrlValid;
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

}
