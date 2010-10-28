package hu.bme.viaum105.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.RegisteredEntity;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.service.ServerException;
import hu.bme.viaum105.subtitle.SubtitleData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@PersistenceUnit(name = "SERIESPORTAL", unitName = "SERIESPORTAL")
@Stateless(name = SeriesPortal.JNDI_NAME, mappedName = SeriesPortal.JNDI_NAME)
@Remote(SeriesPortal.class)
public class SeriesPortalImpl implements SeriesPortal {

    private static final Log log = LogFactory.getLog(SeriesPortalImpl.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Series> getAllSeriesPaged(int pageSize, int pageNumber) throws DaoException {
	SeriesPortalImpl.log.trace("getAllSeriesPaged");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).getAllSeriesPaged(pageSize, pageNumber);
	} finally {
	    entityManager.close();
	}
    }

    @PostConstruct
    @SuppressWarnings("unused")
    private void init() {
	SeriesPortalImpl.log.trace("SeriesPortalImpl created");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void rate(RegisteredEntity registeredEntity, User user, int rate) throws DaoException {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    // TODO rate keresése, user értékelte-e már
	    // Ha igen, módosítás, egyébként insert
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Episode save(Episode episode) throws DaoException {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).save(episode);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Series save(Series series) throws DaoException {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).save(series);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Subtitle uploadSubtitle(Episode episode, Subtitle subtitle, SubtitleData subtitleData) throws DaoException, ServerException {
	// TODO Auto-generated method stub
	return null;
    }

}
