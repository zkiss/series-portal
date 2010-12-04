package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	
	void login(String userName, String password, AsyncCallback<UserDto> callback);
	
	void register(UserDto user, AsyncCallback<Void> callback);
	
	void isUserNameUnique(String userName, AsyncCallback<Boolean> callback);
	
	void changePassword(long id, String password, AsyncCallback<Void> callback);
	
}
