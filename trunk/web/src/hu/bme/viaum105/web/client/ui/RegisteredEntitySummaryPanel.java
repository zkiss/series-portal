package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
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
		
		add(new Label("Like: "+entity.getLikeCount()));
		if(entity.getRate() == null) {
			add(new Label("Rate: unrated"));
		} else {
			add(new Label("Rate: "+entity.getRate()));
		}
		
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