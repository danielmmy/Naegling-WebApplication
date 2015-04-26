package br.com.naegling.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.com.naegling.domain.VirtualMachineHost;
import br.com.naegling.service.VirtualMachineHostService;
import br.com.naegling.service.exception.VirtualMachineHostNotFoundException;

@Controller
public class VirtualMachineHostController extends AbstractController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualMachineHostController.class);
	protected static final String ERROR_MESSAGE_KEY_DELETED_VIRTUAL_MACHINE_HOST_WAS_NOT_FOUND = "error.virtualMachineHost.message.deleted.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_VIRTUAL_MACHINE_HOST_WAS_NOT_FOUND = "error.virtualMachineHost.message.edited.not.found";
    
    protected static final String FEEDBACK_MESSAGE_KEY_VIRTUAL_MACHINE_HOST_CREATED = "feedback.message.virtualMachineHost.created";
    protected static final String FEEDBACK_MESSAGE_KEY_VIRTUAL_MACHINE_HOST_DELETED = "feedback.message.virtualMachineHost.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_VIRTUAL_MACHINE_HOST_EDITED = "feedback.message.virtualMachineHost.edited";
    
    protected static final String MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOST = "virtualMachineHost";
    protected static final String MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOSTS = "virtualMachineHosts";
    
    protected static final String VIRTUAL_MACHINE_HOST_ADD_FORM_VIEW = "virtualMachineHost/create";
    protected static final String VIRTUAL_MACHINE_HOST_EDIT_FORM_VIEW = "virtualMachineHost/edit";
    protected static final String VIRTUAL_MACHINE_HOST_LIST_VIEW = "virtualMachineHost/list";
	
	@Resource
    private VirtualMachineHostService virtualMachineHostService;

    /**
     * Processes delete virtualMachineHost requests.
     * @param id    The id of the deleted virtualMachineHost.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "virtualMachineHost/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        LOGGER.debug("Deleting virtualMachineHost with id: " + id);

        try {
            VirtualMachineHost deleted = virtualMachineHostService.delete(id);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_VIRTUAL_MACHINE_HOST_DELETED, deleted.getHostName());
        } catch (VirtualMachineHostNotFoundException e) {
            LOGGER.debug("No virtualMachineHost found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_DELETED_VIRTUAL_MACHINE_HOST_WAS_NOT_FOUND);
        }

        return createRedirectViewPath("/");
    }

    /**
     * Processes create virtualMachineHost requests.
     * @param model
     * @return  The name of the create virtualMachineHost form view.
     */
    @RequestMapping(value = "virtualMachineHost/create", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showCreateVirtualMachineHostForm(Model model) {
        LOGGER.debug("Rendering create virtualMachineHost form");
        
        model.addAttribute(MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOST, new VirtualMachineHost());

        return VIRTUAL_MACHINE_HOST_ADD_FORM_VIEW;
    }

    /**
     * Processes the submissions of create virtualMachineHost form.
     * @param created   The information of the created virtualMachineHosts.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "virtualMachineHost/create", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String submitCreateVirtualMachineHostForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOST) VirtualMachineHost created, BindingResult bindingResult, RedirectAttributes attributes) {
    	LOGGER.debug("Create virtualMachineHost form was submitted with information: " + created);

    	if (bindingResult.hasErrors()) {
    		return VIRTUAL_MACHINE_HOST_ADD_FORM_VIEW;
    	}
    	try{       
    		VirtualMachineHost virtualMachineHost = virtualMachineHostService.create(created);
    		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_VIRTUAL_MACHINE_HOST_CREATED, virtualMachineHost.getHostName());
    	}catch(JpaSystemException e) {    	
    		addErrorMessage(attributes, e.getMostSpecificCause().getMessage());

    	}

        return createRedirectViewPath("/");
    }
    

    /**
     * Processes edit virtualMachineHost requests.
     * @param id    The id of the edited virtualMachineHost.
     * @param model
     * @param attributes
     * @return  The name of the edit virtualMachineHost form view.
     */
    @RequestMapping(value = "virtualMachineHost/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showEditVirtualMachineHostForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit virtualMachineHost form for virtualMachineHost with id: " + id);
        
        VirtualMachineHost virtualMachineHost = virtualMachineHostService.findById(id);
        if (virtualMachineHost == null) {
            LOGGER.debug("No virtualMachineHost found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_VIRTUAL_MACHINE_HOST_WAS_NOT_FOUND);
            return createRedirectViewPath("virtualMachineHost/list");            
        }

        model.addAttribute(MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOST, virtualMachineHost);
        
        return VIRTUAL_MACHINE_HOST_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit virtualMachineHost form.
     * @param updated   The information of the edited virtualMachineHost.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "virtualMachineHost/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String submitEditVirtualMachineHostForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOST) VirtualMachineHost updated, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Edit virtualMachineHost form was submitted with information: " + updated);
        
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Edit virtualMachineHost form contains validation errors. Rendering form view.");
            return VIRTUAL_MACHINE_HOST_EDIT_FORM_VIEW;
        }
        
        try {
            VirtualMachineHost virtualMachineHost = virtualMachineHostService.update(updated);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_VIRTUAL_MACHINE_HOST_EDITED, virtualMachineHost.getHostName());
        } catch (VirtualMachineHostNotFoundException e) {
            LOGGER.debug("No virtualMachineHost was found with id: " + updated.getId());
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_VIRTUAL_MACHINE_HOST_WAS_NOT_FOUND);
        } catch (JpaSystemException e) {    	
        	addErrorMessage(attributes, e.getMostSpecificCause().getMessage());
		}
        
        return createRedirectViewPath("/");
    }
    

    /**
     * Processes requests to list virtualMachineHosts.
     * @param model
     * @return  The name of the virtualMachineHost list view.
     */
    @RequestMapping(value = "virtualMachineHost/list", method = RequestMethod.GET)
    public String showList(Model model) {
        LOGGER.debug("Rendering virtualMachineHost list page");

        List<VirtualMachineHost> virtualMachineHosts = virtualMachineHostService.findAll();
       
        model.addAttribute(MODEL_ATTRIBUTE_VIRTUAL_MACHINE_HOSTS, virtualMachineHosts);

        return VIRTUAL_MACHINE_HOST_LIST_VIEW;
    }


}
