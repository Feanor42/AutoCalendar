package com.event;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;



public class Event {
	
	private Timestamp start;
	private Timestamp end;
	private String title;
	private String description;

	
	
	//default constructer
	public Event()
	{
		start = null;
		end = null;
		title = "";
		description = "";

		
	}
	public Event(String tit, String des)
	{
		start = null;
		end = null;
		title = tit;
		description = des;

		
	}
	//Overloaded constructer
	public Event(Timestamp st, Timestamp et, String tit, String desc)
	{
		
		start = st;
		end = et;
		title = tit;
		description = desc;
		
		
	}
	
	
	public Timestamp getStartTime()
	{
		return start;
	}
	
	public Timestamp getEndTime()
	{
		return end;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	
	public void setStartTime(Timestamp t)
	{
		start = t;
	}
	
	public void setEndTime(Timestamp t)
	{
		end = t;
	}
	
	public void setTitle(String tit)
	{
		title = tit;
	}
	
	public void setDescription(String desc)
	{
		description = desc;
	}
	

	
//	public Time getLength()
//	{
//		Time difference = new Time(0, 0, 0);
//		difference.setHours(endTime.getHours()-startTime.getHours());
//		difference.setMinutes(endTime.getMinutes()-startTime.getMinutes());
//		return difference;
//	}
}
