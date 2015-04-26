package br.com.naegling.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.RequestContext;

import br.com.naegling.security.SecurityContextSupport;
import br.com.naegling.service.VirtualMachineHostService;
import br.com.naegling.service.VirtualNodeService;
import br.com.naegling.service.exception.VirtualNodeNotFoundException;



@Controller
public class EditNodeController {
	@Resource
	private VirtualMachineHostService virtualMachineHostService;
	
	@Resource
	private VirtualNodeService virtualNodeService;
	
	public EditNodeForm initializeForm(RequestContext requestContext) {
		EditNodeForm editNodeForm = new EditNodeForm();
		editNodeForm.setHosts(virtualMachineHostService.findAll());
		editNodeForm.setClusterId(requestContext.getRequestParameters().getLong("clusterId"));
		editNodeForm.setVirtualNode(SecurityContextSupport.getUserDetails().getAccount().getClusterById(editNodeForm.getClusterId()).getNodeById(requestContext.getRequestParameters().getLong("nodeId")));
		return editNodeForm;
	}
	
	public void editNode(EditNodeForm editNodeForm){
		try {
			virtualNodeService.update(editNodeForm.getVirtualNode());
			SecurityContextSupport.getUserDetails().getAccount().getClusterById(editNodeForm.getClusterId()).updateNode(editNodeForm.getVirtualNode());
		} catch (VirtualNodeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
