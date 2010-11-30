package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.image.WebImageBundle;
import hu.bme.viaum105.web.shared.dto.nonpersistent.RoleDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Az alkamazást összefogó főpanel.
 * 
 * @author zoli
 */
public class WebMainPanel extends FlowPanel {
	
	UserPanel userPanel = GWT.create(UserPanel.class);
	ContentPanel contentPanel = GWT.create(ContentPanel.class);
	SearchPanel searchPanel = GWT.create(SearchPanel.class);
	
	MenuBar menu = new MenuBar();
	MenuItem browseItem = createMenuItem("browse", WebImageBundle.INSTANCE.tv());
	MenuItem createSerieItem = createMenuItem("new serie", WebImageBundle.INSTANCE.add());
	MenuItem profileItem = createMenuItem("profile", WebImageBundle.INSTANCE.profile());
	MenuItem commentItem = createMenuItem("comments", WebImageBundle.INSTANCE.profile());
	
	UserDto user;
	
	//sorozatszám
	private static final long serialVersionUID = -7857414717753358256L;
	
	public WebMainPanel() {
		
		//teszt
		user = new UserDto();
		user.setLoginName("zoli");
		user.setRole(RoleDto.ADMIN);
		
		initComponents();
		
		
	}
	
	/**
	 * Komponensek inicializálása.
	 */
	public void initComponents() {
		menu.addItem(browseItem);
		menu.addItem(createSerieItem);
		menu.addItem(profileItem);
		menu.addItem(commentItem);
		
		browseItem.setCommand(new Command() {
			
			public void execute() {
				contentPanel.showBrowseSeries();
			}
		});

		createSerieItem.setCommand(new Command() {
			
			public void execute() {
				contentPanel.showCreateSerie();
			}
		});
		
		profileItem.setCommand(new Command() {
			
			public void execute() {
				contentPanel.showProfil();
			}
		});
		
		commentItem.setCommand(new Command() {
			
			public void execute() {
				//TODO implementálni
			}
		});
		
		menu.setStyleName("menuBar");
		
		userPanel.setMainPanel(this);
		
		VerticalPanel side = new VerticalPanel();
		side.add(userPanel);
		side.add(searchPanel);
		
		side.addStyleName("deck");
		
		add(side);
		add(menu);
		add(contentPanel);
		
		updateVisibility();
	}
	
	public void setUser(UserDto user) {
		this.user = user;
	}
	
	public void updateVisibility() {
	
		//felhasználó nincs még bejelentkezve, vagy kilépett
		if(user == null) {
			createSerieItem.setVisible(false);
			profileItem.setVisible(false);
			commentItem.setVisible(false);
			
		} else if(user.getRole().equals(RoleDto.USER)) {
			createSerieItem.setVisible(true);
			profileItem.setVisible(true);
			commentItem.setVisible(false);
			
		} else if(user.getRole().equals(RoleDto.ADMIN)) {
			createSerieItem.setVisible(true);
			profileItem.setVisible(true);
			commentItem.setVisible(true);
			
		}
		
		
	}
	
	
	
	public MenuItem createMenuItem(String menuLabel,
			AbstractImagePrototype menuImage) {
			    Command nullCommand = null;
			    MenuItem menuItem = new MenuItem(menuImage.getHTML() + "&nbsp;"+
			menuLabel, true, nullCommand);
			    menuItem.addStyleName("menuItem");
			    return menuItem;
	}

}
