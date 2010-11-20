package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class CommentDto extends EntityBaseDto {

    private static final long serialVersionUID = -7488345130318166411L;

    private UserDto user;

    private RegisteredEntityDto registeredEntity;

    private String comment;

    private boolean isApproved;

    private Date date;

    public String getComment() {
	return this.comment;
    }

    public Date getDate() {
	return this.date;
    }

    public RegisteredEntityDto getRegisteredEntity() {
	return this.registeredEntity;
    }

    public UserDto getUser() {
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

    public void setDate(Date date) {
	this.date = date;
    }

    public void setRegisteredEntity(RegisteredEntityDto registeredEntity) {
	this.registeredEntity = registeredEntity;
    }

    public void setUser(UserDto user) {
	this.user = user;
    }

    @Override
    public String toString() {
	return this.toString("CommentDto") + "[" + //
		(this.date == null ? "---" : DateTimeFormat.getFullDateFormat().format(this.date)) + //
		" " + this.user + //
		": " + this.comment + "]";
    }

}
