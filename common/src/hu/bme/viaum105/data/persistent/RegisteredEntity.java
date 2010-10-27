package hu.bme.viaum105.data.persistent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A Sorozat és az Epizód közös ősosztálya
 * 
 * @author Zoltan Kiss
 */
@Entity
@Table(name = "REGISTERED_ENTITY")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RegisteredEntity extends EntityBase {

    private static final long serialVersionUID = 5169037825980335358L;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "REGISTERED_ENTITY_LABEL")
    private Set<Label> labels = new HashSet<Label>();

    @OneToMany(mappedBy = "registeredEntity", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(mappedBy = "registeredEntity", fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<Like>();

    @OneToMany(mappedBy = "registeredEntity", fetch = FetchType.LAZY)
    private Set<Rate> rates = new HashSet<Rate>();

    public Set<Comment> getComments() {
	return this.comments;
    }

    public String getDescription() {
	return this.description;
    }

    public Set<Label> getLabels() {
	return this.labels;
    }

    public Set<Like> getLikes() {
	return this.likes;
    }

    public Set<Rate> getRates() {
	return this.rates;
    }

    public String getTitle() {
	return this.title;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
