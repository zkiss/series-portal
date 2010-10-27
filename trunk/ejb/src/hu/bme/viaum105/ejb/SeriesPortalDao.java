package hu.bme.viaum105.ejb;

import javax.persistence.EntityManager;

import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.ErrorType;
import hu.bme.viaum105.service.ServerException;

public class SeriesPortalDao {

    private final EntityManager entityManager;

    public SeriesPortalDao(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    public User register(User user) throws DaoException, ServerException {
	try {
	    Long cnt = (Long) this.entityManager.createQuery( //
		    "select count(u) from User u where u.loginName = :loginName"). //
		    setParameter("loginName", user.getLoginName()).getSingleResult();
	    if (cnt > 0) {
		throw new ServerException(ErrorType.LOGINNAME_IS_IN_USE, "Loginname is already in use");
	    }

	    return this.entityManager.merge(user);
	} catch (RuntimeException e) {
	    throw new DaoException("Could not register user: " + user, e);
	}
    }

}
