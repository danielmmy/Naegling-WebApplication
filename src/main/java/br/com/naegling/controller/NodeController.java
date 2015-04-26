package br.com.naegling.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.naegling.domain.Cluster;
import br.com.naegling.domain.VirtualMachineHost;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.security.SecurityContextSupport;
import br.com.naegling.service.ClusterService;
import br.com.naegling.service.NaeglingComService;
import br.com.naegling.service.VirtualMachineHostService;
import br.com.naegling.service.VirtualNodeService;
import br.com.naegling.service.exception.ClusterNotFoundException;
import br.com.naegling.service.exception.VirtualNodeNotFoundException;
import br.com.naegling.tools.FileManipulationTool;
import br.com.naegling.tools.OpenStackToolsInterface;

@Controller
public class NodeController extends AbstractController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeController.class);
	protected static final String ERROR_MESSAGE_KEY_DELETED_NODE_WAS_NOT_FOUND = "error.virtualNode.message.deleted.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_NODE_WAS_NOT_FOUND = "error.virtualNode.message.edited.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_CLUSTER_WAS_NOT_FOUND = "error.cluster.message.edited.not.found";
    
    protected static final String FEEDBACK_MESSAGE_KEY_NODE_CREATED = "feedback.message.virtualNode.created";
    protected static final String FEEDBACK_MESSAGE_KEY_NODE_DELETED = "feedback.message.virtualNode.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_NODE_EDITED = "feedback.message.virtualNode.edited";
    
    protected static final String MODEL_ATTRIBUTE_NODE = "virtualNode";
    protected static final String MODEL_ATTRIBUTE_CLUSTER = "cluster";
    protected static final String MODEL_ATTRIBUTE_HOSTS = "hosts";
    protected static final String NODE_ADD_FORM_VIEW = "node/create";
    protected static final String NODE_EDIT_FORM_VIEW = "node/edit";
    protected static final String NODE_LIST_VIEW = "node/list";
    
    

	
	@Resource
    private VirtualNodeService virtualNodeService;
	
	@Resource
	private ClusterService clusterService;
	
	@Resource
	private VirtualMachineHostService virtualMachineHostService;
	
	@Autowired
	private NaeglingComService naeglingComService;
	
	@Autowired
	private OpenStackToolsInterface openStackTools;

    /**
     * Processes delete virtualNode requests.
     * @param id    The id of the deleted virtualNode.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "node/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody String delete(@RequestParam("clusterId") Long clusterId,@RequestParam("nodeId") Long nodeId) {
        LOGGER.debug("Deleting virtualNode with id: " + nodeId+" from cluster: "+clusterId);
        VirtualNode node=(VirtualNode) SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).removeNodeById(nodeId);
        try {
			virtualNodeService.delete(node.getId());
		} catch (VirtualNodeNotFoundException e) {
			e.printStackTrace();
		}

       
       return "cluster/details/"+clusterId;
    }

    /**
     * Processes create virtualNode requests.
     * @param model
     * @return  The name of the create virtualNode form view.
     * @throws ClusterNotFoundException 
     */
    @RequestMapping(value = "node/create/{id}", method = RequestMethod.GET) 
    @PreAuthorize("hasRole('ROLE_USER')")
    public String showCreateVirtualNodeForm(@PathVariable("id") Long id,Model model,RedirectAttributes attributes) throws ClusterNotFoundException {
    	Cluster cluster=SecurityContextSupport.getUserDetails().getAccount().getClusterById(id);
    	if(cluster==null){
    		LOGGER.debug("No cluster found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_CLUSTER_WAS_NOT_FOUND);
            return createRedirectViewPath("cluster/list");
    	}
    	List<VirtualMachineHost> hosts=virtualMachineHostService.findAll();
        LOGGER.debug("Rendering create node form");
        
        VirtualNode node= new VirtualNode();
        node.setMacs(new ArrayList<String>());
        node.setMac(FileManipulationTool.genMac(), 0);
        
        model.addAttribute(MODEL_ATTRIBUTE_NODE,node);
        model.addAttribute(MODEL_ATTRIBUTE_CLUSTER,cluster);
        model.addAttribute(MODEL_ATTRIBUTE_HOSTS,hosts);

        return NODE_ADD_FORM_VIEW;
    }

    /**
     * Processes the submissions of create virtualNode form.
     * @param created   The information of the created virtualNodes.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "node/create", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String submitCreateVirtualNodeForm(@Valid @ModelAttribute(MODEL_ATTRIBUTE_NODE) VirtualNode created, @RequestParam("clusterId") Long clusterId,BindingResult bindingResult, RedirectAttributes attributes) {
    	if (bindingResult.hasErrors()) {
    		return NODE_ADD_FORM_VIEW;
    	}
    	try{
    		Cluster cluster=SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId);
    		if(cluster==null){
    			LOGGER.debug("No Cluster found with id: " + clusterId);
                addErrorMessage(attributes, "Cluster not found");
                return createRedirectViewPath("/");     
    		}

    		/*String message=NaeglingComType.CREATE_SLAVE_NODE.getValue()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getDomain()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getUuid()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getMac(MacType.BRIDGE.ordinal())+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getHost().getBridgeNetworkInterface()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getHost().getHypervisor()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getRamMemory()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getCpuQuantity()+
					NaeglingComServiceImp.MESSAGE_DELIMITER+
					created.getGraphicalAccessPort();
			
			int retval=naeglingComService.sendMessageToHostname(message, created.getHost().getHostName(), created.getHost().getNaeglingPort());
			if(retval!=0){
				LOGGER.debug("Error creating node: " + created.getDomain());
                addErrorMessage(attributes, "Node not created");
                return createRedirectViewPath("/");  
			}*/
    		openStackTools.createInstance(SecurityContextSupport.getUserDetails().getAccount(), created);
    		clusterService.addNode(cluster, created);
    		
    		//FIXME Needed to add node at runtime
    		SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).addNode(created);
    	}catch(JpaSystemException e) {    	
    		addErrorMessage(attributes, e.getMostSpecificCause().getMessage());

    	} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClusterNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


        return createRedirectViewPath("/");
    }



    
    @RequestMapping(value = "node/create/generateMac", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody String createNodeFormGenerateMac(){//,RedirectAttributes attributes) {
    	return FileManipulationTool.genMac();
    }
    
    @RequestMapping(value = "node/create/generateUuid", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody String createNodeFormGenerateUuid(){//,RedirectAttributes attributes) {
    	return FileManipulationTool.genUuid();
    }
    
    @RequestMapping(value = "node/start", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody String startNode(@RequestParam("clusterId") Long clusterId, @RequestParam("nodeId") Long nodeId){
    	VirtualNode node=SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).getNodeById(nodeId);
    	
    	/*String message=NaeglingComType.START_NODE.getValue()+
				NaeglingComServiceImp.MESSAGE_DELIMITER+
				node.getDomain()+
				NaeglingComServiceImp.MESSAGE_DELIMITER+
				node.getHost().getHypervisor();
    	int retval=-1;
		
		try {
			retval=naeglingComService.sendMessageToHostname(message, node.getHost().getHostName(), node.getHost().getNaeglingPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(retval==0){
			SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).getNodeById(nodeId).setStatus(1);
			return "1";
		}else{
			SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).getNodeById(nodeId).setStatus(-1);
			return "-1";
		}    	*/
    	return openStackTools.startInstance(SecurityContextSupport.getUserDetails().getAccount(), node);
    	
    }
    
    @RequestMapping(value = "node/stop", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody String stopNode(@RequestParam("clusterId") Long clusterId, @RequestParam("nodeId") Long nodeId){
    	VirtualNode node=SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).getNodeById(nodeId);
    	
 /*   	String message=NaeglingComType.STOP_NODE.getValue()+
				NaeglingComServiceImp.MESSAGE_DELIMITER+
				node.getDomain()+
				NaeglingComServiceImp.MESSAGE_DELIMITER+
				node.getHost().getHypervisor();
    	int retval=-1;
		
		try {
			retval=naeglingComService.sendMessageToHostname(message, node.getHost().getHostName(), node.getHost().getNaeglingPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(retval==0){
			SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).getNodeById(nodeId).setStatus(retval);
			return "0";
		}else{
			SecurityContextSupport.getUserDetails().getAccount().getClusterById(clusterId).getNodeById(nodeId).setStatus(retval);
			return "-1";
		}*/    	
    	return openStackTools.stopInstance(SecurityContextSupport.getUserDetails().getAccount(), node);
    }

    @RequestMapping(value = "node/novnc/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView noVnc(@PathVariable("id") Long id,Model model,RedirectAttributes attributes){
    	VirtualNode node=SecurityContextSupport.getUserDetails().getAccount().getNodeById(id);
    	
    	return new ModelAndView("redirect:"+openStackTools.noVnc(SecurityContextSupport.getUserDetails().getAccount(), node));
    }
 
    
}