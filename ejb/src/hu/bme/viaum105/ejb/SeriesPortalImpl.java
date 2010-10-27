package hu.bme.viaum105.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.service.ServerException;
import hu.bme.viaum105.subtitle.SubtitleData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@PersistenceUnit(name = "SERIESPORTAL", unitName = "SERIESPORTAL")
@Stateless(name = SeriesPortal.JNDI_LOCATION, mappedName = SeriesPortal.JNDI_LOCATION)
@Remote(SeriesPortal.class)
public class SeriesPortalImpl implements SeriesPortal {

    private static final Log log = LogFactory.getLog(SeriesPortalImpl.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
	SeriesPortalImpl.log.trace("SeriesPortalImpl created");
    }

    @Override
    public User register(User user) throws DaoException, ServerException {
	if (SeriesPortalImpl.log.isTraceEnabled()) {
	    SeriesPortalImpl.log.trace("register: " + user);
	}
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).register(user);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    public Subtitle uploadSubtitle(Episode episode, Subtitle subtitle, SubtitleData subtitleData) throws DaoException, ServerException {
	// TODO Auto-generated method stub
	return null;
    }

}
