package poc.backend.exception;

import java.util.logging.Logger;

/**
 * @author Jharbas Araujo
 */
public final class RepositoryException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepositoryException(String msg, Throwable exception, Logger logger) {
		super(msg, exception);
		logger.severe(msg);
	}

	public RepositoryException(String msg, Logger logger) {
		super(msg);
		logger.severe(msg);
	}

}
