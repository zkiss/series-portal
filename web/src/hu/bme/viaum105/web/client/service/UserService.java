package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.user.client.rpc.RemoteService;

public interface UserService extends RemoteService{
	
	UserDto login(String userName, String password);
	
	void register(UserDto user);
	
	boolean isUserNameUnique(String userName);
	
	public void changePassword(long id, String password);
	
}