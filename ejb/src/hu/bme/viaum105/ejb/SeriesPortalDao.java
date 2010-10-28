package hu.bme.viaum105.ejb;

import java.util.List;

import javax.persistence.EntityManager;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Series;
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

    public List<Series> getAllSeriesPaged(int pageSize, int pageNumber) throws DaoException {
	return DaoHelper.getResultList(this.entityManager.createQuery( //
		"select s from " + Series.class.getSimpleName() + " s order by s.name asc"). //
		setMaxResults(pageSize). //
		setFirstResult(pageSize * pageNumber), Series.class);
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

    public Episode save(Episode episode) throws DaoException {
	try {
	    return this.entityManager.merge(episode);
	} catch (RuntimeException e) {
	    throw new DaoException("Could not save " + episode, e);
	}
    }

    public Series save(Series series) throws DaoException {
	try {
	    return this.entityManager.merge(series);
	} catch (RuntimeException e) {
	    throw new DaoException("Could not save " + series, e);
	}
    }

}
