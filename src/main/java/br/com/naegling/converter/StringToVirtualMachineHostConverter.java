package br.com.naegling.converter;

import javax.annotation.Resource;

import org.springframework.core.convert.converter.Converter;

import br.com.naegling.domain.VirtualMachineHost;
import br.com.naegling.service.VirtualMachineHostService;

public class StringToVirtualMachineHostConverter implements Converter<String, VirtualMachineHost> {
	@Resource
	private VirtualMachineHostService virtualMachineHostService;

	@Override
	public VirtualMachineHost convert(String hostName) {
		return virtualMachineHostService.findByHostName(hostName);		
	}

}
