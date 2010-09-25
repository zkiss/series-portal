package hu.bme.viaum105.web.shared.exception;

public class ServiceException extends SharedException {

    private static final long serialVersionUID = -257494544779007438L;

    ServiceException() {
	// GWT miatt kell
    }

    public ServiceException(String message) {
	super(message);
    }

    public ServiceException(String message, SharedException cause) {
	super(message, cause);
    }

}
