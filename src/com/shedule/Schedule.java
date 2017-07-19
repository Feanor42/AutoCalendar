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

	private ArrayList<Time> schedule;
	private ArrayList<Time> possibilities = new ArrayList<Time>();
	private long[] best;

	
	public Schedule(ArrayList<Time> s)
	{
		schedule = s;
	}
	
	public Schedule()
	{
		schedule = new ArrayList<Time>();
		generateSchedule();
		
	}
	
	public ArrayList<Time> getSchedule()
	{
		return schedule;
	}
	
	public void addEventToSchedule(Event e, int index)
	{
		
		schedule.add(index,e.getStartTime());
		schedule.add(index+1,e.getEndTime());
				
	}
	
	public void addEventToSchedule(Event e)
	{
		Time start = e.getStartTime();
		Time end = e.getEndTime();
		for(int i = 0; i<schedule.size(); i+=2)
		{
			if(end.before(schedule.get(i)))
			{
				schedule.add(i,start);
				schedule.add(i+1,end);
				return;
			}
		}
		int i = schedule.size()-1;
		if(schedule.get(i).before(start))
		{
			schedule.add(start);
			schedule.add(end);
		}
				
	}
	
	public void removeEventFromSchedule(Time e)
	{
		schedule.remove(e);
	}
	
	public Task addTaskToSchedule(Task T)
	{
		Time due = T.getDueDate();
		Time assign = T.getAssignDate();
		long length = T.getEstimatedLength()*60000;
		eliminateOutliers(assign, due);
		findPossibleTimes(length);
		long start = findBestPossibleTime(assign);
		Time s =new Time(start);
		T.setStartTime(s);
		Time e = new Time(start+length);
		T.setEndTime(e);
		return T;
	}
	
	private void eliminateOutliers(Time ass, Time due)
	{
		for(int i =1; i< schedule.size(); i+=2)
		{
			if(ass.after(schedule.get(i)))
			{
				schedule.remove(i);
				schedule.remove(i-1);
				i-=2;
			}
			else if(due.before(schedule.get(i)))
			{
				schedule.remove(i);
				schedule.remove(i-1);
				i-=2;
			}
		}
		
	}
	private void findPossibleTimes(long l)
	{
		long fiveMin = 5*60000;
		for(int i =1; i< schedule.size()-1; i+=2)
		{
			if(l< (schedule.get(i+1).getTime()-schedule.get(i).getTime())-2*fiveMin)
			{
				Time t = new Time(schedule.get(i).getTime()+fiveMin);
				possibilities.add(t);
			}
			
		}
	}
	
	private long findBestPossibleTime(Time ass)
	{
		best = new long[possibilities.size()];
		long focusTime = ass.getTime();
		for(int i = 0; i< possibilities.size(); i++)
		{
			long possibleTime= possibilities.get(i).getTime();
			if(Math.abs(possibleTime-focusTime)> Math.abs(possibleTime-focusTime+86400000))
			{
				focusTime+=86400000;
				best[i] = (Math.abs(possibleTime-focusTime));
				
			}
			else
			{
				best[i] = (Math.abs(possibleTime-focusTime));
			}
		}
		long ans = 86400000;
		for(long l:best)
		{
			if(l<ans)
			{
				ans = l;
			}
		}
		
		return ans;
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
                        addEventToSchedule(e);
                        
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
                        	}
                            
                        }
                        Event e = new Event(start,end,title,des);
                        addEventToSchedule(e);
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
