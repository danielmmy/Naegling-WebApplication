package br.com.naegling.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.springframework.stereotype.Service;

@Service
public class NaeglingComServiceImp implements NaeglingComService {
	public static final String MESSAGE_DELIMITER="#";
	public static final int  NAEGLING_COM_TIMEOUT=5000;
	public static final int  UDP_MESSAGE_MAX_SIZE=512;
	public static final int  TCP_MESSAGE_MAX_SIZE=5242880;

	@Override
	public int sendMessageToHostname(String message, String hostname, String naeglingPort) throws Exception {
		/*
		 * Create socket and send message.
		 */
		if(message.length()<TCP_MESSAGE_MAX_SIZE){
			InetAddress serverAddr=InetAddress.getByName(hostname);
			Socket socket = new Socket(serverAddr, Integer.parseInt(naeglingPort));
			socket.setSoTimeout(NAEGLING_COM_TIMEOUT);//time to wait
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			if(socket.isConnected()){
				System.out.println("sending: "+message);
				out.print(message);
				out.flush();
				message=br.readLine();
				System.out.println("received: "+message);
			}else{
				message="-2#";
			}
			String[] receivedFlags=message.split(MESSAGE_DELIMITER);
			socket.close();
			if(receivedFlags[0].equals("-1"))
				return -1;
			return Integer.parseInt(receivedFlags[0]);
		}else{
			throw new Exception("Message exceeds maximum size.");
		}
	}

}
