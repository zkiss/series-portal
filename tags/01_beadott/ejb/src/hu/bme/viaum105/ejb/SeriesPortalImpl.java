package hu.bme.viaum105.ejb;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import hu.bme.viaum105.data.persistent.SubtitleData;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.ErrorType;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.service.ServerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@PersistenceUnit(name = "SERIESPORTAL", unitName = "SERIESPORTAL")
@Stateless(name = SeriesPortal.JNDI_NAME)
@Remote(SeriesPortal.class)
public class SeriesPortalImpl implements SeriesPortal {

    private static final Log log = LogFactory.getLog(SeriesPortalImpl.class);

    private static void switchActors(SeriesPortalDao dao, RegisteredEntity entity) throws DaoException {
	HashSet<Actor> actorsInDb = new HashSet<Actor>();
	for (Iterator<Actor> iterator = entity.getActors().iterator(); iterator.hasNext();) {
	    Actor a = iterator.next();
	    if (a.getId() == 0) {
		Actor actorInDb = dao.getActor(a.getName());
		if (actorInDb != null) {
		    iterator.remove();
		    actorsInDb.add(actorInDb);
		}
	    }
	}
	entity.getActors().addAll(actorsInDb);
    }

    private static void switchLabels(SeriesPortalDao dao, RegisteredEntity entity) throws DaoException {
	HashSet<Label> labelsInDb = new HashSet<Label>();
	for (Iterator<Label> iterator = entity.getLabels().iterator(); iterator.hasNext();) {
	    Label label = iterator.next();
	    label.setLabel(label.getLabel().toLowerCase());
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
    public void approveComment(long commentId) throws DaoException, ServerException {
	SeriesPortalImpl.log.trace("approveComment");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    Comment comment = dao.getComment(commentId);
	    if (comment == null) {
		throw new ServerException(ErrorType.ILLEGAL_ARGUMENT, "No comment found with id #" + commentId);
	    }
	    comment.setApproved(true);
	    dao.save(comment);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void changeUserPassword(long userId, String newPassword) throws DaoException, ServerException {
	SeriesPortalImpl.log.trace("changeUserPassword");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    User user = dao.getUser(userId);
	    if (user == null) {
		throw new ServerException(ErrorType.ILLEGAL_ARGUMENT, "No user found with id #" + userId);
	    }
	    user.setPassword(newPassword);
	    dao.save(user);
	} finally {
	    entityManager.close();
	}
    }

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
	    ret.setApproved(false);
	    return new SeriesPortalDao(entityManager).save(ret);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SubtitleData downloadSubtitle(long subtitleId) throws DaoException, ServerException {
	SeriesPortalImpl.log.trace("downloadSubtitle");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    SubtitleData subtitleData = dao.getSubtitleData(subtitleId);
	    if (subtitleData == null) {
		throw new ServerException(ErrorType.ILLEGAL_ARGUMENT, "There is no subtitle with id #" + subtitleId);
	    }
	    return subtitleData;
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
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean isLoginNameAvailable(String loginName) throws DaoException {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.isLoginNameAvailable(loginName);
	} finally {
	    entityManager.close();
	}
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
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Series> listTopRatedSeries(int pageSize, int pageNumber) throws DaoException {
	SeriesPortalImpl.log.trace("getTopRatedSeries");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.listTopRatedSeries(pageSize, pageNumber);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    public List<Comment> listUnapprovedComments(int pageSize, int pageNumber) throws DaoException {
	SeriesPortalImpl.log.trace("listUnapprovedComments");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.listUnapprovedComments(pageSize, pageNumber);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
	    SeriesPortalImpl.switchLabels(dao, episode);
	    SeriesPortalImpl.switchActors(dao, episode);
	    dao.handleLazyInitializedProperties(episode);
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
	    SeriesPortalImpl.switchLabels(dao, series);
	    SeriesPortalImpl.switchActors(dao, series);
	    return dao.save(series);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RegisteredEntity> searchByActors(Set<String> actors) throws DaoException {
	SeriesPortalImpl.log.trace("searchByActors");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.searchByActors(actors);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RegisteredEntity> searchByDescription(String description) throws DaoException {
	SeriesPortalImpl.log.trace("searchByDescription");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.searchByDescription(description);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RegisteredEntity> searchByLabels(Set<String> labels) throws DaoException {
	SeriesPortalImpl.log.trace("searchByLabels");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.searchByLabels(labels);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RegisteredEntity> searchByTitle(String title) throws DaoException {
	SeriesPortalImpl.log.trace("searchByTitle");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.searchByTitle(title);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Series> searchSeriesByDirector(String director) throws DaoException {
	SeriesPortalImpl.log.trace("searchSeriesByDirector");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.searchSeriesByDirector(director);
	} finally {
	    entityManager.close();
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Subtitle uploadSubtitle(Subtitle subtitle) throws DaoException, ServerException {
	SeriesPortalImpl.log.trace("uploadSubtitle");
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	try {
	    SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	    return dao.save(subtitle);
	} finally {
	    entityManager.close();
	}
    }

}
