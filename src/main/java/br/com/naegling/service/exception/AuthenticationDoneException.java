package br.com.naegling.service.exception;


public class AuthenticationDoneException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	public AuthenticationDoneException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
