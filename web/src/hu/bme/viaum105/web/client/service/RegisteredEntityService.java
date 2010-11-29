package hu.bme.viaum105.web.client.service;

import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface RegisteredEntityService extends RemoteService {
	
	List<SeriesDto> findAllSerie();

}
