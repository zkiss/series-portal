package hu.bme.viaum105.web.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;


@SuppressWarnings("deprecation")
public interface WebImageBundle extends ImageBundle {
	
	public static final WebImageBundle INSTANCE =  GWT.create(WebImageBundle.class);

	@Resource("profile.PNG")
    AbstractImagePrototype profile();
	
	@Resource("add.PNG")
	AbstractImagePrototype add();
	
	@Resource("tv.PNG")
	AbstractImagePrototype tv();
}
