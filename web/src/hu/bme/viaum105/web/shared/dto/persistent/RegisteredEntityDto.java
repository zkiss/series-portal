package hu.bme.viaum105.web.shared.dto.persistent;

import java.util.HashSet;
import java.util.Set;

/**
 * A Sorozat és az Epizód közös ősosztálya
 * 
 * @author Zoltan Kiss
 */
public abstract class RegisteredEntityDto extends EntityBaseDto {

    private static final long serialVersionUID = 5169037825980335358L;

    private String title;

    private String description;

    private Set<LabelDto> labels = new HashSet<LabelDto>();

    private Set<CommentDto> comments = new HashSet<CommentDto>();

    private Set<LikeDto> likes = new HashSet<LikeDto>();

    private Set<RateDto> rates = new HashSet<RateDto>();

    public Set<CommentDto> getComments() {
	return this.comments;
    }

    public String getDescription() {
	return this.description;
    }

    public Set<LabelDto> getLabels() {
	return this.labels;
    }

    public Set<LikeDto> getLikes() {
	return this.likes;
    }

    public Set<RateDto> getRates() {
	return this.rates;
    }

    public String getTitle() {
	return this.title;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
