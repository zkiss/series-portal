package hu.bme.viaum105.data.persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACTION_RATE")
public class Rate extends EntityBase {

    private static final long serialVersionUID = 7012992080457568073L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "REGISTERED_ENTITY_ID", nullable = false)
    private RegisteredEntity registeredEntity;

    @Column(name = "RATE", precision = 2, nullable = false)
    private int rate;

    public int getRate() {
	return this.rate;
    }

    public RegisteredEntity getRegisteredEntity() {
	return this.registeredEntity;
    }

    public User getUser() {
	return this.user;
    }

    public void setRate(int rate) {
	this.rate = rate;
    }

    public void setRegisteredEntity(RegisteredEntity registeredEntity) {
	this.registeredEntity = registeredEntity;
    }

    public void setUser(User user) {
	this.user = user;
    }

}
