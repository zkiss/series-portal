package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

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
	
	public RegisteredEntityPanel() {
		initComponents();
	}
	
	public void initComponents() {
	}
	
	public void showEntity(SeriesDto entity) {
		clear();
		
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
	}
}
