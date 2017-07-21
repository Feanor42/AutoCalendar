package com.shedule;

import java.util.ArrayList;
import java.util.Calendar;

import com.event.Event;
import com.event.Task;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class Schedule {

	private ArrayList<Event> schedule;
	private ArrayList<Timestamp> possibilities = new ArrayList<Timestamp>();
	private long[] best;

	
	public Schedule(ArrayList<Event> s)
	{
		schedule = s;
	}
	
	public Schedule(int userID)
	{
		schedule = new ArrayList<Event>();
		generateSchedule(userID);
		
	}
	
	public ArrayList<Event> getSchedule()
	{
		return schedule;
	}
	
	public void addEventToSchedule(Event e, int index)
	{
		
		schedule.add(index,e);
				
	}
	
	public void addEventToSchedule(Event e)
	{
		schedule.add(e);
	}
	
	public void removeEventFromSchedule(Timestamp e)
	{
		schedule.remove(e);
	}
	
	public Task addTaskToSchedule(Task T)
	{
		// Splitting tasks functionality
//		if(T.getEstimatedLength() > 60)
//		{
//			int length = T.getEstimatedLength();
//			int minIndLength = (int) Math.floor(length / Math.floor((length/30)));
//			while(length > 0)
//			{
//				Task splitTask = T;
//				length = length - minIndLength;
//				splitTask.setEstimatedLength(length);
//				
//			}
//			
//		}
		Timestamp due = T.getDueDate();
		Timestamp assign = T.getAssignDate();
		
		// Find closest point to noon in the middle of 
		// the assign date to due date time frame. Prefer middle of week
		// over noon focus time.
		// 1. Find closest spot to noon every day that it will fit.
		// 2. Find closest spot to middle of week that it will fit.
		
		// Convert due and assign to Calendars
		Calendar assignDate = Calendar.getInstance();
		assignDate.setTime(assign); 
		Calendar dueDate = Calendar.getInstance();
		dueDate.setTime(due); 
		
		// Start algorithm at assign day
		Calendar initialStartDate = Calendar.getInstance();
		initialStartDate.setTime(assign);  
		
		while( !initialStartDate.after(dueDate) )
		{
			// Set closest to noon as possible on each day
			initialStartDate.set(Calendar.HOUR_OF_DAY, 12);
			initialStartDate.set(Calendar.MINUTE, 0); 
			initialStartDate.set(Calendar.SECOND, 0); 
			initialStartDate.set(Calendar.MILLISECOND, 0); 
			
			// If assign date is after noon then set 
			// the start time to the assign time
			if(initialStartDate.before(assignDate))
			{
				initialStartDate = (Calendar) assignDate.clone();
			}
			
			// Set end time to start time plus estimated time
			Calendar initialEndDate = Calendar.getInstance();
			initialEndDate = (Calendar) initialStartDate.clone(); 
			initialEndDate.add(Calendar.MINUTE, T.getEstimatedLength());
			
			// If intialEndDate is after the due date then set
			// the initialEndDate to the due date and change
			// the initial Start Date accordingly.
			if(initialEndDate.after(dueDate))
			{
				initialEndDate = (Calendar) dueDate.clone();
				initialStartDate = (Calendar) initialEndDate.clone();
				initialStartDate.add(Calendar.MINUTE, -T.getEstimatedLength());
				
				// If start date is now in previous day then stop the loop
				if(initialStartDate.get(Calendar.DAY_OF_YEAR) != initialEndDate.get(Calendar.DAY_OF_YEAR))
				{
					break;
				}
			}
			
			// Add 5 min buffer to start and end time
			Calendar initialStartDateBuffer = Calendar.getInstance();
			initialStartDateBuffer = (Calendar) initialStartDate.clone();
			initialStartDateBuffer.add(Calendar.MINUTE, -5);
			
			Calendar initialEndDateBuffer = Calendar.getInstance();
			initialEndDateBuffer = (Calendar) initialEndDate.clone();
			initialEndDateBuffer.add(Calendar.MINUTE, 5);
			
			// Make temporary times for finding a good possible time
			Calendar endFinal = Calendar.getInstance();
			endFinal = (Calendar) initialEndDateBuffer.clone();
			
			Calendar startFinal = Calendar.getInstance();
			startFinal = (Calendar) initialStartDateBuffer.clone();
			
			boolean possible = false;
			int direction = 1;
			int offset = 0;
			
			while(!possible)
			{
				possible = true;
				
				for(int i = 0; i < schedule.size(); i++)
				{
					Calendar eventStart = Calendar.getInstance();
					eventStart.setTime(schedule.get(i).getStartTime());
					Calendar eventEnd = Calendar.getInstance();
					eventEnd.setTime(schedule.get(i).getEndTime());
					
					// If the start of the task is after the start of an event
					// and the end of the task is before the end of the event then
					// the position is not valid. Or if the end date is after the start
					// of an event and before the end of an event then the position
					// is not valid. Finally, if the start of the task is before the start
					// of an event and the end of the task is after the end of the event
					// then it is not valid.
					if(((startFinal.after(eventStart) || startFinal.equals(eventStart)) && 
							(startFinal.before(eventEnd) || startFinal.equals(eventEnd))) ||
							((endFinal.after(eventStart) || endFinal.equals(eventStart)) && 
							(endFinal.before(eventEnd) || endFinal.equals(eventEnd))) ||
							(startFinal.before(eventStart) && endFinal.after(eventEnd)))
					{
						possible = false;
					}
				}
				if(!possible)
				{
					// Move time plus and minus 5 minutes around noon
					// until a possible time is found
					offset += 5;
					direction = -direction;
					endFinal = (Calendar) initialEndDateBuffer.clone();
					startFinal = (Calendar) initialStartDateBuffer.clone();
					
					// If offset puts the start before the assign time,
					// or puts the end after the due time, then don't 
					// move in that direction. Or, if the day of year is 
					// different then the initial day of year then switch
					// directions.
					endFinal.add(Calendar.MINUTE, offset*direction);
					startFinal.add(Calendar.MINUTE, offset*direction);
					startFinal.add(Calendar.MINUTE, 5);
					endFinal.add(Calendar.MINUTE, -5);
					
					if(endFinal.after(dueDate) || 
							startFinal.before(assignDate) || 
							(endFinal.get(Calendar.DAY_OF_YEAR) != initialEndDateBuffer.get(Calendar.DAY_OF_YEAR)))
					{
						direction = -direction;
					}

					endFinal = (Calendar) initialEndDateBuffer.clone();
					startFinal = (Calendar) initialStartDateBuffer.clone();
					endFinal.add(Calendar.MINUTE, offset*direction);
					startFinal.add(Calendar.MINUTE, offset*direction);
					
					// If it still doesn't work then end the loop
					if(endFinal.after(dueDate) || 
							startFinal.before(assignDate) || 
							(startFinal.get(Calendar.DAY_OF_YEAR) != initialStartDateBuffer.get(Calendar.DAY_OF_YEAR)))
					{
						break;
					}
				}
				else
				{
					break;
				}
			}
			if(possible)
			{
				// Remove buffer from startFinal and endFinal
				startFinal.add(Calendar.MINUTE, 5);
				endFinal.add(Calendar.MINUTE, -5);
				
				// Add possible start time to the list of possibilities
				possibilities.add( new java.sql.Timestamp(startFinal.getTimeInMillis()) );
			}
			
			// Go to start of next day
			initialStartDate.add(Calendar.DAY_OF_YEAR, 1);
			initialStartDate.set(Calendar.HOUR_OF_DAY, 0);
			initialStartDate.set(Calendar.MINUTE, 0); 
			initialStartDate.set(Calendar.SECOND, 0); 
			initialStartDate.set(Calendar.MILLISECOND, 0); 
			
		}
				
		// Get a set of all days that are possible
		Set<Integer> days = new HashSet<Integer>();
		for(Timestamp p:possibilities)
		{
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(p);
			days.add(tempStart.get(Calendar.DAY_OF_YEAR));
		}
		
		// HashMap with days as the key and length of events
		// on that day as the value
		HashMap<Integer, Long> dayAndTime = new HashMap<Integer, Long>();
		
		// Initialize all entries in map to days with zero events
		for(int day: days)
		{
			dayAndTime.put(day, (long) 0);
		}
		
		// Find the day with the least amount of time
		// spent on events.
		for(Event e:schedule)
		{
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(e.getStartTime());
			if(days.contains(tempStart.get(Calendar.DAY_OF_YEAR)))
			{
				long length = 0;
				if(dayAndTime.containsKey(tempStart.get(Calendar.DAY_OF_YEAR)))
				{
					length = dayAndTime.get(tempStart.get(Calendar.DAY_OF_YEAR));
				}
				
				length += e.getEndTime().getTime() - e.getStartTime().getTime();
				dayAndTime.put(tempStart.get(Calendar.DAY_OF_YEAR), length);
			}
		}
		
		// Iterate through days and times to find day with least time
		int bestDay = 0;
		long leastTime = 0;
		for (Map.Entry<Integer, Long> entry : dayAndTime.entrySet()) 
		{
			Integer day = entry.getKey();
		    Long length = entry.getValue();
		    if(leastTime == 0 || (length < leastTime))
		    {
		    	leastTime = length;
		    	bestDay = day;
		    	if(length == 0)
		    	{
		    		break;
		    	}
		    }
		}
	
		for(Timestamp p:possibilities)
		{
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(p);
			if(tempStart.get(Calendar.DAY_OF_YEAR) == bestDay)
			{
				T.setStartTime(p);
			}
		}
				
		long endTime = T.getStartTime().getTime() + 60000*T.getEstimatedLength();
		
		Timestamp e = new Timestamp(endTime);
		T.setEndTime(e);
		return T;
	}

	
	private void generateSchedule(int userID)
	{
		String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        try {
        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM Event WHERE UserID=" + userID;

                try {
                	Statement statement = connection.createStatement();
                    ResultSet r = statement.executeQuery(selectSql);
                    String title = "";
                    Timestamp start = null;
                    Timestamp end = null;
                    String des = "";
                    
                    while (r.next()) {
                        title = r.getString("title");
                        des = r.getString("description");
                        start = r.getTimestamp("datetimestart");
                        end = r.getTimestamp("datetimeend");
                        
                        Event e = new Event(start, end, title, des);
                        addEventToSchedule(e);
                    }
                    
                    selectSql = "SELECT * FROM Task WHERE UserID=" + userID;
                    r = statement.executeQuery(selectSql);
                    int et = 0;
                    Timestamp due = null;
                    Timestamp ass = null;
                    
                    while (r.next()) {
                    	title = r.getString("title");
                        des = r.getString("description");
                        start = r.getTimestamp("datetimestart");
                        end = r.getTimestamp("datetimeend");
                    	ass = r.getTimestamp("assigndate");
                        due = r.getTimestamp("datedue");
                        
                        Task t = new Task(start, end, title, des, ass, et, due);
                        addEventToSchedule(t);
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
