package hu.bme.viaum105.web.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchPanel extends VerticalPanel {
	
	ListBox searchInBox = new ListBox();
	TextBox searhField = new TextBox();
	Button searchButton = new Button("Search");
	
	enum Search {
		TITLE,
		DESCRIPTION,
		DIRECTOR,
		LABEL,
		ACTOR
	};
	
	public SearchPanel() {
		initComponents();
	}

	public void initComponents() {
		searchInBox.addItem("Title", Search.TITLE.toString());
		searchInBox.addItem("Description", Search.DESCRIPTION.toString());
		searchInBox.addItem("Director", Search.DIRECTOR.toString());
		searchInBox.addItem("Label", Search.LABEL.toString());
		searchInBox.addItem("Actor", Search.ACTOR.toString());
		
		searchButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
			}
		});
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.add(searhField);
		panel.add(searchButton);
		
		add(searchInBox);
		add(panel);
	}
}
