package hu.bme.viaum105.web.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApprovablePanel extends VerticalPanel {
	
	Button approveButton = new Button();
	
	HorizontalPanel buttonHolder = new HorizontalPanel();
	
	public ApprovablePanel() {
		initComponents();
	}
	
	public void initComponents() {
		
		buttonHolder.add(approveButton);
		
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
}
