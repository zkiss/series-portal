package hu.bme.viaum105.web.client.ui;

import java.util.List;

import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.client.service.RegisteredEntityServiceAsync;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchPanel extends VerticalPanel {
	
	ListBox searchInBox = new ListBox();
	TextBox searhField = new TextBox();
	Button searchButton = new Button("Search");
	
	ContentPanel contentPanel;
	
	RegisteredEntityServiceAsync entityService = 
		(RegisteredEntityServiceAsync) GWT.create(RegisteredEntityService.class);
	
	enum Search {
		TITLE,
		DESCRIPTION,
		DIRECTOR,
		LABEL,
		ACTOR
	};
	
	public SearchPanel() {
		((ServiceDefTarget) entityService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "RegisteredEntityService");
		
		initComponents();
	}
	
	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public void initComponents() {
		searchInBox.addItem("Title", Search.TITLE.toString());
		searchInBox.addItem("Description", Search.DESCRIPTION.toString());
		searchInBox.addItem("Director", Search.DIRECTOR.toString());
		searchInBox.addItem("Label", Search.LABEL.toString());
		searchInBox.addItem("Actor", Search.ACTOR.toString());
		
		searchButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				String selected = searchInBox.getValue(searchInBox.getSelectedIndex());
				String term = searhField.getText();
				
				if(selected.equals(Search.TITLE.toString())) {
					entityService.searchByTitle(term, new SearchAsyncCallBack());
				} else if(selected.equals(Search.DESCRIPTION.toString())) {
					entityService.searchByDescription(term, new SearchAsyncCallBack());
				} else if(selected.equals(Search.DIRECTOR.toString())) {
					entityService.searchByDirector(term, new SearchAsyncCallBack());
				} else if(selected.equals(Search.LABEL.toString())) {
					entityService.searchByLabel(term, new SearchAsyncCallBack());
				} else if(selected.equals(Search.ACTOR.toString())) {
					entityService.searchByActor(term, new SearchAsyncCallBack());
				}
			}
		});
		
		HorizontalPanel panel = new HorizontalPanel();
		panel.add(searhField);
		panel.add(searchButton);
		
		add(searchInBox);
		add(panel);
	}
	
	class SearchAsyncCallBack implements AsyncCallback<List<RegisteredEntityDto>> {
		
		public void onSuccess(List<RegisteredEntityDto> result) {
			contentPanel.showSearchResult(result);
		}
		
		public void onFailure(Throwable caught) {
			Window.alert(caught.getLocalizedMessage());
		}
	}
}
