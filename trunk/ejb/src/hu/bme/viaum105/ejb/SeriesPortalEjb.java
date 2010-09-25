package hu.bme.viaum105.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceUnit;

import hu.bme.viaum105.service.SeriesPortalService;

@PersistenceUnit(name = "SERIESPORTAL", unitName = "SERIESPORTAL")
@Stateless(name = SeriesPortalService.JNDI_LOCATION, mappedName = SeriesPortalService.JNDI_LOCATION)
@Remote(SeriesPortalService.class)
public class SeriesPortalEjb implements SeriesPortalService {

    // TODO

}
