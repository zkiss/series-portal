package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EpisodeDto extends RegisteredEntityDto {

    private static final long serialVersionUID = 3910682836742887123L;

    private SeriesDto series;

    private int seasonNumber;

    private int episodeNumber;

    private int lengthMinutes;

    private Date airDate;

    private Set<SubtitleDto> subtitles = new HashSet<SubtitleDto>();

    public Date getAirDate() {
	return this.airDate;
    }

    public int getEpisodeNumber() {
	return this.episodeNumber;
    }

    public int getLengthMinutes() {
	return this.lengthMinutes;
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

    public void setLengthMinutes(int lengthMinutes) {
	this.lengthMinutes = lengthMinutes;
    }

    public void setSeasonNumber(int seasonNumber) {
	this.seasonNumber = seasonNumber;
    }

    public void setSeries(SeriesDto series) {
	this.series = series;
    }

    @Override
    public String toString() {
	return this.toString("EpisodeDto") + //
		"[s" + this.seasonNumber + //
		"e" + this.episodeNumber + //
		" - " + this.getTitle() + "]";
    }

}
