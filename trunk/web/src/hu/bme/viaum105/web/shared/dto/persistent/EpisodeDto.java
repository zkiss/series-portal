package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EpisodeDto extends RegisteredEntityDto {

    private static final long serialVersionUID = 3910682836742887123L;

    private SeriesDto series;

    private int seasonNumber;

    private int episodeNumber;

    private Date airDate;

    private Set<ActorDto> actors = new HashSet<ActorDto>();

    private Set<SubtitleDto> subtitles = new HashSet<SubtitleDto>();

    public ActorDto addActor(String name) {
	ActorDto a = new ActorDto();
	a.setName(name);
	this.actors.add(a);
	return a;
    }

    public Set<ActorDto> getActors() {
	return this.actors;
    }

    public Date getAirDate() {
	return this.airDate;
    }

    public int getEpisodeNumber() {
	return this.episodeNumber;
    }

    public int getSeasonNumber() {
	return this.seasonNumber;
    }

    public SeriesDto getSeries() {
	return this.series;
    }

    public Set<SubtitleDto> getSubtitles() {
	return this.subtitles;
    }

    public void setAirDate(Date airDate) {
	this.airDate = airDate;
    }

    public void setEpisodeNumber(int episodeNumber) {
	this.episodeNumber = episodeNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
	this.seasonNumber = seasonNumber;
    }

    public void setSeries(SeriesDto series) {
	this.series = series;
    }

    @Override
    public String toString() {
	return super.toString() + "[" + //
		this.series.getTitle() + //
		" s" + this.seasonNumber + //
		"e" + this.episodeNumber + //
		" - " + this.getTitle() + "]";
    }

}
