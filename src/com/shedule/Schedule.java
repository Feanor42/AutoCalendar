package com.shedule;

import java.util.ArrayList;

import com.event.Event;
import com.event.Task;
import java.sql.Date;

public class Schedule {

	private ArrayList<Event> schedule;
	
	public Schedule()
	{
		schedule = new ArrayList<Event>();
	}
	
	public Schedule(ArrayList<Event> s)
	{
		schedule = s;
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
		Date due = T.getDueDate();
		Date assign = T.getAssignDate();
		int length = T.getEstimatedLength();
		
	}
	
	public ArrayList<Date> findViableTimes(Date due,Date assign,int length)
	{
		ArrayList<Date> possible = new ArrayList<Date>();
		for(Event e: schedule)
		{
//			if(e.getStartDate() > due)
//			{
//				
//			}
		}
		return possible;
	}
	
}
