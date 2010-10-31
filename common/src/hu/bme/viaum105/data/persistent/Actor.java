package hu.bme.viaum105.data.persistent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ACTOR")
public class Actor extends EntityBase {

    private static final long serialVersionUID = 4189716793527253752L;

    @Column(name = "FULL_NAME", length = 100, unique = true)
    private String name;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private Set<Episode> episodes = new HashSet<Episode>();

    public Set<Episode> getEpisodes() {
	return this.episodes;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return super.toString() + "[name:" + this.name + "]";
    }

}
