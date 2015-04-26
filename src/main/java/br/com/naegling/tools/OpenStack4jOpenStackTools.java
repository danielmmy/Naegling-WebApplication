package br.com.naegling.tools;

import java.util.List;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.VNCConsole;
import org.openstack4j.model.identity.Tenant;
import org.openstack4j.model.identity.User;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.service.RepositoryAccountService;

@Service
public class OpenStack4jOpenStackTools implements OpenStackToolsInterface{
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryAccountService.class);
	protected static final String endpoint="http://146.134.234.218:5000/v2.0";
	protected static final String openStackAdmin="naegling";
	protected static final String openStackAdminPasswd="adm123";
	protected static final String openStackTenant="naegling";

	

	
	public void createOpenStackUser(Account created) {
		LOGGER.debug("Creating a new openstack user with information: " + created);
		OSClient os =  OSFactory.builder()
                .endpoint(endpoint)
                .credentials(openStackAdmin,openStackAdminPasswd)
                .tenantName(openStackTenant)
                .authenticate();
		Tenant tenant = os.identity().tenants().create(Builders.tenant().name(created.getUserName()).description(created.getUserName()+" Tenant").build());
		User user=os.identity().users().create(Builders.user().name(created.getUserName()).password(created.getPasswd()).email(created.getEmail()).tenant(tenant).build());
		user=os.identity().users().enableUser(user.getId(), true);
		LOGGER.debug("User "+user.getName() +" created on OpenStack" );
		
	}

	@Override
	public void createInstance(Account account, VirtualNode node) {
		OSClient os =  OSFactory.builder()
                .endpoint(endpoint)
                .credentials(account.getUserName(),account.getPasswd())
                .tenantName(account.getUserName())
                .authenticate();
		ServerCreate sc = Builders.server().name(node.getDomain()).flavor("2").image("cf5827f4-82d8-4851-8437-801fb54ce9b5").build();
		Server server = os.compute().servers().boot(sc);
		LOGGER.debug("Instance "+server.getName() +" created on OpenStack" );
		
	}
	
	@Override
	public String startInstance(Account account, VirtualNode node){
		OSClient os =  OSFactory.builder()
                .endpoint(endpoint)
                .credentials(account.getUserName(),account.getPasswd())
                .tenantName(account.getUserName())
                .authenticate();
		List<? extends Server> servers=os.compute().servers().list(false);//fill only name, id and links
		Server server=null;
		for (Server s : servers){
			if (s.getName().equals(node.getDomain()))
					server=s;
		}
		if(server!=null){
			ActionResponse ar=os.compute().servers().action(server.getId(),Action.START );
			if (ar.toString().contains("success"))
				return "1";
		}
		return "-1";
	}
	
	@Override
	public String stopInstance(Account account, VirtualNode node){
		OSClient os =  OSFactory.builder()
                .endpoint(endpoint)
                .credentials(account.getUserName(),account.getPasswd())
                .tenantName(account.getUserName())
                .authenticate();
		List<? extends Server> servers=os.compute().servers().list(false);//fill only name, id and links
		Server server=null;
		for (Server s : servers){
			if (s.getName().equals(node.getDomain()))
					server=s;
		}
		if(server!=null){
			ActionResponse ar=os.compute().servers().action(server.getId(),Action.STOP );
			if (ar.toString().contains("success"))
				return "0";
			
		}
		return "-1";
	}
	
	@Override
	public int getInstanceStatus(Account account, VirtualNode node){
		OSClient os =  OSFactory.builder()
                .endpoint(endpoint)
                .credentials(account.getUserName(),account.getPasswd())
                .tenantName(account.getUserName())
                .authenticate();
		List<? extends Server> servers=os.compute().servers().list();
		Server server=null;
		for (Server s : servers){
			if (s.getName().equals(node.getDomain()))
					server=s;
		}
		if(server!=null){
			String state=server.getVmState();
			if(state.equalsIgnoreCase("stopped"))
				return 0;
			if(state.equalsIgnoreCase("active"))
				return 1;
			
		}
		return -1;
	}

	@Override
	public String noVnc(Account account, VirtualNode node) {
		
		OSClient os =  OSFactory.builder()
                .endpoint(endpoint)
                .credentials(account.getUserName(),account.getPasswd())
                .tenantName(account.getUserName())
                .authenticate();
		List<? extends Server> servers=os.compute().servers().list();
		Server server=null;
		for (Server s : servers){
			if (s.getName().equals(node.getDomain()))
					server=s;
		}
		if(server!=null){
			VNCConsole vnc=os.compute().servers().getVNCConsole(server.getId(), VNCConsole.Type.NOVNC);
			LOGGER.debug(vnc.getURL());
			return vnc.getURL();
		}
		return null;
	}
	

}
