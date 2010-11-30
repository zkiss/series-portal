package hu.bme.viaum105.data.persistent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "REGISTERED_ENTITY_ACTOR", //
    joinColumns = @JoinColumn(name = "ACTOR_ID"), //
    inverseJoinColumns = @JoinColumn(name = "REGISTERED_ENTITY_ID"))
    private Set<Actor> actors = new HashSet<Actor>();

    @OneToMany(mappedBy = "registeredEntity", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(mappedBy = "registeredEntity", fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<Like>();

    @OneToMany(mappedBy = "registeredEntity", fetch = FetchType.LAZY)
    private Set<Rate> rates = new HashSet<Rate>();

    @Transient
    private long likeCount;

    @Transient
    private Double rate;

    public Actor addActor(String name) {
	Actor a = new Actor();
	a.setName(name);
	this.actors.add(a);
	return a;
    }

    public Label addLabel(String label) {
	Label l = new Label();
	l.setLabel(label);
	this.labels.add(l);
	return l;
    }

    public Set<Actor> getActors() {
	return this.actors;
    }

    public Set<Comment> getComments() {
	return this.comments;
    }

    public String getDescription() {
	return this.description;
    }

    public Set<Label> getLabels() {
	return this.labels;
    }

    public long getLikeCount() {
	return this.likeCount;
    }

    public Set<Like> getLikes() {
	return this.likes;
    }

    public Double getRate() {
	return this.rate;
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

    public void setLikeCount(long likeCount) {
	this.likeCount = likeCount;
    }

    public void setRate(Double rate) {
	this.rate = rate;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
