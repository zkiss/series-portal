package hu.bme.viaum105.web.server.service;

import java.util.LinkedList;
import java.util.List;

import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.service.ServerException;
import hu.bme.viaum105.web.client.service.RegisteredEntityService;
import hu.bme.viaum105.web.server.ServiceLocator;
import hu.bme.viaum105.web.server.converter.Converter;
import hu.bme.viaum105.web.server.converter.ConverterException;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RegisteredEntityServiceImpl extends RemoteServiceServlet implements
		RegisteredEntityService {

	private static final long serialVersionUID = -2521223347904535867L;

	public List<SeriesDto> findAllSerie() {

		System.out.println("Meghívódik az entitás szolgáltatás!");
		
		SeriesDto s1 = new SeriesDto();
		s1.setTitle("Supernatural");
		s1.setDescription("Nagyon hosszú leírás a sorozatról. " +
				"Nagyon hosszú leírás a sorozatról. " +
				"Nagyon hosszú leírás a sorozatról.");
		
		SeriesDto s2 = new SeriesDto();
		s2.setTitle("Dexter");
		s2.setDescription("Nagyon hosszú leírás a sorozatról. " +
				"Nagyon hosszú leírás a sorozatról. " +
				"Nagyon hosszú leírás a sorozatról.");
		
		List<SeriesDto> list = new LinkedList<SeriesDto>();
		list.add(s1);
		list.add(s2);
		
		return list;
	}
	
	public void createNewSerie(SeriesDto serie) {
		System.out.println("új sorozat: "+serie.getTitle());
		
		try {
			SeriesPortal services = ServiceLocator.getInstance().getSeriesPortalService();
			
			Series converted = Converter.convert(serie);
			
			services.save(converted);
			
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConverterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
