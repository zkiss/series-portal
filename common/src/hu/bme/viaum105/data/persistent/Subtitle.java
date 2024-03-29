package hu.bme.viaum105.data.persistent;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SUBTITLE")
public class Subtitle extends EntityBase {

    private static final long serialVersionUID = 2591448942227135745L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EPISODE_ID", nullable = false)
    private Episode episode;

    @Column(name = "FILE", nullable = false, length = 255, unique = true)
    private String fileName;

    @Column(name = "ADDED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedAt;

    /*
     * Hibernate nem támogat lazy loadingot blob-okra
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DATA_ID", nullable = false, unique = true)
    private SubtitleData subtitleData;

    public Date getAddedAt() {
	return this.addedAt;
    }

    public Episode getEpisode() {
	return this.episode;
    }

    public String getFileName() {
	return this.fileName;
    }

    public SubtitleData getSubtitleData() {
	return this.subtitleData;
    }

    public void setAddedAt(Date addedAt) {
	this.addedAt = addedAt;
    }

    public void setEpisode(Episode episode) {
	this.episode = episode;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public void setSubtitleData(SubtitleData subtitleData) {
	this.subtitleData = subtitleData;
    }

    @Override
    public String toString() {
	return super.toString() + "[" + this.fileName + "]";
    }

}
