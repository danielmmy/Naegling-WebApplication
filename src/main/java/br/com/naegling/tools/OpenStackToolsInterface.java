package br.com.naegling.tools;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.VirtualNode;

public interface OpenStackToolsInterface {

	
	
	/**
     * Creates a new OpenStack user.
     * @param created   The information of the created account.
     */
    public void createOpenStackUser(Account created);
    
    
    /**
     * Creates a OpenStack instance
     * @param account instance owner
     * @param node instance
     */
    public void createInstance(Account account, VirtualNode node);
    
    /**
     * Creates a OpenStack instance
     * @param account instance owner
     * @param node instance
     * @return 1 if started -1 if failed
     */
    public String startInstance(Account account, VirtualNode node);


    /**
     * Creates a OpenStack instance
     * @param account instance owner
     * @param node instance
     * @return 0 if stopped -1 if failed
     */
	public String stopInstance(Account account, VirtualNode node);
	
	/**
	 * Returns instance power status
	 * @param account
	 * @param node
	 * @return 0 if shutdown, 1 if running else returns -1 
	 */
	public int getInstanceStatus(Account account, VirtualNode node);

	/**
	 * Open novnc connection with OpenStack
	 * @param account
	 * @param node
	 * @return
	 */
	public String noVnc(Account account, VirtualNode node);

}
