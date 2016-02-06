package it.synclab.rilmanager.core;

import java.util.ArrayList;
import java.util.List;

import it.synclab.rilmanager.config.ConfigurationService;
import it.synclab.rilmanager.core.function.Function;
import it.synclab.rilmanager.core.function.IsNotADirectoryException;
import it.synclab.rilmanager.core.function.RilManager;

public class Main {
	
	private List<Function> functions;
	
	public Main() {
		functions = new ArrayList<Function>();
		functions.add(new RilManager());
	}
	
	public Object run(ConfigurationService configurationService) throws IsNotADirectoryException {
		
		for (Function function : functions)
			if (function.canManage(configurationService))
				return function.process(configurationService);
				
		return null;
	}
	
}
