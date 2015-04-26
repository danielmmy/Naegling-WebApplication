package br.com.naegling.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.Cluster;
import br.com.naegling.domain.Role;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.service.exception.AccountNotFoundException;


public interface AccountService {
	
	/**
     * Handles the login logic. If the {@link Account} can be retrieved and the password is correct we get the
     * {@link Account}. In all other cases we get a {@link AuthenticationException}.
     * @param username the username
     * @param password the password
     * @return the account
     * @throws AuthenticationException if account not found or incorrect password
     */
    //Account login(String username, String password) throws AuthenticationException;

    /**
     * Creates a new account.
     * @param created   The information of the created account.
     * @return  The created account.
     */
    public Account create(Account created);
    

    /**
     * Deletes a account.
     * @param accountId  The id of the deleted account.
     * @return  The deleted account.
     * @throws AccountNotFoundException  if no account is found with the given id.
     */
    public Account delete(Long accountId) throws AccountNotFoundException;

    /**
     * Finds all accounts.
     * @return  A list of accounts.
     */
    public List<Account> findAll();

    /**
     * Finds account by id.
     * @param id    The id of the wanted account.
     * @return  The found account. If no account is found, this method returns null.
     */
    public Account findById(Long id);

    /**
     * Updates the information of a account.
     * @param updated   The information of the updated account.
     * @return  The updated account.
     * @throws AccountNotFoundException  if no account is found with given id.
     */
    public Account update(Account updated) throws AccountNotFoundException;
    
    /**
     * Finds account by userName
     * @param userName
     * @return
     */
    public Account findByUserName(String userName);

	public Account addCluster(Account updated, Cluster cluster)
			throws AccountNotFoundException, ConstraintViolationException;

	public Account removeCluster(Account account, Cluster cluster) throws AccountNotFoundException;

	public Account addRole(Account updated, Role role)
			throws AccountNotFoundException, ConstraintViolationException;

	public Account removeRole(Account updated, Role role)
			throws AccountNotFoundException;

	public VirtualNode findAccountNodeByNodeId(String userName, Long nodeId);
}