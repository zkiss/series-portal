package hu.bme.viaum105.ejb;

import java.util.List;

import javax.persistence.EntityManager;

import hu.bme.viaum105.data.persistent.EntityBase;
import hu.bme.viaum105.data.persistent.Like;
import hu.bme.viaum105.data.persistent.Rate;
import hu.bme.viaum105.data.persistent.RegisteredEntity;
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

    public Rate findRate(RegisteredEntity registeredEntity, User user) throws DaoException {
	List<Rate> rates = DaoHelper.getResultList(this.entityManager.createQuery("select r from " + Rate.class.getSimpleName() + " r" + //
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

    public List<Series> getAllSeriesPaged(int pageSize, int pageNumber) throws DaoException {
	return DaoHelper.getResultList(this.entityManager.createQuery( //
		"select s from " + Series.class.getSimpleName() + " s order by s.name asc"). //
		setMaxResults(pageSize). //
		setFirstResult(pageSize * pageNumber), Series.class);
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

    public double getRate(long registeredEntityId) throws DaoException {
	try {
	    Number rate = (Number) this.entityManager.createQuery("select avg(r.rate) from " + Rate.class.getSimpleName() + " r " + //
		    "where r.registeredEntity.id = :id"). //
		    setParameter("id", registeredEntityId). //
		    getSingleResult();
	    return rate.doubleValue();
	} catch (RuntimeException e) {
	    throw new DaoException("Could not determine rate for registered entity #" + registeredEntityId, e);
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

}
