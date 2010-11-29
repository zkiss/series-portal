package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	
	void login(String userName, String password, AsyncCallback<UserDto> callback) 
		throws IllegalArgumentException;
	
	void register(UserDto user, AsyncCallback<Void> callback);
	
	void isUserNameTaken(String userName, AsyncCallback<Boolean> callback);
	
}
