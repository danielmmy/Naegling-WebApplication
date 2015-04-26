package br.com.naegling.tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import br.com.naegling.domain.VirtualNode;


public class VncProxy implements Runnable{

	//private static final Logger LOGGER = LoggerFactory.getLogger(VncProxy.class);
	private InetAddress serverAddr;
	private WebSocketSession webSocketSession;
	private Socket socket;
	private byte []bytes;
	private VirtualNode node;

	public Socket getSocket() {
		return socket;
	}

	public VirtualNode getNode() {
		return node;
	}


	public void setNode(VirtualNode node){
		this.node = node;
			try {
				serverAddr=InetAddress.getByName(node.getHost().getHostName());
				this.socket= new Socket(serverAddr, node.getGraphicalAccessPort());
				this.bytes= new byte[socket.getReceiveBufferSize()];
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	public VncProxy(WebSocketSession session){		
		this.webSocketSession=session;
	}

	@Override
	public void run() {
		while(node==null){
			//do nothing.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true){
			try{
				int sz;
				sz=socket.getInputStream().read(bytes);
				if(sz>0){
					webSocketSession.sendMessage(new BinaryMessage(Arrays.copyOfRange(bytes, 0, sz)));
				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
