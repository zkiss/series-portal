package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.Date;

public class SubtitleDto extends EntityBaseDto {

    private static final long serialVersionUID = 2591448942227135745L;

    private EpisodeDto episode;

    private String fileName;

    private Date addedAt;

    public Date getAddedAt() {
	return this.addedAt;
    }

    public EpisodeDto getEpisode() {
	return this.episode;
    }

    public String getFileName() {
	return this.fileName;
    }

    public void setAddedAt(Date addedAt) {
	this.addedAt = addedAt;
    }

    public void setEpisode(EpisodeDto episode) {
	this.episode = episode;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    @Override
    public String toString() {
	return super.toString() + "[" + this.fileName + "]";
    }

}
