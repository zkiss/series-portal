package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegisteredEntitySummaryPanel extends VerticalPanel {
	
	RegisteredEntityDto entity;
	ListPanel parent; 
	
	public RegisteredEntitySummaryPanel(RegisteredEntityDto entity, ListPanel parent) {
		this.parent = parent;
		this.entity = entity;
		initComponents();
	}
	
	public void initComponents() {
		add(new Label(entity.getTitle()));
		
		add(new Label(entity.getDescription()));
		
		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, new Label("Director: "));
		grid.setWidget(1, 0, new Label("Actor(s): "));
		
		add(grid);
		
		Anchor details = new Anchor("Details");
		details.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				parent.getContentPanel().showDetails(entity);
			}
		});
		add(details);
		
	}
	
	public RegisteredEntityDto getEntity() {
		return entity;
	}

}