package hu.bme.viaum105.service;

import java.util.List;

import javax.persistence.Query;

public abstract class DaoHelper {

    @SuppressWarnings("unchecked")
    public static <T> List<T> getResultList(Query query, Class<T> clazz) throws DaoException {
	try {
	    return query.getResultList();
	} catch (RuntimeException e) {
	    throw new DaoException("Could not execute query", e);
	}
    }

    private DaoHelper() {
	throw new UnsupportedOperationException("This class should not be instantiated");
    }

}
