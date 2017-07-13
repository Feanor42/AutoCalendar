package com.event;

import java.sql.Date;
import java.sql.Time;

public class Task extends Event{

	private Time assign;
	private Time due;
	private int estimatedLength;
	//private int priority;
	
	public Task()
	{
		super();
		assign = null;
		due = null;
		estimatedLength = -1;
		//priority = -1;				
	}
	
	public Task(Time a, Time d, Time s, Time end, int e, String t, String des)
	{
		super(t,des);
		assign = a;
		due = d;
		estimatedLength = e;
		//priority = p;				
	}
	
	public Time getAssignDate()
	{
		return assign;
	}
	
	public Time getDueDate()
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
