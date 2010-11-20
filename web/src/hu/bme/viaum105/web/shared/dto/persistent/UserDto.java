package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.HashSet;
import java.util.Set;

import hu.bme.viaum105.web.shared.dto.nonpersistent.RoleDto;

public class UserDto extends EntityBaseDto {

    private static final long serialVersionUID = -4160487490377817172L;

    private String loginName;

    private String displayName;

    private String password;

    private String email;

    private RoleDto role;

    private Set<CommentDto> comments = new HashSet<CommentDto>();

    private Set<LikeDto> likes = new HashSet<LikeDto>();

    private Set<RateDto> rates = new HashSet<RateDto>();

    public Set<CommentDto> getComments() {
	return this.comments;
    }

    public String getDisplayName() {
	return this.displayName;
    }

    public String getEmail() {
	return this.email;
    }

    public Set<LikeDto> getLikes() {
	return this.likes;
    }

    public String getLoginName() {
	return this.loginName;
    }

    public String getPassword() {
	return this.password;
    }

    public Set<RateDto> getRates() {
	return this.rates;
    }

    public RoleDto getRole() {
	return this.role;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setLoginName(String loginName) {
	this.loginName = loginName;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void setRole(RoleDto role) {
	this.role = role;
    }

    @Override
    public String toString() {
	return this.toString("UserDto") + "[" + this.loginName + "]";
    }

}
