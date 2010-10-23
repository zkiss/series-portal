package hu.bme.viaum105.web.client;

import hu.bme.viaum105.web.client.ui.WebMainPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Web implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		RootPanel.get("mainPanel").add(new WebMainPanel());
		
	}
}
