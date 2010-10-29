package hu.bme.viaum105.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import hu.bme.viaum105.data.persistent.Comment;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Like;
import hu.bme.viaum105.data.persistent.Rate;
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Comment comment(RegisteredEntity registeredEntity, User user, String comment) throws DaoException {
	SeriesPortalImpl.log.trace("comment");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    Comment ret = new Comment();
	    ret.setComment(comment);
	    ret.setDate(new Date());
	    ret.setRegisteredEntity(registeredEntity);
	    ret.setUser(user);
	    return new SeriesPortalDao(entityManager).save(ret);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long getLikeCount(long registeredEntityId) throws DaoException {
	SeriesPortalImpl.log.trace("getLikeCount");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).getLikeCount(registeredEntityId);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public double getRate(long registeredEntityId) throws DaoException {
	SeriesPortalImpl.log.trace("getRate");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).getRate(registeredEntityId);
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
    public Like like(RegisteredEntity registeredEntity, User user) throws DaoException {
	SeriesPortalImpl.log.trace("like");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    Like like = new Like();
	    like.setRegisteredEntity(registeredEntity);
	    like.setUser(user);
	    return new SeriesPortalDao(entityManager).save(like);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Series> listSeriesPaged(int pageSize, int pageNumber) throws DaoException {
	SeriesPortalImpl.log.trace("getAllSeriesPaged");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).getAllSeriesPaged(pageSize, pageNumber);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Rate rate(RegisteredEntity registeredEntity, User user, int rate) throws DaoException {
	SeriesPortalImpl.log.trace("rate");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    Rate r = dao.findRate(registeredEntity, user);
	    if (r == null) {
		r = new Rate();
		r.setUser(user);
		r.setRegisteredEntity(registeredEntity);
	    }
	    r.setRate(rate);
	    return dao.save(r);
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
	SeriesPortalImpl.log.trace("save episode");
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
	SeriesPortalImpl.log.trace("save series");
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
	SeriesPortalImpl.log.trace("uploadSubtitle");
	// TODO Auto-generated method stub
	return null;
    }

}
