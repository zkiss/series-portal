package hu.bme.viaum105.data.persistent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SERIES")
public class Series extends RegisteredEntity {

    private static final long serialVersionUID = 3916150979750806399L;

    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER)
    private Set<Episode> episodes = new HashSet<Episode>();

    @Column(name = "IMDB_URL", length = 255)
    private String imdbUrl;

    @Column(name = "DIRECTOR", length = 100, nullable = false)
    private String director;

    public String getDirector() {
	return this.director;
    }

    public Set<Episode> getEpisodes() {
	return this.episodes;
    }

    public String getImdbUrl() {
	return this.imdbUrl;
    }

    public void setDirector(String director) {
	this.director = director;
    }

    public void setImdbUrl(String imdbUrl) {
	this.imdbUrl = imdbUrl;
    }

    @Override
    public String toString() {
	return super.toString() + "[" + this.getTitle() + "]";
    }

}
