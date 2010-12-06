package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.client.service.RegisteredEntityServiceAsync;
import hu.bme.viaum105.web.shared.dto.persistent.CommentDto;
import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegisteredEntityPanel extends VerticalPanel {

	private Label titleLabel = new Label();
	private Label descriptionLabel = new Label();
	
	private Label directorLabel = new Label("Director");
	private Label actorLabel = new Label("Actors");
	private Label keywordLabel = new Label("Keywords");
	
	private Label imdbUrlLabel = new Label("IMDB URL");
	
	private Label likeLabel = new Label("Like");
	private Label rateLabel = new Label("Rate");
	
	private Button newEpisodeButton = new Button("new episode");
	private Button likeButton = new Button("Like");
	
	private ListBox rateBox = new ListBox();
	private Button rateButton = new Button("Rate");
	
	private ContentPanel contentPanel;
	
	HorizontalPanel ratePanel = new HorizontalPanel();
	
	Map<String, EpisodeDto> titleEpisodeMapping = new HashMap<String, EpisodeDto>();
	
	RegisteredEntityDto entity;
	UserDto user;
	
	RegisteredEntityServiceAsync entityService = 
		(RegisteredEntityServiceAsync) GWT.create(RegisteredEntityService.class);
	
	ApprovablePanel addCommentPanel = new ApprovablePanel();
	final AddCommentPanel cPanel = new AddCommentPanel();
	
	public RegisteredEntityPanel() {
		((ServiceDefTarget) entityService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "RegisteredEntityService");
		
		titleLabel.addStyleName("title");
		
		for(int i=1; i<11; i++) {
			rateBox.addItem(Integer.toString(i));
		}
		
		likeButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				entityService.like(entity, user, new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						Window.alert(caught.getLocalizedMessage());
					}

					public void onSuccess(Void result) {
						contentPanel.showBrowseSeries();
					}

				});
			}
		});
		
		rateButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				int rate = Integer.valueOf(rateBox.getItemText(rateBox.getSelectedIndex()));
				entityService.rate(entity, user, rate, new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						Window.alert(caught.getLocalizedMessage());
					}

					public void onSuccess(Void result) {
						contentPanel.showBrowseSeries();
					}
				});
			}
		});
		
		addCommentPanel.add(cPanel);
		
		addCommentPanel.getApproveButton().setText("Submit");
		addCommentPanel.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if(cPanel.isValid()) {
					entityService.addComment(entity, user, cPanel.getComment(), new AsyncCallback<Void>() {

						public void onFailure(Throwable caught) {
							Window.alert(caught.getLocalizedMessage());
						}

						public void onSuccess(Void result) {
							contentPanel.showBrowseSeries();
						}
					});
				}
			}
		});
	}
	
	public void showSeries(final SeriesDto entity) {
		clear();
		
		this.user = contentPanel.getMainPanel().getUser();
		this.entity = entity;
		
		add(titleLabel);
		add(descriptionLabel);
		
		titleLabel.setText(entity.getTitle());
		descriptionLabel.setText(entity.getDescription());
		
		Grid grid = new Grid(2, 3);
		grid.setWidget(0, 0, likeLabel);
		grid.setWidget(0, 1, new Label(Long.toString(entity.getLikeCount())));
		grid.setWidget(0, 2, likeButton);
		grid.setWidget(1, 0, rateLabel);
		if(entity.getRate() == null) {
			grid.setWidget(1, 1, new Label("unrated"));
		} else {
			grid.setWidget(1, 1, new Label(Double.toString(entity.getRate())));
		}
		
		ratePanel.add(rateBox);
		ratePanel.add(rateButton);
		grid.setWidget(1, 2, ratePanel);
		
		Grid grid2 = new Grid(4, 2);
		grid2.setWidget(0, 0, directorLabel);
		grid2.setWidget(0, 1, new Label(entity.getDirector()));
		grid2.setWidget(1, 0, actorLabel);
		grid2.setWidget(1, 1, new Label(entity.retrieveActorsAsString()));
		grid2.setWidget(2, 0, keywordLabel);
		grid2.setWidget(2, 1, new Label(entity.retrieveLabelsAsString()));
		grid2.setWidget(3, 0, imdbUrlLabel);
		grid2.setWidget(3, 1, new Label(entity.getImdbUrl()));
		
		add(grid);
		
		add(grid2);
		
		add(new Label("Episodes:"));
		
		if(entity.getEpisodes().isEmpty()) {
			add(new Label("No episode found!"));
		} else {
			
			for(EpisodeDto episode : entity.getEpisodes()) {
				titleEpisodeMapping.put(episode.getTitle(), episode);
				
				final Anchor anchor = new Anchor(episode.getTitle());
				anchor.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						showEpisode(titleEpisodeMapping.get(anchor.getText()));
					}
				});
				add(anchor);
			}
		}
		
		newEpisodeButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				contentPanel.setActSeries(entity);
				contentPanel.showCreateEpisode();
			}
		});
		
		add(newEpisodeButton);
		
		cPanel.reset();
		showCommentPanel();
		
		if(user == null) {
			newEpisodeButton.setVisible(false);
			likeButton.setVisible(false);
			ratePanel.setVisible(false);
			addCommentPanel.setVisible(false);
		} else {
			newEpisodeButton.setVisible(true);
			likeButton.setVisible(true);
			ratePanel.setVisible(true);
			addCommentPanel.setVisible(true);
		}
	}
	
	public void showEpisode(EpisodeDto episode) {
		clear();
		
		this.user = contentPanel.getMainPanel().getUser();
		this.entity = episode;
		
		add(titleLabel);
		add(descriptionLabel);
		
		titleLabel.setText(episode.getTitle());
		descriptionLabel.setText(episode.getDescription());
		
		Grid grid = new Grid(2, 3);
		grid.setWidget(0, 0, likeLabel);
		grid.setWidget(0, 1, new Label(Long.toString(episode.getLikeCount())));
		grid.setWidget(0, 2, likeButton);
		grid.setWidget(1, 0, rateLabel);
		if(entity.getRate() == null) {
			grid.setWidget(1, 1, new Label("unrated"));
		} else {
			grid.setWidget(1, 1, new Label(Double.toString(entity.getRate())));
		}
		
		ratePanel.add(rateBox);
		ratePanel.add(rateButton);
		grid.setWidget(1, 2, ratePanel);
		
		Grid grid2 = new Grid(2, 2);
		grid2.setWidget(0, 0, actorLabel);
		grid2.setWidget(0, 1, new Label(episode.retrieveActorsAsString()));
		grid2.setWidget(1, 0, keywordLabel);
		grid2.setWidget(1, 1, new Label(episode.retrieveLabelsAsString()));
		
		add(grid);		
		add(grid2);
		
		if(user == null) {
			likeButton.setVisible(false);
			ratePanel.setVisible(false);
			addCommentPanel.setVisible(false);
		} else {
			likeButton.setVisible(true);
			ratePanel.setVisible(true);
			addCommentPanel.setVisible(true);
		}
		
		cPanel.reset();
		showCommentPanel();
						
	}
	
	public void showCommentPanel() {
		add(addCommentPanel);
		
		entityService.listApprovedComments(entity.getId(), new AsyncCallback<List<CommentDto>>() {

			public void onFailure(Throwable caught) {
				Window.alert(caught.getLocalizedMessage());
			}

			public void onSuccess(List<CommentDto> result) {
				
				for(CommentDto comment : result) {
					CommentPanel cPanel = new CommentPanel();
					cPanel.showComment(comment);
					add(cPanel);
				}
			}
		});
	}
	
	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
}
