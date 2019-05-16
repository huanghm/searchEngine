package org.jbox.configuration;

/**
 * Thrown when the configuration file is not right mapped.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Configuration
 */
public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -7293649763906021828L;

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String msg) {
		super(msg);
	}

	public ConfigurationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}
}
