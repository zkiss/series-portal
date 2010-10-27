package hu.bme.viaum105.data.persistent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import hu.bme.viaum105.data.nonpersistent.Role;

@Entity
@Table(name = "USR")
public class User extends EntityBase {

    private static final long serialVersionUID = -4160487490377817172L;

    @Column(name = "LOGIN_NAME", nullable = false, length = 25, unique = true)
    private String loginName;

    @Column(name = "DISPLAY_NAME", nullable = false, length = 100)
    private String displayName;

    @Column(name = "PASSWORD", nullable = false, length = 30)
    private String password;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "ROLE", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<Like>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Rate> rates = new HashSet<Rate>();

    public Set<Comment> getComments() {
	return this.comments;
    }

    public String getDisplayName() {
	return this.displayName;
    }

    public String getEmail() {
	return this.email;
    }

    public Set<Like> getLikes() {
	return this.likes;
    }

    public String getLoginName() {
	return this.loginName;
    }

    public String getPassword() {
	return this.password;
    }

    public Set<Rate> getRates() {
	return this.rates;
    }

    public Role getRole() {
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

    public void setRole(Role role) {
	this.role = role;
    }

    @Override
    public String toString() {
	return super.toString() + "[name:" + this.loginName + "]";
    }

}
