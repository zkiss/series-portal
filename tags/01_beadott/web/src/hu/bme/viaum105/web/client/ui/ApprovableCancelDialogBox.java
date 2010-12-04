package hu.bme.viaum105.web.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Dialógusablak egy APPROVE és egy CANCEL jellegű gombbal.
 * 
 * @author zoli
 */
public class ApprovableCancelDialogBox extends DialogBox {
	
	Button approveButton = new Button();
	Button cancelButton = new Button();
	
	HorizontalPanel buttonHolder = new HorizontalPanel();
	VerticalPanel dialogPanel = new VerticalPanel();
	
	public ApprovableCancelDialogBox() {
		initComponents();
	}
	
	private void initComponents() {
		cancelButton.setText("Cancel");
		
		buttonHolder.add(approveButton);
		buttonHolder.add(cancelButton);
		
		cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		super.add(dialogPanel);
	}
	
	@Override public void add(Widget w) {
		dialogPanel.clear();
		dialogPanel.add(w);
		dialogPanel.add(buttonHolder);
	}
	
	public Button getApproveButton() {
		return approveButton;
	}
	
}
