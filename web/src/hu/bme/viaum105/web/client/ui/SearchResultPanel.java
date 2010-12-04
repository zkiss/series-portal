package hu.bme.viaum105.web.client.ui;

import java.util.List;

import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchResultPanel extends VerticalPanel {

	ContentPanel contentPanel;
	
	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
	
	public void showList(List<RegisteredEntityDto> result) {
		
		clear();
		
		if(result.isEmpty()) {
			add(new Label("No result found."));
		
		} else {
			
			for(RegisteredEntityDto entity : result) {
				RegisteredEntitySummaryPanel subPanel = new RegisteredEntitySummaryPanel(entity, contentPanel);
				subPanel.addStyleName("entitySummary");
				add(subPanel);
			}
		}		
	}
	
}
