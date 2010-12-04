package hu.bme.viaum105.service;

public class ServerException extends Exception {

    private static final long serialVersionUID = 2854313661578497269L;

    private final ErrorType errorType;

    public ServerException(ErrorType errorType) {
	super();
	this.errorType = errorType;
    }

    public ServerException(ErrorType errorType, String message) {
	super(message);
	this.errorType = errorType;
    }

    public ServerException(ErrorType errorType, String message, Throwable cause) {
	super(message, cause);
	this.errorType = errorType;
    }

    public ServerException(ErrorType errorType, Throwable cause) {
	super(cause);
	this.errorType = errorType;
    }

    /**
     * A hiba pontosabb oka
     * 
     * @return
     */
    public ErrorType getErrorType() {
	return this.errorType;
    }

}
