package br.com.naegling.config;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import br.com.naegling.interceptor.HandshakeInterceptor;
import br.com.naegling.webSocketHandler.NaeglingWebSocketHandler;

@Configuration
@EnableWebSocket
@ComponentScan(basePackages={"br.com.naegling.webSocketHandler"})
public class WebSocketConfiguration implements WebSocketConfigurer {
	String webSocketUrl="websocket/open";

	@Autowired
	private NaeglingWebSocketHandler naeglingWebSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(naeglingWebSocketHandler, webSocketUrl).setHandshakeHandler(handshakeHandler()).addInterceptors(new HandshakeInterceptor());
	}
	
	//Jetty Websocket Handler
    @Bean
      public DefaultHandshakeHandler handshakeHandler() {

        WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        policy.setIdleTimeout(600000);
        return new DefaultHandshakeHandler( new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
    }
	
}
