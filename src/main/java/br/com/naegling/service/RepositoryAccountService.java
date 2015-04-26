package br.com.naegling.service;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.Cluster;
import br.com.naegling.domain.Role;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.repository.AccountRepository;
import br.com.naegling.service.exception.AccountNotFoundException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

/**
 * This implementation of the AccountService interface communicates with
 * the database by using a Spring Data JPA repository.
 * @author Petri Kainulainen
 */
@Service
public class RepositoryAccountService implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryAccountService.class);

	@Resource
	private AccountRepository accountRepository;

	@PersistenceContext
	private EntityManager entityManager;

	//    @Override
	//	public Account login(String userName, String password) throws AuthenticationException {
	//		Account account = this.findByUserName(userName);
	//		if (account != null) {
	//			String pwd = DigestUtils.sha256Hex(password + "{" + userName + "}");
	//			if (!account.getPasswd().equalsIgnoreCase(pwd)) {
	//				throw new AuthenticationException("Wrong username/password combination.", "invalid.password");
	//			}
	//		} else {
	//			throw new AuthenticationException("Wrong username/password combination.", "invalid.username");
	//		}
	//
	//		return account;
	//	}

	@Transactional
	@Override
	public Account create(Account created) {
		LOGGER.debug("Creating a new account with information: " + created);

		Account account = Account.getBuilder(created.getFirstName(), created.getLastName(), created.getUserName(), created.getEmail() ,created.getPasswd(), created.getRoles(), created.getClusters()).build();

		return accountRepository.save(account);
	}


	@Transactional(rollbackFor = AccountNotFoundException.class)
	@Override
	public Account delete(Long accountId) throws AccountNotFoundException {
		LOGGER.debug("Deleting account with id: " + accountId);

		Account deleted = accountRepository.findOne(accountId);

		if (deleted == null) {
			LOGGER.debug("No account found with id: " + accountId);
			throw new AccountNotFoundException();
		}

		accountRepository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Account> findAll() {
		LOGGER.debug("Finding all accounts");
		return accountRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Account findById(Long id) {
		LOGGER.debug("Finding account by id: " + id);
		return accountRepository.findOne(id);
	}

	@Transactional(rollbackFor = AccountNotFoundException.class)
	@Override
	public Account update(Account updated) throws AccountNotFoundException , ConstraintViolationException{
		LOGGER.debug("Updating account with information: " + updated);

		Account account = accountRepository.findOne(updated.getId());

		if (account == null) {
			LOGGER.debug("No account found with id: " + updated.getId());
			throw new AccountNotFoundException();
		}
		account.update(updated.getFirstName(), updated.getLastName(), updated.getUserName(),updated.getEmail(), updated.getPasswd(),updated.getRoles());

		return account;
	}

	@Transactional(rollbackFor = AccountNotFoundException.class)
	@Override
	public Account addRole(Account updated, Role role) throws AccountNotFoundException , ConstraintViolationException{
		LOGGER.debug("Updating account with information: " + updated);

		Account account = accountRepository.findOne(updated.getId());

		if (account == null) {
			LOGGER.debug("No account found with id: " + updated.getId());
			throw new AccountNotFoundException();
		}
		account.addRole(role);

		return account;
	}

	@Transactional(rollbackFor = AccountNotFoundException.class)
	@Override
	public Account removeRole(Account updated, Role role) throws AccountNotFoundException{


		Account account = accountRepository.findOne(updated.getId());

		if (account == null) {
			LOGGER.debug("No account found with id: " + updated.getId());
			throw new AccountNotFoundException();
		}
		account.removeRole(role);

		return account;
	}

	@Transactional(rollbackFor = AccountNotFoundException.class)
	@Override
	public Account addCluster(Account updated, Cluster cluster) throws AccountNotFoundException , ConstraintViolationException{
		LOGGER.debug("Updating account with information: " + updated);

		Account account = accountRepository.findOne(updated.getId());

		if (account == null) {
			LOGGER.debug("No account found with id: " + updated.getId());
			throw new AccountNotFoundException();
		}
		account.addCluster(cluster);

		return account;
	}

	@Transactional(rollbackFor = AccountNotFoundException.class)
	@Override
	public Account removeCluster(Account updated, Cluster cluster) throws AccountNotFoundException{


		Account account = accountRepository.findOne(updated.getId());

		if (account == null) {
			LOGGER.debug("No account found with id: " + updated.getId());
			throw new AccountNotFoundException();
		}
		account.removeCluster(cluster);

		return account;
	}

	@Transactional(readOnly=true)
	@Override
	public Account findByUserName(String userName) {
		String hql = "select a from Account a where a.userName=:userName";
		TypedQuery<Account> query = entityManager.createQuery(hql, Account.class).setParameter("userName",
				userName);
		List<Account> accounts = query.getResultList();

		return accounts.size() == 1 ? accounts.get(0) : null;
	}

	@Transactional(readOnly=true)
	@Override
	public VirtualNode findAccountNodeByNodeId(String userName, Long nodeId){
		Account a=findByUserName(userName);
		return a.getNodeById(nodeId);
	}
}