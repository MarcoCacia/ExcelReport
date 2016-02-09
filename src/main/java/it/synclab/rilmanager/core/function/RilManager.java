package it.synclab.rilmanager.core.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.synclab.rilmanager.config.ConfigurationService;
import it.synclab.rilmanager.model.Ril;

public class RilManager implements Function {
	
	private static Logger logger = LoggerFactory.getLogger(RilManager.class);
	
	public boolean canManage(ConfigurationService configurationService) {
		return true;
	}
	
	public Object process(ConfigurationService configurationService) throws IsNotADirectoryException {
		
		logger.info("Start Directory: " + configurationService.getStartDir());
		logger.info("End Directory: " + configurationService.getEndDir());
		logger.info("Month to process: " + configurationService.getMonth());
		logger.info("Report file name: " + configurationService.getReportFileName());
		
		logger.info("Check Report directory...");
		File createDir = createDir(configurationService.getEndDir());
		File createDirPerMonth = createDir(createDir + File.separator + configurationService.getMonth());
		
		logger.info("Done!");
		
		logger.info("Check Rils to process...");
		String[] filenames = getRils(configurationService.getStartDir());
		Set<Ril> rils = new HashSet<Ril>();
		logger.info("Start processing...");
		for (String filename : filenames) {
			String absoluteRilPath = configurationService.getStartDir() + File.separator + filename;
			logger.info(absoluteRilPath);
			Ril ril = new Ril();
			try {
				ril.fillByFilename(absoluteRilPath);
				rils.add(ril);
			} catch (EncryptedDocumentException e) {
				logger.warn(" {} cannot be processed", absoluteRilPath, e);
			} catch (InvalidFormatException e) {
				logger.warn(" {} cannot be processed", absoluteRilPath, e);
			} catch (IOException e) {
				logger.warn(" {} cannot be processed", absoluteRilPath, e);
			}
		}
		
		return createReport(rils, createDirPerMonth + File.separator + configurationService.getReportFileName());
	}
	
	@SuppressWarnings("resource")
	private boolean createReport(Set<Ril> rils, String reportFileName) {
		boolean ret = true;
		logger.info("process: {}", reportFileName);
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Report");
		Row headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("DATA");
		headRow.createCell(1).setCellValue("NOME PERSONA");
		headRow.createCell(2).setCellValue("ORE LAVORATE");
		headRow.createCell(3).setCellValue("GG SOLARI");
		headRow.createCell(4).setCellValue("FERIE/PERMESSI");
		headRow.createCell(5).setCellValue("MALATTIA");
		headRow.createCell(6).setCellValue("FILE ELABORATO");
		headRow.createCell(7).setCellValue("ERRORE");
		
		int i = 0;
		for (Ril ril : rils) {
			i++;
			logger.info("RIL: {}", ril.getFilename());
			Row row = sheet.createRow((short) i);
			row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
			row.getCell(0).setCellValue(ril.getDateItFormat());
			row.createCell(1).setCellValue(ril.getName());
			row.createCell(2).setCellValue(ril.getWorkingHour());
			row.createCell(3).setCellValue(ril.getDays());
			row.createCell(4).setCellValue(ril.getHoliday());
			row.createCell(5).setCellValue(ril.getSickness());
			row.createCell(6).setCellValue(ril.getFilename());
			row.createCell(7).setCellValue(ril.getErrorMessage());
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(reportFileName);
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			logger.warn(" {} cannot be written", reportFileName, e);
			ret = false;
		} catch (IOException e) {
			logger.warn(" {} cannot be written", reportFileName, e);
			ret = false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ret;
		
	}
	
	private String[] getRils(String startDir) throws IsNotADirectoryException {
		File rilsDirectory = new File(startDir);
		if (!rilsDirectory.isDirectory())
			throw new IsNotADirectoryException(startDir + " is not a Directory!");
			
		return rilsDirectory.list();
	}
	
	public File createDir(String dir) throws IsNotADirectoryException {
		File directory = new File(dir);
		
		if (!directory.exists()) {
			directory.mkdir();
			return directory;
		}
		if (!directory.isDirectory())
			throw new IsNotADirectoryException(dir + " is not a Directory!");
			
		return directory;
		
	}
	
}
