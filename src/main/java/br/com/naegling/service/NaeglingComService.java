package br.com.naegling.service;

public interface NaeglingComService {
	public int sendMessageToHostname(String message,String hostname,String naeglingPort) throws Exception;

}
