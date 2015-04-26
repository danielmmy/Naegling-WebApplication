package br.com.naegling.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.controller.AccountController;
import br.com.naegling.domain.EntityBuilder.EntityBuilderManager;
import br.com.naegling.service.AccountService;
import br.com.naegling.service.RoleService;



/**
 * Sets up initial data so the application can be used straight away. The data setup is executed in a separate
 * transaction, and committed when the {@link #setupData()} method returns
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 * 
 */
public class InitialDataSetup {


    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private AccountService accountService;
    @Resource
    private RoleService roleService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
   



    private Role roleUser = new Role("ROLE_USER");
    private Role roleAdmin = new Role("ROLE_ADMIN");


    @Transactional
    public void initialize() {
        EntityBuilderManager.setEntityManager(this.entityManager);

                // Create admin account
                if(!dataIsAlreadyPresent()){
                	LOGGER.info("Creating admin account");
                	List<Role> roles = new ArrayList<>();
                	roles.add(roleService.create(roleAdmin));
                	roles.add(roleService.create(roleUser));
                	Account admin= new Account();
                	admin.setFirstName("Admin");
                	admin.setLastName("Root");
                	admin.setUserName("admin");
                	admin.setEmail("admin@naegling.com.br");
                	admin.setPasswd("adm123");
                	admin.setRoles(roles);
                	accountService.create(admin);

                }else{
                	LOGGER.info("admin account already exists.");
                }
                EntityBuilderManager.clearEntityManager();
            }

            private boolean dataIsAlreadyPresent() {	
                return InitialDataSetup.this.entityManager.createQuery("select count(a.id) from Account a Where a.userName='admin'", Long.class).getSingleResult().longValue() > 0;
            }
}