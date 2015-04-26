package br.com.naegling.controller;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.naegling.domain.Template;
import br.com.naegling.service.TemplateService;
import br.com.naegling.service.exception.TemplateNotFoundException;
import br.com.naegling.tools.FileManipulationTool;


@Controller
public class TemplateController extends AbstractController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);
	protected static final String ERROR_MESSAGE_KEY_DELETED_TEMPLATE_WAS_NOT_FOUND = "error.template.message.deleted.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_TEMPLATE_WAS_NOT_FOUND = "error.template.message.edited.not.found";
    
    protected static final String FEEDBACK_MESSAGE_KEY_TEMPLATE_CREATED = "feedback.message.template.created";
    protected static final String FEEDBACK_MESSAGE_KEY_TEMPLATE_DELETED = "feedback.message.template.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_TEMPLATE_EDITED = "feedback.message.template.edited";
    
    protected static final String MODEL_ATTRIBUTE_TEMPLATE = "template";
    protected static final String MODEL_ATTRIBUTE_TEMPLATES = "templates";
    
    protected static final String TEMPLATE_ADD_FORM_VIEW = "template/create";
    protected static final String TEMPLATE_EDIT_FORM_VIEW = "template/edit";
    protected static final String TEMPLATE_LIST_VIEW = "template/list";
	
	@Resource
    private TemplateService templateService;

    /**
     * Processes delete template requests.
     * @param id    The id of the deleted template.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "template/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        LOGGER.debug("Deleting template with id: " + id);

        try {
            Template deleted = templateService.delete(id);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_TEMPLATE_DELETED, deleted.getName());
        } catch (TemplateNotFoundException e) {
            LOGGER.debug("No template found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_DELETED_TEMPLATE_WAS_NOT_FOUND);
        }

        return createRedirectViewPath("/");
    }

    /**
     * Processes create template requests.
     * @param model
     * @return  The name of the create template form view.
     */
    @RequestMapping(value = "template/create", method = RequestMethod.GET) 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showCreateTemplateForm(Model model) {
        LOGGER.debug("Rendering create template form");
        
        model.addAttribute(MODEL_ATTRIBUTE_TEMPLATE, new Template());

        return TEMPLATE_ADD_FORM_VIEW;
    }

    /**
     * Processes the submissions of create template form.
     * @param created   The information of the created templates.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "template/create", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String submitCreateTemplateForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_TEMPLATE) Template created, BindingResult bindingResult, RedirectAttributes attributes) {
    	LOGGER.debug("Create template form was submitted with information: " + created);

    	if (bindingResult.hasErrors()) {
    		return TEMPLATE_ADD_FORM_VIEW;
    	}
    	try{       
    		Template template = templateService.create(created);
    		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_TEMPLATE_CREATED, template.getName());
    	}catch(JpaSystemException e) {    	
    		addErrorMessage(attributes, e.getMostSpecificCause().getMessage());

    	}



        return createRedirectViewPath("/");
    }
    
    @RequestMapping(value = "template/create/compute", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody String submitCreateTemplateFormCompute(@RequestParam("filePath") String filePath){//,RedirectAttributes attributes) {
    	String md5Sum="";
    	try{       
    		md5Sum=FileManipulationTool.getMD5(filePath);
    	} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        return md5Sum;
    }

    /**
     * Processes edit template requests.
     * @param id    The id of the edited template.
     * @param model
     * @param attributes
     * @return  The name of the edit template form view.
     */
    @RequestMapping(value = "template/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showEditTemplateForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit template form for template with id: " + id);
        
        Template template = templateService.findById(id);
        if (template == null) {
            LOGGER.debug("No template found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_TEMPLATE_WAS_NOT_FOUND);
            return createRedirectViewPath("template/list");            
        }

        model.addAttribute(MODEL_ATTRIBUTE_TEMPLATE, template);
        
        return TEMPLATE_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit template form.
     * @param updated   The information of the edited template.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "template/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String submitEditTemplateForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_TEMPLATE) Template updated, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Edit template form was submitted with information: " + updated);
        
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Edit template form contains validation errors. Rendering form view.");
            return TEMPLATE_EDIT_FORM_VIEW;
        }
        
        try {
            Template template = templateService.update(updated);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_TEMPLATE_EDITED, template.getName());
        } catch (TemplateNotFoundException e) {
            LOGGER.debug("No template was found with id: " + updated.getId());
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_TEMPLATE_WAS_NOT_FOUND);
        } catch (JpaSystemException e) {    	
        	addErrorMessage(attributes, e.getMostSpecificCause().getMessage());
		}
        
        return createRedirectViewPath("/");
    }
    
    /**
     * Processes requests to list templates.
     * @param model
     * @return  The name of the template list view.
     */
    @RequestMapping(value = "template/list", method = RequestMethod.GET)
    public String showList(Model model) {
        LOGGER.debug("Rendering template list page");

        List<Template> templates = templateService.findAll();
       
        model.addAttribute(MODEL_ATTRIBUTE_TEMPLATES, templates);

        return TEMPLATE_LIST_VIEW;
    }

}
