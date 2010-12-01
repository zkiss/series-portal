package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.client.service.RegisteredEntityServiceAsync;
import hu.bme.viaum105.web.client.service.UserService;
import hu.bme.viaum105.web.client.service.UserServiceAsync;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DeckPanel;

public class ContentPanel extends DeckPanel {
	
	WebMainPanel mainPanel;
	
	ApprovablePanel createSeriePanel = GWT.create(ApprovablePanel.class);
	
	ApprovablePanel modifyProfilPanel = GWT.create(ApprovablePanel.class);
	
	ListPanel listPanel = GWT.create(ListPanel.class);
	
	RegisteredEntityPanel entityPanel = GWT.create(RegisteredEntityPanel.class);
	
	UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	RegisteredEntityServiceAsync entityService = 
		(RegisteredEntityServiceAsync) GWT.create(RegisteredEntityService.class);
		
	
	public ContentPanel() {
		((ServiceDefTarget) userService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "UserService");
		
		((ServiceDefTarget) entityService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "RegisteredEntityService");
		
		initComponents();
	}
	
	public void initComponents() {
		
		final CreateSerieForm panel = GWT.create(CreateSerieForm.class);
		
		createSeriePanel.add(panel);
		createSeriePanel.getApproveButton().setText("Submit");
		createSeriePanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if(panel.isValid()) {
					panel.setErrorMessage("");
					
					SeriesDto series = new SeriesDto();
					series.setTitle(panel.getSeriesTitle());
					series.setDirector(panel.getDirector());
					for(String actor : panel.getActors()) {
						series.addActor(actor);
					}
					series.setDescription(panel.getDescription());
					series.setImdbUrl(panel.getImdbUrl());
					for(String keyWord : panel.getKeyWords()) {
						series.addLabel(keyWord);
					}
					
					entityService.createNewSeries(series, new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							showBrowseSeries();
						}
						
						public void onFailure(Throwable caught) {
							Window.alert(caught.getLocalizedMessage());
						}
					});
					
					showBrowseSeries();
				}
			}
		});
		
		final ModifyProfilForm panel2 = GWT.create(ModifyProfilForm.class);
		
		modifyProfilPanel.add(panel2);
		modifyProfilPanel.getApproveButton().setText("Submit");
		modifyProfilPanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				if(panel2.isValid()) {
					
					//TODO ellenőrizni kell a régi jelszót!
					
					long id = mainPanel.getUser().getId();
					String pwd = panel2.getNewPassword();
					
					userService.changePassword(id, pwd, new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							//TODO beállítani a megkapott userDTO-t
							showBrowseSeries();
						}
						
						public void onFailure(Throwable caught) {
							Window.alert(caught.getLocalizedMessage());
						}
					});
				}
			}
		});

		listPanel.setContentPanel(this);
		
		add(listPanel);
		add(createSeriePanel);
		add(modifyProfilPanel);
		add(entityPanel);
		
		showBrowseSeries();
	}
	
	public void setMainPanel(WebMainPanel panel) {
		this.mainPanel = panel;
	}
	
	public void showBrowseSeries() {
		listPanel.searchForEntites();
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
