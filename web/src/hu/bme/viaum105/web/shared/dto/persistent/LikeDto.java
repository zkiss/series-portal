package hu.bme.viaum105.web.shared.dto.persistent;


public class LikeDto extends EntityBaseDto {

    private static final long serialVersionUID = 1115072136008037255L;

    private UserDto user;

    private RegisteredEntityDto registeredEntity;

    public RegisteredEntityDto getRegisteredEntity() {
	return this.registeredEntity;
    }

    public UserDto getUser() {
	return this.user;
    }

    public void setRegisteredEntity(RegisteredEntityDto registeredEntity) {
	this.registeredEntity = registeredEntity;
    }

    public void setUser(UserDto user) {
	this.user = user;
    }

}
