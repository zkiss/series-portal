package hu.bme.viaum105.data.persistent;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EPISODE")
public class Episode extends RegisteredEntity {

    private static final long serialVersionUID = 3910682836742887123L;

    @ManyToOne
    @JoinColumn(name = "SERIES_ID", nullable = false)
    private Series series;

    @Column(name = "SEASON_NUMBER", nullable = false)
    private int seasonNumber;

    @Column(name = "EPISODE_NUMBER", nullable = false)
    private int episodeNumber;

    @Column(name = "AIR_DATE")
    @Temporal(TemporalType.DATE)
    private Date airDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "EPISODE_ACTOR")
    private Set<Actor> actors = new HashSet<Actor>();

    @OneToMany(mappedBy = "episode")
    private Set<Subtitle> subtitles = new HashSet<Subtitle>();

    public Set<Actor> getActors() {
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

    public Series getSeries() {
	return this.series;
    }

    public Set<Subtitle> getSubtitles() {
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

    public void setSeries(Series series) {
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
