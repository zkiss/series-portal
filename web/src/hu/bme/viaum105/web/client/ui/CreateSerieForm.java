package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.component.MultipleTextInput;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateSerieForm extends VerticalPanel{
	
	private Label titleLabel = new Label("Title");
	private TextBox titleField = new TextBox();
	private Label directorLabel = new Label("Director");
	private TextBox directorField = new TextBox();
	private Label actorLabel = new Label("Actor");
	private MultipleTextInput actorField = new MultipleTextInput();
	private Label descriptionLabel = new Label("Description");
	private TextArea descriptionField = new TextArea();
	private Label imdbUrlLabel = new Label("IMDB URL");
	private TextBox imdbUrlField = new TextBox();
	private Label keyWordLabel = new Label("Keyword");
	private MultipleTextInput keyWordField = new MultipleTextInput();
	
	private Label errorMessage = new Label("");
	
	public CreateSerieForm() {
		initComponents();
	}
	
	private void initComponents() {
		
		errorMessage.addStyleName("errorMessage");
		
		Grid grid = new Grid(6, 2);
		
		grid.setWidget(0, 0, titleLabel);
		grid.setWidget(0, 1, titleField);
		grid.setWidget(1, 0, directorLabel);
		grid.setWidget(1, 1, directorField);
		grid.setWidget(2, 0, actorLabel);
		grid.setWidget(2, 1, actorField);
		grid.setWidget(3, 0, descriptionLabel);
		grid.setWidget(3, 1, descriptionField);
		grid.setWidget(4, 0, imdbUrlLabel);
		grid.setWidget(4, 1, imdbUrlField);
		grid.setWidget(5, 0, keyWordLabel);
		grid.setWidget(5, 1, keyWordField);
		
		
		add(grid);
		add(errorMessage);
	}
	
	public boolean isValid() {
		boolean titleValid = !titleField.getText().isEmpty();
		boolean directorValid = !directorField.getText().isEmpty();
		boolean actorValid = !actorField.getInputList().isEmpty();
		boolean descriptionValid = !descriptionField.getText().isEmpty();
		boolean imdbUrlValid = !imdbUrlField.getText().isEmpty();
		boolean keyWordValid = !keyWordField.getInputList().isEmpty();
		
		for(String s : actorField.getInputList()) {
			System.out.println("actor: "+s+" - "+actorValid);
		}
		
		for(String s : keyWordField.getInputList()) {
			System.out.println("keyword: "+s+" - "+keyWordValid);
		}
		
		if(!titleValid) {
			setErrorMessage("Title is required!");
		} else if(!directorValid) {
			setErrorMessage("Director is required!");
		} else if(!actorValid) {
			setErrorMessage("At least one actor is required!");
		} else if(!descriptionValid) {
			setErrorMessage("Description is required!");
		} else if(!imdbUrlValid) {
			setErrorMessage("IMDB URL is required!");
		} else if(!keyWordValid) {
			setErrorMessage("At least one keyword is required!");
		}
		
		return titleValid && directorValid && actorValid 
			&& descriptionValid && imdbUrlValid && keyWordValid;
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

}
