package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.HashSet;
import java.util.Set;

public class ActorDto extends EntityBaseDto {

    private static final long serialVersionUID = 4189716793527253752L;

    private String name;

    private Set<RegisteredEntityDto> registeredEntities = new HashSet<RegisteredEntityDto>();

    public String getName() {
	return this.name;
    }

    public Set<RegisteredEntityDto> getRegisteredEntities() {
	return this.registeredEntities;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return this.toString("ActorDto") + "[name:" + this.name + "]";
    }

}
