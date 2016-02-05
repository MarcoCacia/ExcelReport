package it.synclab.rilmanager.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ril {

	private Date date;
	private String name;
	private String workingHour;
	private double days;
	private double holiday;
	private int sickness;
	
	public Ril(){
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(days);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(holiday);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + sickness;
		result = prime * result + ((workingHour == null) ? 0 : workingHour.hashCode());
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(days) != Double.doubleToLongBits(other.days))
			return false;
		if (Double.doubleToLongBits(holiday) != Double.doubleToLongBits(other.holiday))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sickness != other.sickness)
			return false;
		if (workingHour == null) {
			if (other.workingHour != null)
				return false;
		} else if (!workingHour.equals(other.workingHour))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Ril [date=" + date + ", name=" + name + ", workingHour=" + workingHour + ", days=" + days + ", holiday="
				+ holiday + ", sickness=" + sickness + "]";
	}
	
	
		
	
}