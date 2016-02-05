package it.synclab.rilexport.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import it.synclab.rilexport.core.Ril;

public class Main {
	/*
	 * COSA SERVE:
	 * 
	 * Avrei bisogno di una applicazione che lanciata (doppio click) va a
	 * leggere tutti gli excel presenti nella cartella Windows (quindi i RIL) e
	 * crea un report con questi campi:
	 * 
	 * DATA -- NOME PERSONA -- ORE LAVORATE - GG SOLARI - FERIE/PERMESSI
	 * 
	 * Su excel sono rispettivamente i campi:
	 * 
	 * � Prospetto Presenze � D6 (Data)
	 * 
	 * � Prospetto Presenze - E2 (nome)
	 * 
	 * � Prospetto Presenza I45 (ore lavorate)
	 * 
	 * � Prospetto Presenza I41 (gg solari lavorate)
	 * 
	 * � MODULO C E19 (Ferie) è in un altro sheet
	 * 
	 * Il massimo (ma se non ci vuole molto tempo altrimenti lasciamo perdere)
	 * sarebbe anche andare a recuperare il totale di giorni di malattia, o
	 * conteggiando le M nella colonna I o andando a contare il numero di
	 * istanze della parola malattia nello sheet MODULO C nella matrice
	 * (C27:H42).
	 */

	public static File createDir(String dir) throws Exception {
		File directory = new File(dir);
		try {
			if (!directory.exists()) {
				directory.mkdir();
				return directory;
			}
			if (!directory.isDirectory())
				throw new Exception();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directory;

	}

	public static void createReport(String dir, String report) {

		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet("Dati");
		Row rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("DATA");
		rowhead.createCell(1).setCellValue("NOME PERSONA");
		rowhead.createCell(2).setCellValue("ORE LAVORATE");
		rowhead.createCell(3).setCellValue("GG SOLARI");
		rowhead.createCell(4).setCellValue("FERIE/PERMESSI");
		rowhead.createCell(5).setCellValue("MALATTIA");

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(dir + report));
			workbook.write(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		System.out.println("Nuovo " + report + " creato");
	}

	public static void fillReport(Set<Ril> rils, String report) throws InvalidFormatException, IOException {

		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet("Dati");
		Row rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("DATA");
		rowhead.createCell(1).setCellValue("NOME PERSONA");
		rowhead.createCell(2).setCellValue("ORE LAVORATE");
		rowhead.createCell(3).setCellValue("GG SOLARI");
		rowhead.createCell(4).setCellValue("FERIE/PERMESSI");
		rowhead.createCell(5).setCellValue("MALATTIA");
		
//		SimpleDateFormat sdf = new SimpleDateFormat(); 
//		sdf = new SimpleDateFormat("MMMM yyyy");
//		Locale.setDefault(Locale.ITALIAN);
		
		int i = 0;
		for (Ril ril : rils) {
			i++;
			Row row = sheet.createRow((short) i);
			row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
			row.getCell(0).setCellValue(ril.getDateItFormat());
			row.createCell(1).setCellValue(ril.getName());
			row.createCell(2).setCellValue(ril.getWorkingHour());
			row.createCell(3).setCellValue(ril.getDays());
			row.createCell(4).setCellValue(ril.getHoliday());
			row.createCell(5).setCellValue(ril.getSickness());
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(report);
			workbook.write(out);
			out.close();
			out.flush();
			System.out.println("\"report.xls\" è stato aggiornato");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static Ril getData(String dir) throws EncryptedDocumentException, InvalidFormatException {

		Ril ril = new Ril();

		try {
			FileInputStream existingFile = new FileInputStream(new File(dir));
			Workbook workbook = WorkbookFactory.create(existingFile);
			Sheet sheet1 = workbook.getSheetAt(0);
			Sheet sheet2 = workbook.getSheetAt(1);

			ril.setDate(sheet1.getRow(5).getCell(3).getDateCellValue());
			ril.setName(sheet1.getRow(1).getCell(4).getStringCellValue());

			ril.setWorkingHour(sheet1.getRow(44).getCell(8).getStringCellValue());
			ril.setDays(sheet1.getRow(40).getCell(8).getNumericCellValue());
			ril.setHoliday(sheet2.getRow(18).getCell(4).getNumericCellValue());

			int counter = 0;
			String m = "";
			for (int i = 8; i <= 39; i++) {
				m = sheet1.getRow(i).getCell(8).getStringCellValue();
				if (m.equalsIgnoreCase("m"))
					counter++;
			}
			ril.setSickness(counter);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ril;
	}

	public static void main(String[] args) throws Exception {
		

//		final String dirReport = "C:/Users/Synclab/Documents/RIL/";
//		final String dirRil = "C:/StageCacia/RILs";
		String dirReport = "";
		String dirRil = "";
		
		System.out.println("directory di partenza");
		System.out.println(dirRil = args[0]);
		System.out.println("directory del report");
		System.out.println(dirReport = args[1]);

		final String reportFileName = "report.xls";

		
		try {
			
			File dirToReport = createDir(dirReport);

			File dirFromRils = new File(dirRil);
			if (!dirFromRils.isDirectory())
				return;
			String[] filenames = dirFromRils.list();
			Set<Ril> rils = new HashSet<Ril>();

			for (String filename : filenames) {
				System.out.println(filename);
				Ril data = getData(dirRil + File.separator + filename);
				System.out.println(data.toString());
				rils.add(data);
			}
			
			fillReport(rils, dirToReport.getAbsolutePath() + File.separator + reportFileName);

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutOfBoundsException caught");
		}

	}

}
