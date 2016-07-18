package poc.backend.exception;

import java.util.logging.Logger;

/**
 * @author Jharbas Araujo
 */
public final class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(String msg, Throwable exception, Logger logger) {
		super(msg, exception);
		logger.severe(msg);
	}

	public ServiceException(String msg, Logger logger) {
		super(msg);
		logger.severe(msg);
	}
}
