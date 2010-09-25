package hu.bme.viaum105.service;

public class DaoException extends Exception {

    private static final long serialVersionUID = 2427825029634028927L;

    public DaoException() {
	super();
    }

    public DaoException(String message) {
	super(message);
    }

    public DaoException(String message, Throwable cause) {
	super(message, cause);
    }

    public DaoException(Throwable cause) {
	super(cause);
    }

}
