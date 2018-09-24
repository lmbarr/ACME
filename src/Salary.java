import java.time.LocalTime;

public class Salary {
	
	private TimeInterval realUsedTime;
	private Range range;
	
	Salary(Range t) {
		range = t;
	}
	
	Salary(Range t, TimeInterval usedTime) {
		range = t;
		realUsedTime = usedTime;
	}
	
	public TimeInterval getTimeContantInterval() {
		return this.range.getTimeContantInterval();
	}
	
	public void setRealUsedTime(TimeInterval t) {
		realUsedTime = t;
	}
	
	public TimeInterval getRealUsedTime() {
		return realUsedTime;
	}
	
	public Range getRange() {
		return range;
	}
	
	public double getHourSalary() {
		double durationHour = realUsedTime.getDuration().toMinutes() / 60.0;
		return (range.getRate() * durationHour) / 1.00;
	}
	
	public Salary(Salary s) {
		this(s.getRange(), s.getRealUsedTime());
	}
}

class Range {
	
	private TimeInterval timeConstantInterval;
	private float rate;
	
	public Range(TimeInterval timeConstantInterval, float rate) {
		this.timeConstantInterval = timeConstantInterval;
		this.rate = rate;		
	}
	
	public TimeInterval getTimeContantInterval() {
		return timeConstantInterval;
	}
	
	public float getRate() { return rate; }
}

interface Days {
	public TimeInterval getTimeInterval();
	public Salary getSalary();
}

enum WeekendDayRange implements Days {
	firstShift(new Salary(new Range(new TimeInterval(LocalTime.of(00, 01), LocalTime.of(9, 00)), 30))), 
	secondShift(new Salary(new Range(new TimeInterval(LocalTime.of(9, 01), LocalTime.of(18, 00)), 20))), 
	thirdShift(new Salary(new Range(new TimeInterval(LocalTime.of(18, 01), LocalTime.of(23, 59)), 25)));
	
	private Salary salary;
	
	WeekendDayRange(Salary s) { this.salary = s; }
	
	public TimeInterval getTimeInterval() { return salary.getTimeContantInterval(); } 
	
	public Salary getSalary() { return salary; }

}

enum WeekDayRange implements Days {
	
	firstShift(new Salary(new Range(new TimeInterval(LocalTime.of(00, 01), LocalTime.of(9, 00)), 25))), 
	secondShift(new Salary(new Range(new TimeInterval(LocalTime.of(9, 01), LocalTime.of(18, 00)), 15))), 
	thirdShift(new Salary(new Range(new TimeInterval(LocalTime.of(18, 01), LocalTime.of(23, 59)), 20)));
	
	private Salary salary;
	
	WeekDayRange(Salary salary) { this.salary = salary; }
	
	public TimeInterval getTimeInterval() { return salary.getTimeContantInterval(); } 
	
	public Salary getSalary() { return salary; }
	
}
