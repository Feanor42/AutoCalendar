package com.event;

import java.util.Date;
import java.sql.Time;



public class Event {
	
	
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;
	private String title;
	private String description;
	private String location;
	private Identifier identifier;
	
	//default constructer
	public Event()
	{
		startDate = null;
		endDate = null;
		startTime = null;
		endTime = null;
		title = "";
		description = "";
		location = "";
		identifier = null;
		
	}
	
	//Overloaded constructer
	public Event(Date sd, Date ed, Time st, Time et, String tit, String desc, String loc, Identifier id)
	{
		startDate = sd;
		endDate = ed;
		startTime = st;
		endTime = et;
		title = tit;
		description = desc;
		location = loc;
		identifier = id;
		
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
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
	
	public String getLocation()
	{
		return location;
	}
	
	public Identifier getIdentifier()
	{
		return identifier;
	}
	
	public void setStartDate(Date d)
	{
		startDate = d;
	}
	
	public void setEndDate(Date d)
	{
		endDate = d;
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
	
	public void setLocation(String l)
	{
		location = l;
	}
	
//	public Time getLength()
//	{
//		Time difference = new Time(0, 0, 0);
//		difference.setHours(endTime.getHours()-startTime.getHours());
//		difference.setMinutes(endTime.getMinutes()-startTime.getMinutes());
//		return difference;
//	}
}
