import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.DayOfWeek;

public class TimeInterval {
	
    private static final HashMap<String, DayOfWeek> DAYS = new HashMap<>();

    static {
    	DAYS.put("MO", DayOfWeek.MONDAY);
    	DAYS.put("TU", DayOfWeek.TUESDAY);
    	DAYS.put("WE", DayOfWeek.WEDNESDAY);
    	DAYS.put("TH", DayOfWeek.THURSDAY);
    	DAYS.put("FR", DayOfWeek.FRIDAY);
    	DAYS.put("SA", DayOfWeek.SATURDAY);
    	DAYS.put("SU", DayOfWeek.SUNDAY);
    }
	
	public DayOfWeek day;
	private LocalTime start;
	private LocalTime end;
	private ArrayList<Salary> byWorkingHours = new ArrayList<Salary> ();
	
	public TimeInterval(LocalTime start, LocalTime end, DayOfWeek day) {
		this(start, end);
		this.day = day;
	}
	
	public TimeInterval(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}
	
	public static TimeInterval createTimeIntervalObj(LocalTime start, LocalTime end, String day) throws Exception {
		
		if (start.isBefore(end)) {
			DayOfWeek day_ = DAYS.get(day);
			return new TimeInterval(start, end, day_);
		} else {
			throw new Exception("Error in time interval creation...\nstart should be before end....");
		}
	}
	
	public ArrayList<Salary> getsalaryArray() { return byWorkingHours; }
	
	public Duration getDuration() {
		return Duration.between(start, end);
	}
	
	public LocalTime getEnd() { return end; }
	public LocalTime getStart() { return start; }
	
	public boolean compareTimeInstances(LocalTime t1, LocalTime t2) {
		return t1.getHour() == t2.getHour() && t1.getMinute() == t2.getMinute();
	}
	
	public boolean isInsideInterval(LocalTime t) {
		return start.isBefore(t) && end.isAfter(t) || compareTimeInstances(t, start) || compareTimeInstances(t, end);
	}
	
	public void divideInWorkingHours(Days[] rangeConstantValues) {
	
		LocalTime start_ = LocalTime.of(start.getHour(), start.getMinute()); //clone this one
		LocalTime ticking = start;
		
		for (Days constantRange: rangeConstantValues) {
			if (constantRange.getTimeInterval().isInsideInterval(start) && constantRange.getTimeInterval().isInsideInterval(end)) {
				
				assignSalary(constantRange, end, start);
			
				return;
			}
		}		
	
		for (int minuteCounter = 0; ticking.isBefore(end.plusMinutes(1)); minuteCounter++) {
			ticking = start.plusMinutes(minuteCounter);

			for (Days contantRange: rangeConstantValues) {
				
				if (contantRange.getTimeInterval().isInsideInterval(ticking)) {
				
					if (compareTimeInstances(ticking, contantRange.getTimeInterval().getStart())) {
	
						start_ = assignSalary(contantRange, ticking, start_);
						
					} else if (compareTimeInstances(ticking, contantRange.getTimeInterval().getEnd())) {
						
						start_ = assignSalary(contantRange, ticking, start_);
						
					} else if (compareTimeInstances(ticking, end)) {
						
						start_ = assignSalary(contantRange, ticking, start_);
						return;
						
					}
				}
			}
		}
	}
	
	public LocalTime assignSalary(Days contantRange, LocalTime end_, LocalTime start_) {
		
		Salary salary = contantRange.getSalary();
	
		salary.setRealUsedTime(new TimeInterval(start_, end_));
		
		byWorkingHours.add(new Salary(salary));
		
		return LocalTime.of(end_.getHour(), end_.getMinute());
	}
}
