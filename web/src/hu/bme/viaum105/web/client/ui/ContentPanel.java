package hu.bme.viaum105.web.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DeckPanel;

public class ContentPanel extends DeckPanel {
	
	ApprovablePanel createSeriePanel = new ApprovablePanel();
	
	public ContentPanel() {
		initComponents();
	}
	
	public void initComponents() {
		
		createSeriePanel.add(new CreateSeriePanel());
		createSeriePanel.getApproveButton().setText("Submit");
		createSeriePanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				//TODO csinálunk vmit
			}
		});
		
		add(createSeriePanel);
		showWidget(0);
	}

}
