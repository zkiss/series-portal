package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.client.image.WebImageBundle;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * Az alkamazást összefogó főpanel.
 * 
 * @author zoli
 */
public class WebMainPanel extends FlowPanel {
	
	UserPanel userPanel = new UserPanel();
	
	MenuBar menu = new MenuBar();
	MenuItem browseItem = createMenuItem("browse", WebImageBundle.INSTANCE.tv());
	MenuItem createSerieItem = createMenuItem("new serie", WebImageBundle.INSTANCE.add());
	MenuItem profileItem = createMenuItem("profile", WebImageBundle.INSTANCE.profile());
	
	ContentPanel contentPanel = new ContentPanel();
	
	//sorozatszám
	private static final long serialVersionUID = -7857414717753358256L;

	public WebMainPanel() {
		initComponents();
	}
	
	/**
	 * Komponensek inicializálása.
	 */
	public void initComponents() {
		menu.addItem(browseItem);
		menu.addItem(createSerieItem);
		menu.addItem(profileItem);
		
		menu.setStyleName("menuBar");
		
		add(userPanel);
		add(menu);
		add(contentPanel);
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
