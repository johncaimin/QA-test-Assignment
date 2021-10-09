package restassuredTests;

import java.util.ArrayList;
import java.util.List;

public class Beach implements Comparable<Beach>{

	 private int daysfound;
	 private String beachName;
	 private List<String> dates = new ArrayList<String>();
 
    public Beach() {
    	
    	daysfound = 0;
    	beachName = "";
    	
    }
	 

	public Beach(int daysfound, String beachName, List<String> dates) {
		super();
		this.daysfound = daysfound;
		this.beachName = beachName;
		this.dates = dates;
	}
	public int getDaysfound() {
		return daysfound;
	}
	public void setDaysfound(int daysfound) {
		this.daysfound = daysfound;
	}
	public String getBeachName() {
		return beachName;
	}
	public void setBeachName(String beachName) {
		this.beachName = beachName;
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	@Override
	public String toString() {
		return "Beach [daysfound=" + daysfound + ", beachName=" + beachName + ", dates=" + dates + "]";
	}
	 
	public int compareTo(Beach o)
	{
		return o.daysfound - this.daysfound;
	}
	
	  
		
}
