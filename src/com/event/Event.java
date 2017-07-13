package com.event;

import java.sql.Date;
import java.sql.Time;



public class Event {
	
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;
	private String title;
	private String description;

	
	
	//default constructer
	public Event()
	{
		startDate = null;
		endDate = null;
		startTime = null;
		endTime = null;
		title = "";
		description = "";

		
	}
	public Event(String tit, String des)
	{
		startDate = null;
		endDate = null;
		startTime = null;
		endTime = null;
		title = tit;
		description = des;

		
	}
	//Overloaded constructer
	public Event(Time st, Time et, String tit, String desc)
	{
		
		startTime = st;
		endTime = et;
		title = tit;
		description = desc;
		
		
	}
	
	
	
	
	
	public Time getStartTime()
	{
		return startTime;
	}
	
	public Time getEndTime()
	{
		return endTime;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	
	public void setStartTime(Time t)
	{
		startTime = t;
	}
	
	public void setEndTime(Time t)
	{
		endTime = t;
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
