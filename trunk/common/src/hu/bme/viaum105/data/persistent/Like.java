package hu.bme.viaum105.data.persistent;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACTION_LIKE")
public class Like extends EntityBase {

    private static final long serialVersionUID = 1115072136008037255L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "REGISTERED_ENTITY_ID", nullable = false)
    private RegisteredEntity registeredEntity;

    public RegisteredEntity getRegisteredEntity() {
	return this.registeredEntity;
    }

    public User getUser() {
	return this.user;
    }

    public void setRegisteredEntity(RegisteredEntity registeredEntity) {
	this.registeredEntity = registeredEntity;
    }

    public void setUser(User user) {
	this.user = user;
    }

}
