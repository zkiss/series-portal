package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Sorozatokhoz és epizódokhoz tartozó szolgáltatások aszinkron interfésze.
 * 
 * @author zoli
 */
public interface RegisteredEntityServiceAsync {

	void findAllSeries(AsyncCallback<List<SeriesDto>> callback);
	
	void createNewSeries(SeriesDto series, AsyncCallback<Void> callback);
	
	void createNewEpisode(EpisodeDto episode, AsyncCallback<Void> callback);
	
}
