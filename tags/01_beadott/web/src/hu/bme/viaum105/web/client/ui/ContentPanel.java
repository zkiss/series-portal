package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.client.service.RegisteredEntityServiceAsync;
import hu.bme.viaum105.web.client.service.UserService;
import hu.bme.viaum105.web.client.service.UserServiceAsync;
import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

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
	
	ApprovablePanel createEpisodePanel = GWT.create(ApprovablePanel.class);
	
	UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	RegisteredEntityServiceAsync entityService = 
		(RegisteredEntityServiceAsync) GWT.create(RegisteredEntityService.class);
		
	SeriesDto actSeries;
	
	final CreateSerieForm serieForm = GWT.create(CreateSerieForm.class);
	final ModifyProfilForm profilForm = GWT.create(ModifyProfilForm.class);
	final CreateEpisodeForm episodeForm = new CreateEpisodeForm();
	
	public ContentPanel() {
		((ServiceDefTarget) userService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "UserService");
		
		((ServiceDefTarget) entityService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "RegisteredEntityService");
		
		initComponents();
	}
	
	public void initComponents() {
		
		createSeriePanel.add(serieForm);
		createSeriePanel.getApproveButton().setText("Submit");
		createSeriePanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if(serieForm.isValid()) {
					serieForm.setErrorMessage("");
					
					SeriesDto series = new SeriesDto();
					series.setTitle(serieForm.getSeriesTitle());
					series.setDirector(serieForm.getDirector());
					for(String actor : serieForm.getActors()) {
						series.addActor(actor);
					}
					series.setDescription(serieForm.getDescription());
					series.setImdbUrl(serieForm.getImdbUrl());
					for(String keyWord : serieForm.getKeyWords()) {
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
		
		modifyProfilPanel.add(profilForm);
		modifyProfilPanel.getApproveButton().setText("Submit");
		modifyProfilPanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				if(profilForm.isValid()) {
					
					userService.login(mainPanel.getUser().getLoginName(), profilForm.getOldPassword(), new AsyncCallback<UserDto>() {

						public void onFailure(Throwable caught) {
							Window.alert(caught.getLocalizedMessage());
						}

						public void onSuccess(UserDto result) {
							
							if(result == null) {
								profilForm.setErrorMessage("Old password is not valid");
							
							} else {
								long id = mainPanel.getUser().getId();
								String pwd = profilForm.getNewPassword();
								
								userService.changePassword(id, pwd, new AsyncCallback<Void>() {
									
									public void onSuccess(Void result) {
										showBrowseSeries();
									}
									
									public void onFailure(Throwable caught) {
										Window.alert(caught.getLocalizedMessage());
									}
								});
							}
						}
						
					});
					
				}
			}
		});

		createEpisodePanel.add(episodeForm);
		createEpisodePanel.getApproveButton().setText("Submit");
		createEpisodePanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				if(episodeForm.isValid()) {
					episodeForm.setErrorMessage("");
					
					EpisodeDto episode = new EpisodeDto();
					episode.setTitle(episodeForm.getEpisodeTitle());
					episode.setDescription(episodeForm.getDescription());
					episode.setSeasonNumber(episodeForm.getSeasonNumber());
					episode.setEpisodeNumber(episodeForm.getEpisodeNumber());
					episode.setLengthMinutes(episodeForm.getLenght());
					for(String s : episodeForm.getActors()) {
						episode.addActor(s);
					}
					for(String s : episodeForm.getKeywords()) {
						episode.addLabel(s);
					}
					episode.setSeries(actSeries);
					
					entityService.createNewEpisode(episode, new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
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
		entityPanel.setContentPanel(this);
		
		add(listPanel);
		add(createSeriePanel);
		add(modifyProfilPanel);
		add(entityPanel);
		add(createEpisodePanel);
		
		showBrowseSeries();
	}
	
	public void setMainPanel(WebMainPanel panel) {
		this.mainPanel = panel;
	}
	
	public void setActSeries(SeriesDto actSeries) {
		this.actSeries = actSeries;
	}
	
	public WebMainPanel getMainPanel() {
		return mainPanel;
	}
	
	public void showBrowseSeries() {
		listPanel.searchForEntites();
		showWidget(0);
	}
	
	public void showCreateSerie() {
		serieForm.resetFields();
		showWidget(1);
	}
	
	public void showProfil() {
		profilForm.resetFields();
		showWidget(2);
	}
	
	
	public void showDetails(RegisteredEntityDto entity) {
		
		if(entity instanceof SeriesDto) {
			entityPanel.showSeries((SeriesDto)entity);			
		} else if(entity instanceof EpisodeDto) {
			entityPanel.showEpisode((EpisodeDto) entity);
		}
		
		showWidget(3);
	}
	
	public void showCreateEpisode() {
		episodeForm.resetFields();
		showWidget(4);
	}

}
