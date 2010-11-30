package hu.bme.viaum105.ejb;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hu.bme.viaum105.data.persistent.Actor;
import hu.bme.viaum105.data.persistent.Comment;
import hu.bme.viaum105.data.persistent.EntityBase;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Label;
import hu.bme.viaum105.data.persistent.Like;
import hu.bme.viaum105.data.persistent.Rate;
import hu.bme.viaum105.data.persistent.RegisteredEntity;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.SubtitleData;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.DaoHelper;
import hu.bme.viaum105.service.ErrorType;
import hu.bme.viaum105.service.ServerException;

public class SeriesPortalDao {

    private final EntityManager entityManager;

    public SeriesPortalDao(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    public Like findLike(RegisteredEntity registeredEntity, User user) throws DaoException {
	List<Like> likes = DaoHelper.getResultList(this.entityManager.createQuery( //
		"select l from " + Like.class.getSimpleName() + " l" + //
			" where l.user = :user" + //
			" and l.registeredEntity = :entity"). //
		setParameter("user", user). //
		setParameter("entity", registeredEntity), Like.class);
	Like ret;
	if (likes.size() == 0) {
	    ret = null;
	} else {
	    ret = likes.get(0);
	}
	return ret;
    }

    public Rate findRate(RegisteredEntity registeredEntity, User user) throws DaoException {
	List<Rate> rates = DaoHelper.getResultList(this.entityManager.createQuery( //
		"select r from " + Rate.class.getSimpleName() + " r" + //
			" where r.user = :user" + //
			" and r.registeredEntity = :entity"). //
		setParameter("user", user). //
		setParameter("entity", registeredEntity), Rate.class);
	Rate ret;
	if (rates.size() == 0) {
	    ret = null;
	} else {
	    ret = rates.get(0);
	}
	return ret;
    }

    public Actor getActor(String name) throws DaoException {
	Actor ret;
	try {
	    ret = (Actor) this.entityManager.createQuery( //
		    "select a from " + Actor.class.getSimpleName() + " a" + //
			    " where a.name = :name"). //
		    setParameter("name", name).getSingleResult();
	} catch (NoResultException e) {
	    ret = null;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not get actor: " + name, e);
	}
	return ret;
    }

    public Comment getComment(long commentId) throws DaoException {
	Comment ret = null;
	try {
	    ret = this.entityManager.find(Comment.class, commentId);
	} catch (NoResultException e) {
	    // nincs találat
	} catch (RuntimeException e) {
	    throw new DaoException("Could not get comment #" + commentId, e);
	}
	return ret;
    }

    public Label getLabel(String label) throws DaoException {
	Label ret;
	try {
	    ret = (Label) this.entityManager.createQuery( //
		    "select l from " + Label.class.getSimpleName() + " l" + //
			    " where l.label = :label"). //
		    setParameter("label", label).getSingleResult();
	} catch (NoResultException e) {
	    ret = null;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not get label: " + label, e);
	}
	return ret;
    }

    private <T extends RegisteredEntity> void getLikeCntAndRates(Collection<T> entities) throws DaoException {
	for (T entity : entities) {
	    entity.setRate(this.getRate(entity.getId()));
	    entity.setLikeCount(this.getLikeCount(entity.getId()));
	}
    }

    public long getLikeCount(long registeredEntityId) throws DaoException {
	try {
	    Number ret = (Number) this.entityManager.createQuery( //
		    "select count(l) from " + Like.class.getSimpleName() + " l where l.registeredEntity.id = :id"). //
		    setParameter("id", registeredEntityId).getSingleResult();
	    return ret.longValue();
	} catch (RuntimeException e) {
	    throw new DaoException("Could not query like count for registered entity #" + registeredEntityId, e);
	}
    }

    public Double getRate(long registeredEntityId) throws DaoException {
	try {
	    Number rate = (Number) this.entityManager.createQuery( //
		    "select avg(r.rate) from " + Rate.class.getSimpleName() + " r " + //
			    "where r.registeredEntity.id = :id"). //
		    setParameter("id", registeredEntityId). //
		    getSingleResult();
	    return rate == null ? null : rate.doubleValue();
	} catch (RuntimeException e) {
	    throw new DaoException("Could not determine rate for registered entity #" + registeredEntityId, e);
	}
    }

    public SubtitleData getSubtitleData(long subtitleId) throws DaoException {
	SubtitleData ret = null;
	try {
	    ret = (SubtitleData) this.entityManager.createQuery( //
		    "select s from " + SubtitleData.class.getSimpleName() + " s where s.subtitle.id = :id"). //
		    setParameter("id", subtitleId).getSingleResult();
	} catch (NoResultException e) {
	    // nincs ilyen subtitle
	} catch (RuntimeException e) {
	    throw new DaoException("Could not get subtitle data for subtitle #" + subtitleId, e);
	}
	return ret;
    }

    public User getUser(long userId) throws DaoException {
	User ret;
	try {
	    ret = (User) this.entityManager.createQuery( //
		    "select u from " + User.class.getSimpleName() + " u" + //
			    " where u.id = :userId"). //
		    setParameter("userId", userId).getSingleResult();
	} catch (NoResultException e) {
	    ret = null;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not execute query", e);
	}
	return ret;
    }

    public User getUser(String loginname, String passwordHash) throws ServerException, DaoException {
	try {
	    return (User) this.entityManager.createQuery( //
		    "select u from " + User.class.getSimpleName() + " u" + //
			    " where loginName = :loginname" + //
			    " and u.password = :password"). //
		    setParameter("loginname", loginname). //
		    setParameter("password", passwordHash).getSingleResult();
	} catch (NoResultException e) {
	    throw new ServerException(ErrorType.LOGIN_ERROR, "No user found with the supplied loginname and password hash", e);
	} catch (RuntimeException e) {
	    throw new DaoException("Could not execute query", e);
	}
    }

    public void handleLazyInitializedProperties(Episode episode) throws DaoException {
	/*
	 * Series property lazy init, de nem nullable. ezért ha null a series,
	 * akkor hagyhatjuk az eredeti beállítást
	 */
	if (episode.getSeries() == null) {
	    try {
		Episode episodeInDb = this.entityManager.find(Episode.class, episode.getId());
		if (episodeInDb == null) {
		    throw new DaoException("Episode is deleted: " + episode);
		}
		episode.setSeries(episodeInDb.getSeries());
	    } catch (RuntimeException e) {
		throw new DaoException("Could not refresh series property of " + episode, e);
	    }
	}
    }

    public boolean isLoginNameAvailable(String loginName) throws DaoException {
	try {
	    Number cnt = (Number) this.entityManager.createQuery( //
		    "select count(u) from " + User.class.getSimpleName() + " u" + //
			    " where lower(u.loginName) = :loginName"). //
		    setParameter("loginName", loginName.toLowerCase()).getSingleResult();
	    return cnt.longValue() == 0;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not determine if login name is free: " + loginName, e);
	}
    }

    public List<Series> listSeriesPaged(int pageSize, int pageNumber) throws DaoException {
	List<Series> series = DaoHelper.getResultList(this.entityManager.createQuery( //
		"select s from " + Series.class.getSimpleName() + " s order by s.title asc"). //
		setMaxResults(pageSize). //
		setFirstResult(pageSize * pageNumber), Series.class);
	this.getLikeCntAndRates(series);
	return series;
    }

    public List<Series> listTopRatedSeries(int pageSize, int pageNumber) throws DaoException {
	try {
	    List<Series> entities = DaoHelper.getResultList(this.entityManager.createQuery( //
		    "select s from " + Series.class.getSimpleName() + " s" + //
			    ", " + Rate.class.getSimpleName() + " r" + //
			    " where r.registeredEntity = s" + //
			    " order by count(r) desc"). //
		    setMaxResults(pageSize). //
		    setFirstResult(pageSize * pageNumber), Series.class);
	    for (Iterator<Series> iterator = entities.iterator(); iterator.hasNext();) {
		Series series = iterator.next();
		if (series == null) {
		    iterator.remove();
		}
	    }
	    this.getLikeCntAndRates(entities);
	    return entities;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not get top rated series", e);
	}
    }

    public User register(User user) throws DaoException, ServerException {
	try {
	    Long cnt = (Long) this.entityManager.createQuery( //
		    "select count(u) from " + User.class.getSimpleName() + " u where u.loginName = :loginName"). //
		    setParameter("loginName", user.getLoginName()).getSingleResult();
	    if (cnt > 0) {
		throw new ServerException(ErrorType.LOGINNAME_IS_IN_USE, "Loginname is already in use");
	    }

	    return this.entityManager.merge(user);
	} catch (RuntimeException e) {
	    throw new DaoException("Could not register user: " + user, e);
	}
    }

    public <T extends EntityBase> T save(T entity) throws DaoException {
	try {
	    return this.entityManager.merge(entity);
	} catch (RuntimeException e) {
	    throw new DaoException("Could not save " + entity, e);
	}
    }

    public List<RegisteredEntity> searchByActors(Set<String> actors) throws DaoException {
	TreeSet<String> actorNames = new TreeSet<String>();
	for (String actorName : actors) {
	    actorNames.add(actorName.toLowerCase());
	}
	try {
	    List<RegisteredEntity> entities = DaoHelper.getResultList(this.entityManager.createQuery( //
		    "select re from " + RegisteredEntity.class.getSimpleName() + " re" + //
			    " inner join re.actors a" + //
			    " where lower(a.name) in (:actors)"). //
		    setParameter("actors", actorNames), RegisteredEntity.class);
	    this.getLikeCntAndRates(entities);
	    return entities;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not search by actors: " + actorNames, e);
	}
    }

    public List<RegisteredEntity> searchByDescription(String description) throws DaoException {
	try {
	    List<RegisteredEntity> entities = DaoHelper.getResultList(this.entityManager.createQuery( //
		    "select re from " + RegisteredEntity.class.getSimpleName() + " re" + //
			    " where lower(re.description) like lower(:description)"). //
		    setParameter("description", "%" + description + "%"), RegisteredEntity.class);
	    this.getLikeCntAndRates(entities);
	    return entities;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not search by description: " + description, e);
	}
    }

    public List<RegisteredEntity> searchByLabels(Set<String> labels) throws DaoException {
	TreeSet<String> labelsLowerCase = new TreeSet<String>();
	for (String actorName : labels) {
	    labelsLowerCase.add(actorName.toLowerCase());
	}
	try {
	    List<RegisteredEntity> entities = DaoHelper.getResultList(this.entityManager.createQuery( //
		    "select re from " + RegisteredEntity.class.getSimpleName() + " re" + //
			    " inner join re.labels l" + //
			    " where lower(l.name) in (:labels)"). //
		    setParameter("labels", labelsLowerCase), RegisteredEntity.class);
	    this.getLikeCntAndRates(entities);
	    return entities;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not search by labels: " + labelsLowerCase, e);
	}
    }

    public List<RegisteredEntity> searchByTitle(String title) throws DaoException {
	try {
	    List<RegisteredEntity> entities = DaoHelper.getResultList(this.entityManager.createQuery( //
		    "select re from " + RegisteredEntity.class.getSimpleName() + " re" + //
			    " where lower(re.title) like lower(:title)"). //
		    setParameter("title", "%" + title + "%"), RegisteredEntity.class);
	    this.getLikeCntAndRates(entities);
	    return entities;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not search by title: " + title, e);
	}
    }

    public List<Series> searchSeriesByDirector(String director) throws DaoException {
	try {
	    List<Series> entities = DaoHelper.getResultList(this.entityManager.createQuery( //
		    "select s from " + Series.class.getSimpleName() + " s" + //
			    " where lower(s.director) like lower(:director)"). //
		    setParameter("director", "%" + director + "%"), Series.class);
	    this.getLikeCntAndRates(entities);
	    return entities;
	} catch (RuntimeException e) {
	    throw new DaoException("Could not search by director: " + director, e);
	}
    }
}
