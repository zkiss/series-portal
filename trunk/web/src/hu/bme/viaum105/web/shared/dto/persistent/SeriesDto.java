package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.HashSet;
import java.util.Set;

public class SeriesDto extends RegisteredEntityDto {

    private static final long serialVersionUID = 3916150979750806399L;

    private Set<EpisodeDto> episodes = new HashSet<EpisodeDto>();

    private String imdbUrl;

    private String director;

    public String getDirector() {
	return this.director;
    }

    public Set<EpisodeDto> getEpisodes() {
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
	return this.toString("SeriesDto") + "[" + this.getTitle() + "]";
    }

}
