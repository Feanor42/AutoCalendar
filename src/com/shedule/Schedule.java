package com.shedule;

import java.util.ArrayList;

import com.event.Event;
import com.event.Task;
import com.event.ReoccurringEvent;

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
	
}
