package com.shedule;

import java.util.ArrayList;

import com.event.Event;
import com.event.Task;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class Schedule {

	private ArrayList<Event> schedule;
	

	
	public Schedule(ArrayList<Event> s)
	{
		schedule = s;
	}
	
	public Schedule()
	{
		schedule = new ArrayList<Event>();
		generateSchedule();
	}
	
	public ArrayList<Event> getSchedule()
	{
		return schedule;
	}
	
	public void addEventToSchedule(Event e, int index)
	{
		schedule.add(index, e);
	}
	
	public void removeEventFromSchedule(Event e)
	{
		schedule.remove(e);
	}
	
	public void addTaskToSchedule(Task T)
	{
		Time due = T.getDueDate();
		Time assign = T.getAssignDate();
		int length = T.getEstimatedLength();
//		HashMap<Date,Time> possible = findViableTimes(due, assign, length);
//		possible = eliminateOutliers(possible);
		
	}
	
//	private HashMap<Date,Time> findViableTimes(Date due,Date assign,int length)
//	{
//		HashMap<Date,Time> possible = new HashMap<Date,Time>();
//		
//		for(int i = 0; i<schedule.size();i++)
//		{
//			Event e = schedule.get(i);
//			if(e.getStartDate().after(due))
//			{
//				break;
//			}
//			if(e.getStartDate().after(assign)&& i< schedule.size()-1)
//			{
//				Event next = schedule.get(i+1);
//				if((next.getStartTime().getTime()-5)-(e.getEndTime().getTime()+5) > length)
//				{
//					Date d = e.getEndDate();
//					long temp = e.getEndTime().getTime()+5;
//					Time t = new Time(temp);
//					
//					possible.put(d, t);
//				}
//			}
//			else if(e.getStartDate().after(assign)&& i== schedule.size()-1)
//			{
//				Date d = e.getEndDate();
//				long temp = e.getEndTime().getTime()+5;
//				Time t = new Time(temp);
//				
//				possible.put(d, t);
//				
//			}
//		}
//		return possible;
//	}
	
	@SuppressWarnings("rawtypes")
	private HashMap<Date,Time> eliminateOutliers(HashMap<Date,Time> possible)
	{
		Time morning = Time.valueOf("7:00:00");
		Time night = Time.valueOf("22:00:00");
		Iterator it = possible.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			if(morning.after(possible.get(pair.getKey()))&&night.before(possible.get(pair.getKey())))
			{
				it.remove();
			}
			
		}
		return possible;
	}
	
	private void generateSchedule()
	{
		String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        try {
        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM Event WHERE UserID=0";

                try {
                	Statement statement = connection.createStatement();
                    ResultSet r = statement.executeQuery(selectSql);
                    String title = "";
                    Time start = null;
                    Time end = null;
                    String des = "";
                    while (r.next()) {
                        int rows = r.getMetaData().getColumnCount();
                        
                        for (int i = 0; i < rows; i++) {
                        	String column = r.getMetaData().getColumnLabel(i + 1).toLowerCase();
                        	
                        	switch (column) {
                        		case "title":  column = "title";
                        			title = (String) r.getObject(i+1);
                        		case "datetimestart":  column = "start";
                        			start = (Time) r.getObject(i+1);
                        		case "datetimeend":  column = "end";
                        			end= (Time) r.getObject(i+1);
                        		case "description":  column = "description";
                        			des = (String) r.getObject(i+1);
                        	}
                            
                        }
                        Event e = new Event(start,end,title,des);
                        schedule.add(e);
                        
                    }
                    selectSql = "SELECT * FROM Task WHERE UserID=0";
                    r = statement.executeQuery(selectSql);
                    int et = 0;
                    Time due = null;
                    Time ass = null;
                    while (r.next()) {
                        int rows = r.getMetaData().getColumnCount();
                        
                        for (int i = 0; i < rows; i++) {
                        	String column = r.getMetaData().getColumnLabel(i + 1).toLowerCase();
                        	
                        	switch (column) {
                    		case "title":  column = "title";
                    			title = (String) r.getObject(i+1);
                    		case "datetimestart":  column = "start";
                    			start = (Time) r.getObject(i+1);
                    		case "datetimeend":  column = "end";
                    			end= (Time) r.getObject(i+1);
                    		case "description":  column = "description";
                    			des = (String) r.getObject(i+1);
                    		case "datedue":  column = "dueDate";
                    			due = (Time) r.getObject(i+1);
                    		case "priority":  column = "priority";
                    			break;	
                    		case "estimatedtime":  column = "timeToComplete";
                    			et = (int) r.getObject(i+1);
                    		case "assigndate":  column = "assignDate";
                    			ass = (Time) r.getObject(i+1);
                        	}
                            
                        }
                        Event e = new Task(start,end,title,des,due, et,ass);
                        schedule.add(e);
                }
                }
                
                catch (Exception e) {
                    e.printStackTrace();
                }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }
		
	

	
	
	
	
}
