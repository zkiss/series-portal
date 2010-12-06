package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.CommentDto;
import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface RegisteredEntityService extends RemoteService {
	
	List<SeriesDto> findAllSeries();
	
	void createNewSeries(SeriesDto series);
	
	void createNewEpisode(EpisodeDto episode);

	void like(RegisteredEntityDto entity, UserDto user);
	
	void rate(RegisteredEntityDto entity, UserDto user, int rate);
	
	List<RegisteredEntityDto> searchByTitle(String title);
	
	List<RegisteredEntityDto> searchByDescription(String searchTerm);
	
	List<RegisteredEntityDto> searchByDirector(String searchTerm);
	
	List<RegisteredEntityDto> searchByLabel(String term);
	
	List<RegisteredEntityDto> searchByActor(String actor);
	
	void addComment(RegisteredEntityDto entity, UserDto user, String comment);
	
	List<CommentDto> getUnapprovedComments();
	
	void approveComment(long commentId);
}
