package hu.bme.viaum105.web.client.ui;

import java.util.List;

import hu.bme.viaum105.web.client.component.MultipleTextInput;

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
	Label seasonNumberLabel = new Label("Season");
	TextBox seasonNumberField = new TextBox();
	Label episodeNumberLabel = new Label("Episode");
	TextBox episodeNumberField = new TextBox();
	Label actorsLabel = new Label("Actors");
	MultipleTextInput actorsField = new MultipleTextInput();
	Label keyWordsLabel = new Label("Keywords");
	MultipleTextInput keyWordsField = new MultipleTextInput();
	Label lengthLabel = new Label("Length");
	TextBox lengthField = new TextBox();
	
	private Label errorMessage = new Label("");
	
	public CreateEpisodeForm() {
		initComponents();
	}
	
	public void initComponents() {
		errorMessage.addStyleName("errorMessage");
		
		Grid grid = new Grid(7, 2);
		
		grid.setWidget(0, 0, titleLabel);
		grid.setWidget(0, 1, titleField);
		grid.setWidget(1, 0, descriptionLabel);
		grid.setWidget(1, 1, descriptionField);
		grid.setWidget(2, 0, seasonNumberLabel);
		grid.setWidget(2, 1, seasonNumberField);
		grid.setWidget(3, 0, episodeNumberLabel);
		grid.setWidget(3, 1, episodeNumberField);
		grid.setWidget(4, 0, actorsLabel);
		grid.setWidget(4, 1, actorsField);
		grid.setWidget(5, 0, keyWordsLabel);
		grid.setWidget(5, 1, keyWordsField);
		grid.setWidget(6, 0, lengthLabel);
		grid.setWidget(6, 1, lengthField);
		
		add(grid);
		add(errorMessage);
	}
	
	public String getEpisodeTitle() {
		return titleField.getText();
	}
	
	public String getDescription() {
		return descriptionField.getText();
	}
	
	public int getSeasonNumber() {
		return Integer.valueOf(seasonNumberField.getText());
	}
	
	public int getEpisodeNumber() {
		return Integer.valueOf(episodeNumberField.getText());
	}
	
	public int getLenght() {
		return Integer.valueOf(lengthField.getText());
	}
	
	public List<String> getActors() {
		return actorsField.getInputList();
	}
	
	public List<String> getKeywords() {
		return keyWordsField.getInputList();
	}
	
	public boolean isValid() {
		boolean titleInvalid = !titleField.getText().isEmpty();
		boolean descriptionInvalid = !descriptionField.getText().isEmpty();
		boolean seasonNumberInvalid = !seasonNumberField.getText().isEmpty();
		boolean episodeNumberInvalid = !episodeNumberField.getText().isEmpty();
		boolean lengthInvalid = !lengthField.getText().isEmpty();
		boolean actorValid = !actorsField.getInputList().isEmpty();
		boolean keywordValid = !keyWordsField.getInputList().isEmpty();
		
		if(!titleInvalid) {
			setErrorMessage("Title is required!");
		} else if(!descriptionInvalid) {
			setErrorMessage("Description is required!");
		} else if(!seasonNumberInvalid) {
			setErrorMessage("Season number is required!");
		} else if(!episodeNumberInvalid) {
			setErrorMessage("Episode number is required!");
		} else if(!lengthInvalid) {
			setErrorMessage("Length is required!");
		} else if(!actorValid) {
			setErrorMessage("At least one actor is required!");
		} else if(!keywordValid) {
			setErrorMessage("At least one keyword is required!");
		}
		
		try{
			Integer.valueOf(lengthField.getText());
		} catch (NumberFormatException e) {
			setErrorMessage("Length must be a number!");
			return false;
		}
		
		try{
			Integer.valueOf(seasonNumberField.getText());
		} catch (NumberFormatException e) {
			setErrorMessage("Season number must be a number!");
			return false;
		}
		
		try{
			Integer.valueOf(episodeNumberField.getText());
		} catch (NumberFormatException e) {
			setErrorMessage("Episode number must be a number!");
			return false;
		}
		
		return titleInvalid && descriptionInvalid && seasonNumberInvalid 
							&& episodeNumberInvalid && lengthInvalid 
							&& actorValid && keywordValid;
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

}
