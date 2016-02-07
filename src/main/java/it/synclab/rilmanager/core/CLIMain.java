package it.synclab.rilmanager.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.synclab.rilmanager.config.ConfigurationException;
import it.synclab.rilmanager.config.ConfigurationService;

public class CLIMain {
	private static Logger logger = LoggerFactory.getLogger(CLIMain.class);
	
	private CLIMain() {
	}
	
	public static void main(String[] args) throws Exception {
		
		try {
			
			ConfigurationService configurationService = new ConfigurationService();
			if (!configurationService.configWithArgument(args))
				System.exit(0);
				
			Object ret = new Main().run(configurationService);
			
			if (ret != null)
				System.out.println(ret);
				
			System.exit(0);
			
		} catch (ConfigurationException e) {
			logger.error("Error configuring build: {}", e.getMessage());
			
		}
		
		System.exit(1);
		
	}
	
}
