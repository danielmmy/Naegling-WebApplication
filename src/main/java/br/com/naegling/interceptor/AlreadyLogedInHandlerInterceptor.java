package br.com.naegling.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import br.com.naegling.controller.LoginController;
import br.com.naegling.domain.Account;
import br.com.naegling.service.exception.AuthenticationDoneException;

public class AlreadyLogedInHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account) WebUtils.getSessionAttribute(request, LoginController.ACCOUNT_ATTRIBUTE);
        if (account != null) {
            throw new AuthenticationDoneException("Authentication done.", "authentication.done");
        }
        
        return true;
    }

}
