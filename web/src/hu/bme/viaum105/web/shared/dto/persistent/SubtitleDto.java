package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.Date;

public class SubtitleDto extends EntityBaseDto {

    private static final long serialVersionUID = 2591448942227135745L;

    private EpisodeDto episode;

    private String fileName;

    private Date addedAt;

    private SubtitleDataDto subtitleData;

    public Date getAddedAt() {
	return this.addedAt;
    }

    public EpisodeDto getEpisode() {
	return this.episode;
    }

    public String getFileName() {
	return this.fileName;
    }

    public SubtitleDataDto getSubtitleData() {
	return this.subtitleData;
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

    public void setSubtitleData(SubtitleDataDto subtitleData) {
	this.subtitleData = subtitleData;
    }

    @Override
    public String toString() {
	return this.toString("SubtitleDto") + "[" + this.fileName + "]";
    }

}
