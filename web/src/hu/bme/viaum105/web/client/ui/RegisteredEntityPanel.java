package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
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
	
	private ContentPanel contentPanel;
	
	Map<String, EpisodeDto> titleEpisodeMapping = new HashMap<String, EpisodeDto>();
	
	
	
	public RegisteredEntityPanel() {
	}
	
	public void showSeries(final SeriesDto entity) {
		clear();
		
		UserDto user = contentPanel.getMainPanel().getUser();
		
		add(titleLabel);
		add(descriptionLabel);
		
		titleLabel.setText(entity.getTitle());
		descriptionLabel.setText(entity.getDescription());
		
		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, likeLabel);
		grid.setWidget(0, 1, new Label(Long.toString(entity.getLikeCount())));
		grid.setWidget(1, 0, rateLabel);
		grid.setWidget(1, 1, new Label(Double.toString(entity.getRate())));
		
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
		
		if(user == null) {
			newEpisodeButton.setVisible(false);
		} else {
			newEpisodeButton.setVisible(true);
		}
	}
	
	public void showEpisode(EpisodeDto episode) {
		clear();
		
		add(titleLabel);
		add(descriptionLabel);
		
		titleLabel.setText(episode.getTitle());
		descriptionLabel.setText(episode.getDescription());
		
		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, likeLabel);
		grid.setWidget(0, 1, new Label(Long.toString(episode.getLikeCount())));
		grid.setWidget(1, 0, rateLabel);
		grid.setWidget(1, 1, new Label(Double.toString(episode.getRate())));
		
		Grid grid2 = new Grid(2, 2);
		grid2.setWidget(0, 0, actorLabel);
		grid2.setWidget(0, 1, new Label(episode.retrieveActorsAsString()));
		grid2.setWidget(1, 0, keywordLabel);
		grid2.setWidget(1, 1, new Label(episode.retrieveLabelsAsString()));
		
		add(grid);
		
		add(grid2);
						
	}
	
	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
}
