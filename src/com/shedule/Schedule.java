package com.shedule;

import java.util.ArrayList;

import com.event.Event;
import com.event.Task;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		HashMap<Date,Time> possible = findViableTimes(due, assign, length);
		possible = eliminateOutliers(possible);
		
	}
	
	private HashMap<Date,Time> findViableTimes(Date due,Date assign,int length)
	{
		HashMap<Date,Time> possible = new HashMap<Date,Time>();
		
		for(int i = 0; i<schedule.size();i++)
		{
			Event e = schedule.get(i);
			if(e.getStartDate().after(due))
			{
				break;
			}
			if(e.getStartDate().after(assign)&& i< schedule.size()-1)
			{
				Event next = schedule.get(i+1);
				if((next.getStartTime().getTime()-5)-(e.getEndTime().getTime()+5) > length)
				{
					Date d = e.getEndDate();
					long temp = e.getEndTime().getTime()+5;
					Time t = new Time(temp);
					
					possible.put(d, t);
				}
			}
			else if(e.getStartDate().after(assign)&& i== schedule.size()-1)
			{
				Date d = e.getEndDate();
				long temp = e.getEndTime().getTime()+5;
				Time t = new Time(temp);
				
				possible.put(d, t);
				
			}
		}
		return possible;
	}
	
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
	
	
}
