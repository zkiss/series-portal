package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddCommentPanel extends VerticalPanel {
	
	Label commentLabel = new Label("Comment");
	TextArea commentField = new TextArea();
	
	Label errorMessage = new Label("");
	
	public AddCommentPanel() {
		initComponents();
	}
	
	public String getComment() {
		return commentField.getText();
	}
	
	public void initComponents() {
		add(commentLabel);
		add(commentField);
		add(errorMessage);
	}
	
	public boolean isValid() {
		if(commentField.getText().isEmpty()) {
			errorMessage.setText("No comment added!");
			return false;
		}
		
		return true;
	}
	
	public void setErrorMessage(String msg) {
		errorMessage.setText(msg);
	}
	
	public void reset() {
		commentField.setText("");
	}

}
