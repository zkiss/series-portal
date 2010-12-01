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

    private Set<ActorDto> actors = new HashSet<ActorDto>();

    private Set<CommentDto> comments = new HashSet<CommentDto>();

    private Set<LikeDto> likes = new HashSet<LikeDto>();

    private Set<RateDto> rates = new HashSet<RateDto>();

    private long likeCount;

    private double rate;

    public ActorDto addActor(String name) {
	ActorDto a = new ActorDto();
	a.setName(name);
	this.actors.add(a);
	return a;
    }
    
    public LabelDto addLabel(String label) {
    	LabelDto l = new LabelDto();
    	l.setLabel(label);
    	this.labels.add(l);
    	return l;
        }

    public Set<ActorDto> getActors() {
	return this.actors;
    }

    public Set<CommentDto> getComments() {
	return this.comments;
    }

    public String getDescription() {
	return this.description;
    }

    public Set<LabelDto> getLabels() {
	return this.labels;
    }

    public long getLikeCount() {
	return this.likeCount;
    }

    public Set<LikeDto> getLikes() {
	return this.likes;
    }

    public double getRate() {
	return this.rate;
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

    public void setLikeCount(long likeCount) {
	this.likeCount = likeCount;
    }

    public void setRate(Double rate) {
	this.rate = rate;
    }

    public void setTitle(String title) {
	this.title = title;
    }
    
    public String retrieveActorsAsString() {
    	StringBuilder builder = new StringBuilder();
    	
    	for(ActorDto actor : actors) {
    		builder.append(actor.getName());
    		builder.append(", ");
    	}
    	
    	String result = builder.toString();
    	
    	return result.substring(0, result.length()-2);
    }
    
    public String retrieveLabelsAsString() {
    	StringBuilder builder = new StringBuilder();
    	
    	for(LabelDto label : labels) {
    		builder.append(label.getLabel());
    		builder.append(", ");
    	}
    	
    	String result = builder.toString();
    	
    	return result.substring(0, result.length()-2);
    }

}
