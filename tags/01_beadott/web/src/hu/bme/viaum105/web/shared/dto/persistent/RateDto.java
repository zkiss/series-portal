package hu.bme.viaum105.web.shared.dto.persistent;

public class RateDto extends EntityBaseDto {

    private static final long serialVersionUID = 7012992080457568073L;

    private UserDto user;

    private RegisteredEntityDto registeredEntity;

    private int rate;

    public int getRate() {
	return this.rate;
    }

    public RegisteredEntityDto getRegisteredEntity() {
	return this.registeredEntity;
    }

    public UserDto getUser() {
	return this.user;
    }

    public void setRate(int rate) {
	this.rate = rate;
    }

    public void setRegisteredEntity(RegisteredEntityDto registeredEntity) {
	this.registeredEntity = registeredEntity;
    }

    public void setUser(UserDto user) {
	this.user = user;
    }

    @Override
    public String toString() {
	return this.toString("RateDto") + "[" + this.rate + "]";
    }

}
