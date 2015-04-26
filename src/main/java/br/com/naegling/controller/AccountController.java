package br.com.naegling.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.Role;
import br.com.naegling.service.AccountService;
import br.com.naegling.service.RoleService;
import br.com.naegling.service.exception.AccountNotFoundException;
import br.com.naegling.tools.OpenStackToolsInterface;

@Controller
public class AccountController extends AbstractController {
private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    
    protected static final String ERROR_MESSAGE_KEY_DELETED_ACCOUNT_WAS_NOT_FOUND = "error.account.message.deleted.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_ACCOUNT_WAS_NOT_FOUND = "error.account.message.edited.not.found";
    
    protected static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_CREATED = "feedback.account.message.created";
    protected static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_DELETED = "feedback.account.message.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_EDITED = "feedback.account.message.edited";
    
    protected static final String MODEL_ATTRIBUTE_EDIT_ACCOUNT = "editAccount";
    protected static final String MODEL_ATTRIBUTE_CREATE_ACCOUNT = "createAccount";
    protected static final String MODEL_ATTRIBUTE_ACCOUNTS = "accounts";
    
    protected static final String ACCOUNT_ADD_FORM_VIEW = "account/create";
    protected static final String ACCOUNT_EDIT_FORM_VIEW = "account/edit";
    protected static final String ACCOUNT_LIST_VIEW = "account/list";

    
    @Resource
    private AccountService accountService;
    
    @Resource
    private RoleService roleService;
    
    @Autowired
    private OpenStackToolsInterface openStackTools;
    

    /**
     * Processes delete account requests.
     * @param id    The id of the deleted account.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "account/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        LOGGER.debug("Deleting account with id: " + id);

        try {
            Account deleted = accountService.delete(id);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_DELETED, deleted.getName());
        } catch (AccountNotFoundException e) {
            LOGGER.debug("No account found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_DELETED_ACCOUNT_WAS_NOT_FOUND);
        }

        return createRedirectViewPath("/");
    }

    /**
     * Processes create account requests.
     * @param model
     * @return  The name of the create account form view.
     */
    @RequestMapping(value = "account/create", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showCreateAccountForm(Model model) {
        LOGGER.debug("Rendering create account form");
        
        model.addAttribute(MODEL_ATTRIBUTE_CREATE_ACCOUNT, new Account());

        return ACCOUNT_ADD_FORM_VIEW;
    }

    /**
     * Processes the submissions of create account form.
     * @param created   The information of the created accounts.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "account/create", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String submitCreateAccountForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CREATE_ACCOUNT) Account created, BindingResult bindingResult, RedirectAttributes attributes) {
    	LOGGER.debug("Create account form was submitted with information: " + created);

    	if (bindingResult.hasErrors()) {
    		return ACCOUNT_ADD_FORM_VIEW;
    	}
    	try{       
    		Account account = accountService.create(created);
    		openStackTools.createOpenStackUser(account);
    		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_CREATED, account.getName());
    	}catch(JpaSystemException e) {    	
    		addErrorMessage(attributes, e.getMostSpecificCause().getMessage());

    	}



        return createRedirectViewPath("/");
    }
    
    
    

    /**
     * Processes edit account requests.
     * @param id    The id of the edited account.
     * @param model
     * @param attributes
     * @return  The name of the edit account form view.
     */
    @RequestMapping(value = "account/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showEditAccountForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit account form for account with id: " + id);
        
        Account account = accountService.findById(id);
        if (account == null) {
            LOGGER.debug("No account found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_ACCOUNT_WAS_NOT_FOUND);
            return "account/list";            
        }

        model.addAttribute(MODEL_ATTRIBUTE_EDIT_ACCOUNT, account);
        
        return ACCOUNT_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit account form.
     * @param updated   The information of the edited account.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "account/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String submitEditAccountForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_EDIT_ACCOUNT) Account updated, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Edit account form was submitted with information: " + updated);
        
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Edit account form contains validation errors. Rendering form view.");
            return ACCOUNT_EDIT_FORM_VIEW;
        }
        
        try {
        	/*FIXME 
        	 *  Due to bad ORM mapping or repository service implementation, it is necessary to create new roles for accounts instead of creating only the relation accounts_roles
        	 */
        	if(updated.getRoles()!=null){
        	List<Role> createdRoles=new ArrayList<>();
        	for(Role r: updated.getRoles()){
        		LOGGER.debug("Role: "+r.getRole()+" id: "+r.getId());
        		createdRoles.add(roleService.create(r));
        	}
        	updated.setRoles(createdRoles);
        	}
            Account account = accountService.update(updated);

            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_EDITED, account.getName());
        } catch (AccountNotFoundException e) {
            LOGGER.debug("No account was found with id: " + updated.getId());
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_ACCOUNT_WAS_NOT_FOUND);
        } catch (JpaSystemException e) {    	
        	addErrorMessage(attributes, e.getMostSpecificCause().getMessage());
		}
        
        return createRedirectViewPath("/");
    }
    

    /**
     * Processes requests to list accounts.
     * @param model
     * @return  The name of the account list view.
     */
    @RequestMapping(value = "account/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showList(Model model,HttpSession session) {
        LOGGER.debug("Rendering account list page");
        

        List<Account> accounts = accountService.findAll();
       
        model.addAttribute(MODEL_ATTRIBUTE_ACCOUNTS, accounts);

        return ACCOUNT_LIST_VIEW;
    }

}