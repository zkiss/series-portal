package hu.bme.viaum105.web.server;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import hu.bme.viaum105.service.ErrorType;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.service.ServerException;

public class ServiceLocator {

    private static ServiceLocator instance;

    public static ServiceLocator getInstance() throws ServerException {
	try {
	    if (ServiceLocator.instance == null) {
		ServiceLocator.instance = new ServiceLocator(new InitialContext());
	    }
	    return ServiceLocator.instance;
	} catch (NamingException e) {
	    throw new ServerException(ErrorType.NAMING_EXCEPTION, e);
	}
    }

    private Context context;

    private SeriesPortal seriesPortalService;

    private ServiceLocator(Context context) {
	this.context = context;
    }

    public SeriesPortal getSeriesPortalService() throws ServerException {
	if (this.seriesPortalService == null) {
	    this.seriesPortalService = this.lookup(SeriesPortal.JNDI_LOCATION, SeriesPortal.class);
	}
	return this.seriesPortalService;
    }

    @SuppressWarnings("unchecked")
    public <T> T lookup(String name, Class<T> clazz) throws ServerException {
	try {
	    return (T) this.context.lookup(name.startsWith("jms") ? name : "series-portal/" + name + "/remote");
	} catch (NamingException e) {
	    throw new ServerException(ErrorType.NAMING_EXCEPTION, e);
	}
    }

}
