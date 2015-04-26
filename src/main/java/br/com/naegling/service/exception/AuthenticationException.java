package br.com.naegling.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.naegling.controller.AccountController;

/**
 * Thrown when username or password are incorect
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 * 
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthenticationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;

	public AuthenticationException(String message, String code) {
		super(message);
		
		final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    	LOGGER.debug("test AuthenticationException #############################################################################################################################");
		
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}