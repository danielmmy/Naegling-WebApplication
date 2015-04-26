package br.com.naegling.webSocketHandler;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import br.com.naegling.domain.VirtualNode;
import br.com.naegling.service.AccountService;
import br.com.naegling.tools.VncProxy;

@Controller
public class NaeglingWebSocketHandler extends BinaryWebSocketHandler{
	private HashMap<String, VncProxy> map=new HashMap<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(NaeglingWebSocketHandler.class);
	@Resource
	private AccountService accountService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session){
		try {
			//Restricting vnc proxies to one per user. If it's unnecessary should map the proxy to webSocketSession id instead of principal's name.
			if(!map.containsKey(session.getPrincipal().getName())){
				VncProxy vnc=new VncProxy(session);
				Thread t= new Thread(vnc);
				t.start();
				map.put(session.getPrincipal().getName(), vnc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session,  BinaryMessage message){
		if(map.get(session.getPrincipal().getName()).getNode()==null){
			Long nodeId;
			String str=new String(message.getPayload().array());	
			nodeId=Long.parseLong(str);
			VirtualNode node=accountService.findAccountNodeByNodeId(session.getPrincipal().getName(),nodeId );
			if(node!=null){
				LOGGER.debug("Setting node to: "+node.getDomain());
				map.get(session.getPrincipal().getName()).setNode(node);
			}else{
				LOGGER.debug("Illegal Access! Account is not the owner of node with id "+nodeId);
			}
		}else{
			try {
				map.get(session.getPrincipal().getName()).getSocket().getOutputStream().write(message.getPayload().array());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
		map.remove(session.getPrincipal().getName());
		LOGGER.debug("Closed session for user "+session.getPrincipal().getName()+". Websocket session exited with status "+status.getCode()+": "+status.getReason());
	}

}
