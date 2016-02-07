package it.synclab.rilmanager.config;

import java.io.File;
import java.io.FileNotFoundException;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class ConfigurationService {
	@Option(name = "-rmc", aliases = "--config", usage = "Ril manager configuration file. Required.", metaVar = "CONFIGFILE")
	private File configFile = null;
	
	@Option(name = "-h", aliases = "--help", usage = "Show this help.")
	private Boolean help;
	
	@Option(name = "-sd", aliases = "--startdir", usage = "Dirctory Rils to process.")
	private String startDir = null;
	
	@Option(name = "-ed", aliases = "--enddir", usage = "Dirctory to put report.")
	private String endDir = null;
	
	@Option(name = "-m", aliases = "--month", usage = "Month to processs.")
	private String month = null;
	
	@Option(name = "-rfn", aliases = "--reportfilename", usage = "File name of report.")
	private String reportFileName = null;
	
	public boolean configWithArgument(String[] args) throws ConfigurationException {
		CmdLineParser parser = new CmdLineParser(this);
		
		try {
			
			parser.parseArgument(args);
			
			if (help != null && help) {
				UsageHelper usageHelper = new UsageHelper(parser);
				usageHelper.printUsage();
				return false;
			}
			
			if (configFile != null) {
				configWith(configFile);
				return true;
			}
			if (startDir == null)
				throw new ConfigurationException("Option \"-sd (--startdir)\" is required");
				
			if (endDir == null)
				throw new ConfigurationException("Option \"-ed (--enddir)\" is required");
				
			if (month == null)
				throw new ConfigurationException("Option \"-m (--month)\" is required");
				
			if (reportFileName == null)
				reportFileName = "report_" + month + ".xls";
				
			return true;
			
		} catch (Exception e) {
			throw new ConfigurationException("Configuration is not valid: " + e.getMessage(), e);
			
		}
	}
	
	public void configWith(File filename) throws ConfigurationException, FileNotFoundException {
	
	}
	
	public File getConfigFile() {
		return configFile;
	}
	
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	
	public Boolean getHelp() {
		return help;
	}
	
	public void setHelp(Boolean help) {
		this.help = help;
	}
	
	public String getStartDir() {
		return startDir;
	}
	
	public void setStartDir(String startDir) {
		this.startDir = startDir;
	}
	
	public String getEndDir() {
		return endDir;
	}
	
	public void setEndDir(String endDir) {
		this.endDir = endDir;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getReportFileName() {
		return reportFileName;
	}
	
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	
}
