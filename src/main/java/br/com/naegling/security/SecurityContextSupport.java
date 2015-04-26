package br.com.naegling.security;

import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Support class for easy access to our custom {@link NaeglingUserDetails}
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 * 
 */
public class SecurityContextSupport {

	public static NaeglingUserDetails getUserDetails() {
		return (NaeglingUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
