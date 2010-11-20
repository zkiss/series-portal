package hu.bme.viaum105.data.persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SUBTITLE_DATA")
public class SubtitleData extends EntityBase {

    private static final long serialVersionUID = 3745128135325385880L;

    @OneToOne(optional = false, mappedBy = "subtitleData")
    private Subtitle subtitle;

    @Lob
    @Column(name = "FILE_DATA", nullable = false)
    private byte[] fileData;

    public byte[] getFileData() {
	return this.fileData;
    }

    public Subtitle getSubtitle() {
	return this.subtitle;
    }

    public void setFileData(byte[] fileData) {
	this.fileData = fileData;
    }

    public void setSubtitle(Subtitle subtitle) {
	this.subtitle = subtitle;
    }

}
