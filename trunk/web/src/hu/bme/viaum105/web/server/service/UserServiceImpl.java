package hu.bme.viaum105.web.server.service;

import hu.bme.viaum105.web.client.service.UserService;
import hu.bme.viaum105.web.shared.dto.nonpersistent.RoleDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet 
		implements UserService {

	//sorozatszám
	private static final long serialVersionUID = -861141346800060747L;

	public UserDto login(String userName, String password)
			throws IllegalArgumentException {
		
		UserDto user = null;
		
		if(userName.equals("zoli") && password.equals("123")) {
			user = new UserDto();
			user.setLoginName("zoli");
			user.setRole(RoleDto.ADMIN);
		}
		
		return user;
	}
	
	public void register(UserDto user) {
		System.out.println("Regisztráció kész");
	}
	
	public boolean isUserNameTaken(String userName) {
		
		if("zoli".equals(userName)) {
			return true;
		}
		return false;
	}

}
