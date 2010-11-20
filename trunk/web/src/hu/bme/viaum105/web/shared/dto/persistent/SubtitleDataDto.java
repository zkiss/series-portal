package hu.bme.viaum105.web.shared.dto.persistent;


public class SubtitleDataDto extends EntityBaseDto {

    private static final long serialVersionUID = 3745128135325385880L;

    private SubtitleDto subtitle;

    private byte[] fileData;

    public byte[] getFileData() {
	return this.fileData;
    }

    public SubtitleDto getSubtitle() {
	return this.subtitle;
    }

    public void setFileData(byte[] fileData) {
	this.fileData = fileData;
    }

    public void setSubtitle(SubtitleDto subtitle) {
	this.subtitle = subtitle;
    }

}
