package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.service.UserService;
import hu.bme.viaum105.web.client.service.UserServiceAsync;
import hu.bme.viaum105.web.shared.dto.nonpersistent.RoleDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class UserPanel extends DeckPanel {
	
	HorizontalPanel beforeLoginPanel = new HorizontalPanel();
	HorizontalPanel afterLoginPanel = new HorizontalPanel();
	
	WebMainPanel mainPanel;
	
	Label userName = new Label();
	
	UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	public UserPanel() {
		((ServiceDefTarget) userService).setServiceEntryPoint( 
				GWT.getModuleBaseURL() + "UserService");
		
		initComponents();
	}
	
	private void initComponents() {
		
		Anchor loginAnchor = new Anchor("Login");
		Anchor registerAnchor = new Anchor("Register");
		
		beforeLoginPanel.add(loginAnchor);
		beforeLoginPanel.add(registerAnchor);
		
		Anchor logoutAnchor = new Anchor("Logout");
		afterLoginPanel.add(userName);
		afterLoginPanel.add(logoutAnchor);
		
		add(beforeLoginPanel);
		add(afterLoginPanel);
		showWidget(0);
		
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
		
		logoutAnchor.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				mainPanel.setUser(null);
				mainPanel.updateVisibility();
				showWidget(0);
			}
		});
		
	}
	
	private void showLogin() {
		final LoginPanel loginPanel = new LoginPanel();
		final ApprovableCancelDialogBox dialogBox = new ApprovableCancelDialogBox();
		
		dialogBox.getApproveButton().setText("Login");
		dialogBox.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				String loginName = loginPanel.getLoginName();
				String password = loginPanel.getPassword();
				
				if(loginName.isEmpty() || password.isEmpty()) {
					loginPanel.setErrorMessage("The login and password field are required");
				
				} else {
					userService.login(loginName, password, new AsyncCallback<UserDto>() {
						
						public void onSuccess(UserDto result) {
							if(result == null) {
								loginPanel.setErrorMessage("Bad login name or password");
							} else {
								userName.setText(result.getLoginName());
								showWidget(1);
								
								dialogBox.hide();
								
								mainPanel.setUser(result);
								mainPanel.updateVisibility();
							}
						}
						
						public void onFailure(Throwable caught) {
							Window.alert("hiba: "+caught.getLocalizedMessage());
						}
					});
				}
				
				
				
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
		final ApprovableCancelDialogBox dialogBox = new ApprovableCancelDialogBox();
		
		dialogBox.getApproveButton().setText("Register");
		dialogBox.getApproveButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				if(registerPanel.isValid()) {
				
					//TODO
					userService.isUserNameUnique(registerPanel.getLoginName(), new AsyncCallback<Boolean>() {
						
						public void onSuccess(Boolean result) {
							if(!result) {
							
								registerPanel.setErrorMessage("Login name is already taken!");

							} else {
								UserDto user = new UserDto();
								user.setDisplayName(registerPanel.getLoginName());
								user.setLoginName(registerPanel.getLoginName());
								user.setPassword(registerPanel.getPassword());
								user.setEmail(registerPanel.getEmail());
								user.setRole(RoleDto.USER);
								userService.register(user, new AsyncCallback<Void>() {

									public void onFailure(Throwable caught) {
										Window.alert(caught.getLocalizedMessage());
									}

									public void onSuccess(Void result) {
										dialogBox.hide();
									}
								});
							}
							
							
						}
						
						public void onFailure(Throwable caught) {
							Window.alert(caught.getLocalizedMessage());
						}
					});
					
				}
				
			}
		});
		
		dialogBox.setText("Register");
		dialogBox.setAnimationEnabled(true);
		dialogBox.add(registerPanel);
		
		dialogBox.center();
		dialogBox.show();
		
	}
	
	public void setMainPanel(WebMainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

}
