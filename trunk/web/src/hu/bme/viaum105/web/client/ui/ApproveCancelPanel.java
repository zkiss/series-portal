package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApproveCancelPanel extends VerticalPanel {
	
	Button approveButton = new Button();
	Button cancelButton = new Button();
	
	HorizontalPanel buttonHolder = new HorizontalPanel();
	
	public ApproveCancelPanel() {
		initComponents();
	}
	
	public void initComponents() {
		
		buttonHolder.add(approveButton);
		buttonHolder.add(cancelButton);
		
		buttonHolder.addStyleName("buttonPanel");
		
		super.add(buttonHolder);
		
	}
	
	@Override public void add(Widget w) {
		clear();
		super.add(w);
		super.add(buttonHolder);
	}
	
	public Button getApproveButton() {
		return approveButton;
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}

}
