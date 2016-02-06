package it.synclab.rilmanager.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Ril {
	
	private Date date;
	private String name;
	private String workingHour;
	private double days;
	private double holiday;
	private int sickness;
	private String filename;
	
	public Ril() {
	}
	
	public void fillByFilename(String file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		FileInputStream fis = new FileInputStream(new File(file));
		
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet0 = workbook.getSheetAt(0);
		Sheet sheet1 = workbook.getSheetAt(1);
		
		this.date = sheet0.getRow(5).getCell(3).getDateCellValue();
		this.name = sheet0.getRow(1).getCell(4).getStringCellValue();
		
		this.workingHour = sheet0.getRow(44).getCell(8).getStringCellValue();
		this.days = sheet0.getRow(40).getCell(8).getNumericCellValue();
		this.holiday = sheet1.getRow(18).getCell(4).getNumericCellValue();
		
		int counter = 0;
		for (int i = 8; i <= 39; i++) {
			if ("m".equalsIgnoreCase(sheet0.getRow(i).getCell(8).getStringCellValue()))
				counter++;
		}
		this.filename = file;
		
	}
	
	public Ril(Date date, String name, String workingHour, double days, double holiday, int sickness) {
		this.date = date;
		this.name = name;
		this.workingHour = workingHour;
		this.days = days;
		this.holiday = holiday;
		this.sickness = sickness;
	}
	
	public String getDateItFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Locale.setDefault(Locale.ITALIAN);
		sdf = new SimpleDateFormat("MMMM yyyy");
		return sdf.format(date);
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getWorkingHour() {
		return workingHour;
	}
	
	public void setWorkingHour(String workingHour) {
		this.workingHour = workingHour;
	}
	
	public double getDays() {
		return days;
	}
	
	public void setDays(double days) {
		this.days = days;
	}
	
	public double getHoliday() {
		return holiday;
	}
	
	public void setHoliday(double holiday) {
		this.holiday = holiday;
	}
	
	public int getSickness() {
		return sickness;
	}
	
	public void setSickness(int sickness) {
		this.sickness = sickness;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ril other = (Ril) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Ril [date=" + date + ", name=" + name + ", workingHour=" + workingHour + ", days=" + days + ", holiday=" + holiday + ", sickness=" + sickness + ", filename="
				+ filename + "]";
	}
	
}