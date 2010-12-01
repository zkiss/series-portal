package hu.bme.viaum105.web.server.service;

import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.server.ServiceLocator;
import hu.bme.viaum105.web.server.converter.Converter;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RegisteredEntityServiceImpl extends RemoteServiceServlet implements
		RegisteredEntityService {

	private static final long serialVersionUID = -2521223347904535867L;

	public List<SeriesDto> findAllSeries() {

		try {
			SeriesPortal services = ServiceLocator.getInstance().getSeriesPortalService();
			
			List<Series> seriesList = services.listSeriesPaged(50, 0);
			
			List<SeriesDto> seriesDtoList = new LinkedList<SeriesDto>();
			
			for(Series s : seriesList) {
				SeriesDto se = Converter.convert(s);
				seriesDtoList.add(se);
			}
			
			return seriesDtoList;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void createNewSeries(SeriesDto series) {
		
		System.out.println("Sorozat létrehozása: "+series.getTitle());
		
		try {
			SeriesPortal services = ServiceLocator.getInstance().getSeriesPortalService();
			
			Series converted = Converter.convert(series);
			
			services.save(converted);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
