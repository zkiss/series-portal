package hu.bme.viaum105.web.server.service;

import java.util.LinkedList;
import java.util.List;

import hu.bme.viaum105.web.client.service.RegisteredEntityService;
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

}
