package it.synclab.rilmanager.core.function;

import it.synclab.rilmanager.config.ConfigurationService;

public interface Function {
	
	boolean canManage(ConfigurationService configurationService);
	
	Object process(ConfigurationService configurationService) throws IsNotADirectoryException;
	
}
