package hu.bme.viaum105.ejb;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import hu.bme.viaum105.data.persistent.Actor;
import hu.bme.viaum105.data.persistent.Comment;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Label;
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
@Stateless(name = SeriesPortal.JNDI_NAME)
@Remote(SeriesPortal.class)
public class SeriesPortalImpl implements SeriesPortal {

    private static final Log log = LogFactory.getLog(SeriesPortalImpl.class);

    private static void switchLabels(SeriesPortalDao dao, RegisteredEntity entity) throws DaoException {
	HashSet<Label> labelsInDb = new HashSet<Label>();
	for (Iterator<Label> iterator = entity.getLabels().iterator(); iterator.hasNext();) {
	    Label label = iterator.next();
	    if (label.getId() == 0) {
		Label labelInDb = dao.getLabel(label.getLabel());
		if (labelInDb != null) {
		    iterator.remove();
		    labelsInDb.add(labelInDb);
		}
	    }
	}
	entity.getLabels().addAll(labelsInDb);
    }

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
    public Double getRate(long registeredEntityId) throws DaoException {
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
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    Like like = dao.findLike(registeredEntity, user);
	    if (like == null) {
		like = new Like();
		like.setRegisteredEntity(registeredEntity);
		like.setUser(user);
		like = dao.save(like);
	    }
	    return like;
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
	    return new SeriesPortalDao(entityManager).listSeriesPaged(pageSize, pageNumber);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    public User login(String loginname, String passwordHash) throws DaoException, ServerException {
	SeriesPortalImpl.log.trace("login");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    return new SeriesPortalDao(entityManager).getUser(loginname, passwordHash);
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
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);

	    // label-ek kikeresése
	    SeriesPortalImpl.switchLabels(dao, episode);

	    // színészek kikeresése
	    HashSet<Actor> actorsInDb = new HashSet<Actor>();
	    for (Iterator<Actor> iterator = episode.getActors().iterator(); iterator.hasNext();) {
		Actor a = iterator.next();
		if (a.getId() == 0) {
		    Actor actorInDb = dao.getActor(a.getName());
		    if (actorInDb != null) {
			iterator.remove();
			actorsInDb.add(actorInDb);
		    }
		}
	    }
	    episode.getActors().addAll(actorsInDb);
	    return dao.save(episode);
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
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    // label-ek kikeresése
	    SeriesPortalImpl.switchLabels(dao, series);
	    return dao.save(series);
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
