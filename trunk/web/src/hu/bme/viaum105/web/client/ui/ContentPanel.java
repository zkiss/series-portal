package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DeckPanel;

public class ContentPanel extends DeckPanel {
	
	ApprovablePanel createSeriePanel = GWT.create(ApprovablePanel.class);
	ApprovablePanel modifyProfilPanel = GWT.create(ApprovablePanel.class);
	
	ListPanel listPanel = GWT.create(ListPanel.class);
	
	RegisteredEntityPanel entityPanel = GWT.create(RegisteredEntityPanel.class);
		
	public ContentPanel() {
		initComponents();
	}
	
	public void initComponents() {
		
		CreateSerieForm panel = GWT.create(CreateSerieForm.class);
		
		createSeriePanel.add(panel);
		createSeriePanel.getApproveButton().setText("Submit");
		createSeriePanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				System.out.println("onclick");
			}
		});
		
		ModifyProfilForm panel2 = GWT.create(ModifyProfilForm.class);
		
		modifyProfilPanel.add(panel2);
		modifyProfilPanel.getApproveButton().setText("Submit");
		modifyProfilPanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		listPanel.setContentPanel(this);
		
		add(listPanel);
		add(createSeriePanel);
		add(modifyProfilPanel);
		add(entityPanel);
		
		showBrowseSeries();
	}
	
	
	public void showBrowseSeries() {
		showWidget(0);
	}
	
	public void showCreateSerie() {
		showWidget(1);
	}
	
	public void showProfil() {
		showWidget(2);
	}
	
	
	public void showDetails(RegisteredEntityDto entity) {
		
		if(entity instanceof SeriesDto) {
			entityPanel.showEntity((SeriesDto)entity);			
		}
		
		showWidget(3);
	}

}
