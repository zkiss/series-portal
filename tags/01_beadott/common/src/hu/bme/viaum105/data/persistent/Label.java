package hu.bme.viaum105.data.persistent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LABEL")
public class Label extends EntityBase {

    private static final long serialVersionUID = -9128158276328346394L;

    @Column(name = "LABEL", nullable = false, unique = true)
    private String label;

    @ManyToMany(mappedBy = "labels")
    private Set<RegisteredEntity> registeredEntities = new HashSet<RegisteredEntity>();

    public String getLabel() {
	return this.label;
    }

    public Set<RegisteredEntity> getRegisteredEntities() {
	return this.registeredEntities;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return super.toString() + "[" + this.label + "]";
    }

}
