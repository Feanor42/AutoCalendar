package com.event;

import java.sql.DateTime;




public class Event {
	
	
	private DateTime startDate;
	private DateTime endDate;
	private String title;
	private String description;
	private String location;
	
	
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
		
		
	}
	
	//Overloaded constructer
	public Event(DateTime sd, DateTime ed, String tit, String desc, String loc)
	{
		startDate = sd;
		endDate = ed;
		
		title = tit;
		description = desc;
		location = loc;
		
		
	}
	
	public DateTime getStartDate()
	{
		return startDate;
	}
	
	public DateTime getEndDate()
	{
		return endDate;
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
	
	public void setStartDate(DateTime d)
	{
		startDate = d;
	}
	
	public void setEndDate(DateTime d)
	{
		endDate = d;
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
