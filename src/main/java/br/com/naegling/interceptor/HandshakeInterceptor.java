package br.com.naegling.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import br.com.naegling.config.WebSocketConfiguration;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfiguration.class);
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		LOGGER.debug("++++++++++++++_________________________+++++++++++++++++=========================");
		LOGGER.debug("Before Handshake");
		LOGGER.debug(request.getHeaders().toString());
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		LOGGER.debug("++++++++++++++_________________________+++++++++++++++++=========================");
		LOGGER.debug("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
