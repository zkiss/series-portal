package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegisteredEntityPanel extends VerticalPanel {

	private Label titleLabel = new Label();
	private Label descriptionLabel = new Label();
	
	public RegisteredEntityPanel() {
		initComponents();
	}
	
	public void initComponents() {
		add(titleLabel);
		add(descriptionLabel);
	}
	
	public void showEntity(SeriesDto entity) {
		titleLabel.setText(entity.getTitle());
		descriptionLabel.setText(entity.getDescription());
	}
}
