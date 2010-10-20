package hu.bme.viaum105.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import hu.bme.viaum105.service.SeriesPortal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@PersistenceUnit(name = "SERIESPORTAL", unitName = "SERIESPORTAL")
@Stateless(name = SeriesPortal.JNDI_LOCATION, mappedName = SeriesPortal.JNDI_LOCATION)
@Remote(SeriesPortal.class)
public class SeriesPortalImpl implements SeriesPortal {

    private static final Log log = LogFactory.getLog(SeriesPortalImpl.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    // TODO

    @PostConstruct
    private void init() {
	SeriesPortalImpl.log.trace("SeriesPortalImpl created");
    }

}
