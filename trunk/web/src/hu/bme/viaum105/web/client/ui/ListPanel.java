package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.client.service.RegisteredEntityServiceAsync;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListPanel extends VerticalPanel {
	
	List<RegisteredEntityDto> listOfEntities;
	ContentPanel contentPanel;
	
	ListBox orderingBox = new ListBox();
	
	private enum Ordering {
		RATING,
		LIKE,
		TITLE,
	}
		
	RegisteredEntityServiceAsync entityService = 
		(RegisteredEntityServiceAsync) GWT.create(RegisteredEntityService.class);
	
	public ListPanel() {
		listOfEntities = new LinkedList<RegisteredEntityDto>();
		
		((ServiceDefTarget) entityService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "RegisteredEntityService");
		
		orderingBox.addItem("by title", Ordering.TITLE.toString());
		orderingBox.addItem("by rating", Ordering.RATING.toString());
		orderingBox.addItem("by like", Ordering.LIKE.toString());
		
		orderingBox.addChangeHandler( new ChangeHandler() {
			
			public void onChange(ChangeEvent event) {
				orderEntities(orderingBox.getValue(orderingBox.getSelectedIndex()));
				showList();
			}
		});
		
		add(orderingBox);
		
	}
	
	public void searchForEntites() {
		entityService.findAllSeries(new AsyncCallback<List<SeriesDto>>() {
			
			public void onSuccess(List<SeriesDto> result) {
				listOfEntities.clear();
				
				if(result != null) {
					listOfEntities.addAll(result);					
				}
				showList();
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Hiba a sorozatok listázásakor! "+caught.getLocalizedMessage());
			}
		});
	}
	
	public void setContentPanel(ContentPanel parent) {
		this.contentPanel = parent;
	}
	
	public ContentPanel getContentPanel() {
		return contentPanel;
	}
	
	public void setListOfEntities(List<RegisteredEntityDto> listOfEntities) {
		this.listOfEntities = listOfEntities;
	}
	
	private void showList() {
		
		clear();
		
		if(listOfEntities.isEmpty()) {
			add(new Label("No result found."));
		
		} else {
			add(orderingBox);
			
			for(RegisteredEntityDto entity : listOfEntities) {
				RegisteredEntitySummaryPanel subPanel = new RegisteredEntitySummaryPanel(entity, this.getContentPanel());
				subPanel.addStyleName("entitySummary");
				add(subPanel);
			}
		}		
	}
	
	public void orderEntities(String selectedOption) {
		
		if(selectedOption.equals(Ordering.TITLE.toString())) {
			Collections.sort(listOfEntities, new Comparator<RegisteredEntityDto>() {

				public int compare(RegisteredEntityDto o1, RegisteredEntityDto o2) {
					return o1.getTitle().compareTo(o2.getTitle());
				}
				
			});
			
		} else if(selectedOption.equals(Ordering.RATING.toString())) {
			Collections.sort(listOfEntities, new Comparator<RegisteredEntityDto>() {

				public int compare(RegisteredEntityDto o1, RegisteredEntityDto o2) {
					double r1 = (o1.getRate() == null) ? 0.0 : o1.getRate();
					double r2 = (o2.getRate() == null) ? 0.0 : o2.getRate();
					
					if(r1 < r2) {
						return 1;
					} else if(r1 > r2) {
						return -1;
					}
					
					return 0;
				}
				
			});
			
		} else if(selectedOption.equals(Ordering.LIKE.toString())) {
			Collections.sort(listOfEntities, new Comparator<RegisteredEntityDto>() {

				public int compare(RegisteredEntityDto o1, RegisteredEntityDto o2) {
					long l1 = o1.getLikeCount();
					long l2 = o2.getLikeCount();
					
					if(l1 < l2) {
						return 1;
					} else if(l1 > l2) {
						return -1;
					}
					
					return 0;
				}
				
			});
		}
	}

}
