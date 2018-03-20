package com.harman.skillserver.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Class is to log exception related to database
 * 
 * @author inkkhanna
 *
 */
public class DatabaseException extends RuntimeException {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseException.class);
	private static final long serialVersionUID = 5L;

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public DatabaseException(String message) {
		this.message = message;
		LOGGER.debug("Exception > " + message);
	}

	public DatabaseException(String message, Throwable th) {
		this.message = message;
		LOGGER.debug("Exception > " + message);
		if (th != null) {
			LOGGER.error(" Error is : " + th.getMessage(), th);
		}
	}
}