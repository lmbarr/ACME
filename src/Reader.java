// Based on:
// http://chuwiki.chuidiang.org/index.php?title=Lectura_y_Escritura_de_Ficheros_en_Java
// https://github.com/oemel09/ACME/blob/master/src/de/oemel09/acme/TimeEntry.java
import java.util.ArrayList;
import java.time.LocalTime;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TimeEntry {

    private String name;
	private ArrayList<TimeInterval> employee = new ArrayList<TimeInterval>();

    public void parseEntry(String entry) {

        // create pattern to match the whole string
        String entryRegex = "(\\w+)=(MO|TU|WE|TH|FR|SA|SU)([0-1][0-9]|2[0-3]):([0-5][0-9])-([0-1][0-9]|2[0-3]):([0-5][0-9])(?:,(MO|TU|WE|TH|FR|SA|SU)([0-1][0-9]|2[0-3]):([0-5][0-9])-([0-1][0-9]|2[0-3]):([0-5][0-9]))*";
        if (entry.matches(entryRegex)) {
            name = entry.split("=")[0];

            // create pattern to get each group
            String workDayRegex = "(MO|TU|WE|TH|FR|SA|SU)([0-1][0-9]|2[0-3]):([0-5][0-9])-([0-1][0-9]|2[0-3]):([0-5][0-9])";
            Pattern workDayPattern = Pattern.compile(workDayRegex);
            Matcher matcher = workDayPattern.matcher(entry);
            
            while (matcher.find()) {
            	
                int startHour = Integer.parseInt(matcher.group(2));
                int startMinute = Integer.parseInt(matcher.group(3));
                int endHour = Integer.parseInt(matcher.group(4));
                int endMinute = Integer.parseInt(matcher.group(5));
                String day = matcher.group(1);
                TimeInterval start;
                
                try {
                	start = TimeInterval.createTimeIntervalObj(LocalTime.of(startHour, startMinute), 
        				LocalTime.of(endHour, endMinute), day);
                	employee.add(start);
                	
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }                    
        		
            }
        } else {
        	
            throw new IllegalArgumentException();
        }
    }

    public String getName() { return name; }
    
    public ArrayList<TimeInterval> getEmployeeTimeRecord() { return employee; }
    
    public void setEmploy() {
		ACMEEmployee employee_ = new ACMEEmployee(employee, name);
		employee_.calculateSalary();
		System.out.println(employee_);
    }
}

public class Reader {
   public static void main(String [] arg) {
      File archivo;
      FileReader fr = null;
      BufferedReader br;

      try {
         archivo = new File ("/home/luis/MEGA/workspace/ACME/src/input.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         String line;
         while((line=br.readLine())!=null){
            TimeEntry entry = new TimeEntry();
            entry.parseEntry(line);
            entry.setEmploy();
         }
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
   }
}
