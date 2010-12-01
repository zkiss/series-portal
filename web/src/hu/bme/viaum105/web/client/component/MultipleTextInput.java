package hu.bme.viaum105.web.client.component;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * GUI komponens, ami több szöveges érték egyidejű megadására szolgál.
 * 
 * @author zoli
 */
public class MultipleTextInput extends HorizontalPanel {

	TextBox inputBox = new TextBox();
	Button addButton = new Button(">>");
	TextArea inputArea = new TextArea();
	
	List<String> inputList = new LinkedList<String>();
	
	public MultipleTextInput() {
		initComponents();
	}
	
	public void initComponents() {
		
		inputArea.setEnabled(false);
		
		addButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if(!inputBox.getText().isEmpty()) {
					String textsoFar = inputArea.getText();
					
					if(textsoFar.isEmpty()) {
						inputArea.setText(inputBox.getText());
					} else {
						inputArea.setText(textsoFar+"\n"+inputBox.getText());						
					}
					
					inputList.add(inputBox.getText());
					inputBox.setText("");
				}
			}
		});
		
		add(inputBox);
		add(addButton);
		add(inputArea);
	}
	
	/**
	 * Visszaadja a begépelt szövegek listájának másolatát (deep copy).
	 * 
	 * @return A lemásolt lista.
	 */
	public List<String> getInputList() {
		List<String> copy = new LinkedList<String>();
		
		for(String s : inputList) {
			copy.add(new String(s));
		}
		
		return copy;
	}
	
	public void reset() {
		inputList.clear();
		inputBox.setText("");
		inputArea.setText("");
	}
}
