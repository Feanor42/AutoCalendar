package com.event;

import java.sql.DateTime;

public class Task extends Event{

	private DateTime assign;
	private DateTime due;
	private int estimatedLength;
	private int priority;
	
	public Task()
	{
		super();
		assign = null;
		due = null;
		estimatedLength = -1;
		priority = -1;				
	}
	
	public Task(Date s, Date d, int e, int p)
	{
		super();
		assign = s;
		due = d;
		estimatedLength = e;
		priority = p;				
	}
	
	public DateTime getAssignDate()
	{
		return assign;
	}
	
	public DateTime getDueDate()
	{
		return due;
	}
	
	public int getEstimatedLength()
	{
		return estimatedLength;
	}
	
//	public Time getEstimatedLength()
//	{
//		
//	}
	
}
