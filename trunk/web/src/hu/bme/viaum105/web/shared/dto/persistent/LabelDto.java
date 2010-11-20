package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.HashSet;
import java.util.Set;

public class LabelDto extends EntityBaseDto {

    private static final long serialVersionUID = -9128158276328346394L;

    private String label;

    private Set<RegisteredEntityDto> registeredEntities = new HashSet<RegisteredEntityDto>();

    public String getLabel() {
	return this.label;
    }

    public Set<RegisteredEntityDto> getRegisteredEntities() {
	return this.registeredEntities;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return this.toString("LabelDto") + "[" + this.label + "]";
    }

}
