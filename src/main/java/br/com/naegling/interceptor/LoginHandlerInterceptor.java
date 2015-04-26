package br.com.naegling.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import br.com.naegling.controller.AccountController;
import br.com.naegling.controller.LoginController;
import br.com.naegling.domain.Account;
import br.com.naegling.service.exception.AuthenticationException;



/**
 * {@code HandlerInterceptor} to apply security to controllers.
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    	LOGGER.debug("test interceptor !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Account account = (Account) WebUtils.getSessionAttribute(request, LoginController.ACCOUNT_ATTRIBUTE);
        if (account == null) {

            //Retrieve and store the original URL.
            String url = request.getRequestURL().toString();
            WebUtils.setSessionAttribute(request, LoginController.REQUESTED_URL, url);
            throw new AuthenticationException("Authentication required.", "authentication.required");
        }
        
        return true;
    }

}
