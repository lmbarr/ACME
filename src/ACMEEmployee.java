import java.time.DayOfWeek;
import java.util.ArrayList;

public class ACMEEmployee {
	
	private String name;
	private float salary = 0;
	private ArrayList<TimeInterval> employee;
	
	public ACMEEmployee(ArrayList<TimeInterval> employeeACME, String name) {
		employee = employeeACME;
		this.name = name;
	}
	
	public void calculateSalary() {
		
		for (TimeInterval ti: employee) { 
			Days[] rangeValues = ACMEEmployee.getClassWeekName(ti.day);
			ti.divideInWorkingHours(rangeValues);
		}
	
		for (TimeInterval ti: employee) {
			
			for (Salary s: ti.getsalaryArray()) {
				salary += s.getHourSalary();
				//System.out.println(s.getHourSalary());
			}
		}
	}
	
	public static Days[] getClassWeekName(DayOfWeek dayIn) {
		
		DayOfWeek[] weekendDays = { DayOfWeek.SATURDAY, DayOfWeek.SUNDAY };

		for (DayOfWeek day: weekendDays) {
			
			if (dayIn == day) {
				return WeekendDayRange.values();
			}
		}
		return WeekDayRange.values();
	}
	
	public String toString() {
		return "The amount to pay " + name + " is: " + salary + " USD";
	}
}
