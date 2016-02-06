package it.synclab.rilmanager.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.kohsuke.args4j.CmdLineParser;

import it.synclab.rilmanager.utils.FileUtils;

public class UsageHelper {
	
	private final PrintWriter printWriter;
	private final CmdLineParser cmdLineParser;
	
	public UsageHelper(CmdLineParser cmdLineParser) {
		this.cmdLineParser = cmdLineParser;
		this.printWriter = new PrintWriter(System.out);
	}
	
	public UsageHelper(PrintWriter printWriter, CmdLineParser cmdLineParser) {
		this.printWriter = printWriter;
		this.cmdLineParser = cmdLineParser;
	}
	
	public void printUsage() {
		String version = "null";
		
		try {
			version = FileUtils.convertStreamToString(ClassLoader.getSystemResourceAsStream("version.txt"));
		} catch (IOException e) {
			printError(e);
		}
		
		printWriter.println("Ril Manager - ver. " + version);
		printWriter.println("Utility to process Rils.");
		printWriter.println("Usage: java -jar JARFILE option");
		printWriter.flush();
		cmdLineParser.printUsage(printWriter, null);
		
	}
	
	public void printError(Exception e) {
		e.printStackTrace(printWriter);
		printWriter.flush();
	}
	
	public void close() {
		printWriter.close();
	}
	
}
