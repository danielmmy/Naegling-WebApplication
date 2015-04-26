package br.com.naegling.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.Cluster;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.security.SecurityContextSupport;
import br.com.naegling.service.AccountService;
import br.com.naegling.service.ClusterService;
import br.com.naegling.service.NaeglingComService;
import br.com.naegling.service.exception.AccountNotFoundException;
import br.com.naegling.service.exception.ClusterNotFoundException;
import br.com.naegling.tools.OpenStackToolsInterface;

@Controller
public class ClusterController extends AbstractController{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterController.class);
	protected static final String ERROR_MESSAGE_KEY_DELETED_CLUSTER_WAS_NOT_FOUND = "error.cluster.message.deleted.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_CLUSTER_WAS_NOT_FOUND = "error.cluster.message.edited.not.found";
    
    protected static final String FEEDBACK_MESSAGE_KEY_CLUSTER_CREATED = "feedback.message.cluster.created";
    protected static final String FEEDBACK_MESSAGE_KEY_CLUSTER_DELETED = "feedback.message.cluster.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_CLUSTER_EDITED = "feedback.message.cluster.edited";
    
    protected static final String MODEL_ATTRIBUTE_CLUSTER = "cluster";
    protected static final String MODEL_ATTRIBUTE_CLUSTERS = "clusters";
    
    protected static final String CLUSTER_ADD_FORM_VIEW = "cluster/create";
    protected static final String CLUSTER_EDIT_FORM_VIEW = "cluster/edit";
    protected static final String CLUSTER_LIST_VIEW = "cluster/list";
    protected static final String CLUSTER_DETAILS_FORM_VIEW = "cluster/details";
	
	@Resource
    private ClusterService clusterService;
	@Resource
	private AccountService accountService;
	
	@Autowired
	private NaeglingComService naeglingComService;
	
	@Autowired
	private OpenStackToolsInterface openStackTools;

    /**
     * Processes delete cluster requests.
     * @param id    The id of the deleted cluster.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "cluster/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes, HttpServletRequest request) {
        LOGGER.debug("Deleting cluster with id: " + id);

        try {
        	Cluster deleted=SecurityContextSupport.getUserDetails().getAccount().removeClusterById(id);
            deleted = clusterService.findById(deleted.getId());
    		Account updated=SecurityContextSupport.getUserDetails().getAccount();
    		
    		accountService.removeCluster(updated, deleted);
    		clusterService.delete(deleted.getId());
    		
        }catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClusterNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return createRedirectViewPath("/");
    }

    /**
     * Processes create cluster requests.
     * @param model
     * @return  The name of the create cluster form view.
     */
    @RequestMapping(value = "cluster/create", method = RequestMethod.GET) 
    public String showCreateClusterForm(Model model) {
        LOGGER.debug("Rendering create cluster form");
        
        model.addAttribute(MODEL_ATTRIBUTE_CLUSTER, new Cluster());

        return CLUSTER_ADD_FORM_VIEW;
    }

    /**
     * Processes the submissions of create cluster form.
     * @param created   The information of the created clusters.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "cluster/create", method = RequestMethod.POST)
    public String submitCreateClusterForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CLUSTER) Cluster created, BindingResult bindingResult, RedirectAttributes attributes, HttpServletRequest request) {
    	if (bindingResult.hasErrors()) {
    		return CLUSTER_ADD_FORM_VIEW;
    	}
    	try{
    		Account updated=SecurityContextSupport.getUserDetails().getAccount();
    		created.setAccount(updated);
    		updated=accountService.addCluster(updated, created);
    		//FIXME Need to update logged in user information
    		SecurityContextSupport.getUserDetails().setAccount(updated);
    	}catch(JpaSystemException e) {    	
    		addErrorMessage(attributes, e.getMostSpecificCause().getMessage());

    	} catch (AccountNotFoundException e) {
    		addErrorMessage(attributes, e.getMessage());
		}

        return createRedirectViewPath("/");
    }
    

    /**
     * Processes edit cluster requests.
     * @param id    The id of the edited cluster.
     * @param model
     * @param attributes
     * @return  The name of the edit cluster form view.
     */
    @RequestMapping(value = "cluster/edit/{id}", method = RequestMethod.GET)
    public String showEditClusterForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit cluster form for cluster with id: " + id);
        
        Cluster cluster = clusterService.findById(id);
        if (cluster == null) {
            LOGGER.debug("No cluster found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_CLUSTER_WAS_NOT_FOUND);
            return createRedirectViewPath("cluster/list");            
        }

        model.addAttribute(MODEL_ATTRIBUTE_CLUSTER, cluster);
        
        return CLUSTER_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit cluster form.
     * @param updated   The information of the edited cluster.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "cluster/edit", method = RequestMethod.POST)
    public String submitEditClusterForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CLUSTER) Cluster updated, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Edit cluster form was submitted with information: " + updated);
        
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Edit cluster form contains validation errors. Rendering form view.");
            return CLUSTER_EDIT_FORM_VIEW;
        }
        
        try {
            Cluster cluster = clusterService.update(updated);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CLUSTER_EDITED, cluster.getName());
            SecurityContextSupport.getUserDetails().getAccount().updateCluster(updated);
        } catch (ClusterNotFoundException e) {
            LOGGER.debug("No cluster was found with id: " + updated.getId());
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_CLUSTER_WAS_NOT_FOUND);
        } catch (JpaSystemException e) {    	
        	addErrorMessage(attributes, e.getMostSpecificCause().getMessage());
		}
        
        return createRedirectViewPath("/");
    }

    /**
     * Processes requests to list clusters.
     * @param model
     * @return  The name of the cluster list view.
     */
    @RequestMapping(value = "cluster/list", method = RequestMethod.GET)
    public String showList(Model model) {
        LOGGER.debug("Rendering cluster list page");

        List<Cluster> clusters = clusterService.findAll();
       
        model.addAttribute(MODEL_ATTRIBUTE_CLUSTERS, clusters);

        return CLUSTER_LIST_VIEW;
    }
    
    /**
     * Processes cluster details requests.
     * @param id    The id of the edited cluster.
     * @param model
     * @param attributes
     * @return  The name of the edit cluster form view.
     */
    @RequestMapping(value = "cluster/details/{id}", method = RequestMethod.GET)
    public String showClusterDetailForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit cluster form for cluster with id: " + id);
        
        Cluster cluster = SecurityContextSupport.getUserDetails().getAccount().getClusterById(id);
        if (cluster == null) {
            LOGGER.debug("No cluster found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_CLUSTER_WAS_NOT_FOUND);
            return createRedirectViewPath("cluster/list");            
        }
        
        for(VirtualNode n: cluster.getNodes()){
/*        	String message=NaeglingComType.NODE_STATUS.getValue()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					n.getDomain()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					n.getHost().getHypervisor();
        	try {
        		int retval=naeglingComService.sendMessageToHostname(message, n.getHost().getHostName(), n.getHost().getNaeglingPort());
				n.setStatus(retval);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        	int retval=openStackTools.getInstanceStatus(SecurityContextSupport.getUserDetails().getAccount(), n);
        	n.setStatus(retval);
        }

        model.addAttribute(MODEL_ATTRIBUTE_CLUSTER, cluster);
        
        return CLUSTER_DETAILS_FORM_VIEW;
    }


}
