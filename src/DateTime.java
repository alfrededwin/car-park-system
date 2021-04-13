

public class DateTime implements Comparable <DateTime> { 
	
	//Properties
	private int year;
	private int month;
	private int date;
	
	private int hours;
	private int minutes;
	private int seconds;

	//Constructor
	public DateTime(int year, int month, int date, int hours, int minutes, int seconds) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		if(Math.log10(year)+1>4) {
			System.out.println("Invalid input for a year.");
		}else {
			this.year = year;
		}
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
			if(month>0 && month <12) {
				this.month = month;
			}else {
				throw new IllegalArgumentException("Invalid input for a month");
			}
	
	}
	
	public int getDate() { 
		return date;
	}

	public void setDate(int date) {
		if(getYear()%400==0 && getMonth()==2) {
			if(date<30) {
				this.date = date;
			}else {
				throw new IllegalArgumentException("This is a leap year and this "
						+ "month has only 29 days.");
			}
		}else if(!(getYear()%400==0)&& getMonth()==2) {
			if(date<29) {
				this.date = date;
			}else {
				throw new IllegalArgumentException("Invalid input for a date");
			}
		}else {
			this.date=date;
		}
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setHours(int hours) {
		if(hours>0 && hours<24) {
			this.hours = hours;
		}else {
			throw new IllegalArgumentException("Invalid input for a hour");
		}
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public void setMinutes(int minutes) {
		if(minutes<60) {
			this.minutes = minutes;
		}else {
			throw new IllegalArgumentException("Invalid input for minutes");
		}	
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public void setSeconds(int seconds) {
		if(seconds<60) {
			this.seconds = seconds;
		}else {
			throw new IllegalArgumentException("Invalid input for  seconds");
		}	
	}
	
	private long getSecondInaDay() {
		return this.year*365*24*60*60+
				this.month*30*24*60*60+this.date*24*60*60+this.hours*60*60+
				this.minutes*60+this.seconds;
	}

	@Override
	public int compareTo(DateTime o) {	
		return (int) (this.getSecondInaDay() - o.getSecondInaDay());
	}

}
