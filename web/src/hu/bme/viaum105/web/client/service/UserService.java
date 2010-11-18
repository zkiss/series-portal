package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.user.client.rpc.RemoteService;

public interface UserService extends RemoteService{
	
	UserDto login(String userName, String password) throws IllegalArgumentException;
	
	void register(UserDto user);
	
	boolean isUserNameTaken(String userName);

}