package com.event;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Task extends Event{

	private Timestamp assign;
	private Timestamp due;
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
	
	public Task(Timestamp a, Timestamp d, Timestamp s, Timestamp end, int e, String t, String des)
	{
		super(t,des);
		assign = a;
		due = d;
		estimatedLength = e;
		//priority = p;				
	}
	public Task(Timestamp start, Timestamp end, String title, String des, Timestamp d, int e, Timestamp a)
	{
		super(start,end,title,des);
		assign = a;
		due = d;
		estimatedLength = e;
	}
	
	public Timestamp getAssignDate()
	{
		return assign;
	}
	
	public Timestamp getDueDate()
	{
		return due;
	}
	
	public int getEstimatedLength()
	{
		return estimatedLength;
	}
	
	public void setEstimatedLength(int length)
	{
		estimatedLength = length;
	}
	
}
