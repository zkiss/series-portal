package hu.bme.viaum105.data.persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACTION_COMMENT")
public class Comment extends EntityBase {

    private static final long serialVersionUID = -7488345130318166411L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "REGISTERED_ENTITY_ID", nullable = false)
    private RegisteredEntity registeredEntity;

    @Column(name = "USR_COMMENT", nullable = false)
    @Lob
    private String comment;

    @Column(name = "IS_APPROVED")
    private boolean isApproved;

    public String getComment() {
	return this.comment;
    }

    public RegisteredEntity getRegisteredEntity() {
	return this.registeredEntity;
    }

    public User getUser() {
	return this.user;
    }

    public boolean isApproved() {
	return this.isApproved;
    }

    public void setApproved(boolean isApproved) {
	this.isApproved = isApproved;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public void setRegisteredEntity(RegisteredEntity registeredEntity) {
	this.registeredEntity = registeredEntity;
    }

    public void setUser(User user) {
	this.user = user;
    }

}
