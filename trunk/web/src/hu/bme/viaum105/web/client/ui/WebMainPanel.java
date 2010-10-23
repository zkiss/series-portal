package hu.bme.viaum105.web.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Az alkamazást összefogó főpanel.
 * 
 * @author zoli
 */
public class WebMainPanel extends HorizontalPanel {
	
	private Anchor loginAnchor = new Anchor();
	private Anchor registerAnchor = new Anchor();
	
	//sorozatszám
	private static final long serialVersionUID = -7857414717753358256L;

	public WebMainPanel() {
		initComponents();
	}
	
	/**
	 * Komponensek inicializálása.
	 */
	public void initComponents() {
		loginAnchor.setText("Login");
		registerAnchor.setText("Register");
		
		add(loginAnchor);
		add(registerAnchor);
		
		
		loginAnchor.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				showLogin();
			}
		});
		
		registerAnchor.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				showRegister();
			}
		});
	}
	
	private void showLogin() {
		final LoginPanel loginPanel = new LoginPanel();
		final ApprovableDialogBox dialogBox = new ApprovableDialogBox();
		
		dialogBox.getApproveButton().setText("Login");
		dialogBox.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				String loginName = loginPanel.getLoginName();
				String password = loginPanel.getPassword();
				
				if(loginName.isEmpty() || password.isEmpty()) {
					loginPanel.setErrorMessage("The login and password field are required");
				}
				
				//TODO szolgáltatáshívás
			}
		});
		
		dialogBox.setText("Login");
		dialogBox.setAnimationEnabled(true);
		dialogBox.add(loginPanel);
		
		dialogBox.center();
		dialogBox.show();
	}
	
	/**
	 * Feldobja a regisztrációs dialógus ablakot.
	 */
	private void showRegister() {
		final RegisterPanel registerPanel = new RegisterPanel();
		final ApprovableDialogBox dialogBox = new ApprovableDialogBox();
		
		dialogBox.getApproveButton().setText("Register");
		dialogBox.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				//TODO implementálni
			}
		});
		
		dialogBox.setText("Register");
		dialogBox.setAnimationEnabled(true);
		dialogBox.add(registerPanel);
		
		dialogBox.center();
		dialogBox.show();
		
	}

}
