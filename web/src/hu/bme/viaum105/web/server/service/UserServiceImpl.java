package hu.bme.viaum105.web.server.service;

import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.web.client.service.UserService;
import hu.bme.viaum105.web.server.ServiceLocator;
import hu.bme.viaum105.web.server.converter.Converter;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet 
		implements UserService {

	//sorozatszám
	private static final long serialVersionUID = -861141346800060747L;

	public UserDto login(String userName, String password) {
		
		try {
			SeriesPortal services = ServiceLocator.getInstance().getSeriesPortalService();
			
			User u = services.login(userName, password);
			
			return Converter.convert(u);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Regisztráció szolgáltatás.
	 */
	public void register(UserDto user) {
		
		try {
			SeriesPortal services = ServiceLocator.getInstance().getSeriesPortalService();
			
			User u = Converter.convert(user);
			
			services.register(u);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Jelszó változtató szolgáltatás.
	 */
	public void changePassword(String password) {
		
		try {
			SeriesPortal services = ServiceLocator.getInstance().getSeriesPortalService();
						
			//services.;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isUserNameUnique(String userName) {	
		return true;
	}

}
