package hu.bme.viaum105.service;

public class ServerException extends Exception {

    private static final long serialVersionUID = 2854313661578497269L;

    public ServerException() {
	super();
    }

    public ServerException(String message) {
	super(message);
    }

    public ServerException(String message, Throwable cause) {
	super(message, cause);
    }

    public ServerException(Throwable cause) {
	super(cause);
    }

}
