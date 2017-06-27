package com.event;

import java.util.List;
import java.sql.Time;
import java.util.Date;

public class ReoccurringEvent extends Event{

	private Boolean[] days = {false,false,false,false,false,false,false};
	private Date begin;
	private Date finish;
	
	public ReoccurringEvent()
	{
		super();
		begin = null;
		finish = null;
	}
	
	/*
	 * Constructor for a reoccurring event
	 * d Boolean array length 7, index 0 representing Sunday and 
	 * true meaning the event will occur on that day
	 * b begining date of the period
	 * f final date of the period
	 */
	public ReoccurringEvent(Date sd, Date ed, Time st, Time et, String tit, String desc, String loc, Identifier id, Boolean[] d, Date b, Date f)
	{
		super( sd, ed, st, et, tit, desc, loc, id);
		days = d;
		begin = b;
		finish = f;
	}
	
	/*
	 * get the days of the week
	 */
	public Boolean[] getDaysArray()
	{
		return days;
	}
	
	/*
	 * get the date to begin reoccurence
	 * may not be the first day of the event
	 */
	public Date getBeginDate()
	{
		return begin;
	}
	
	/*
	 * Get the last date in the period of recurrence
	 * May not be the last day of the event
	 */
	public Date getFinishDate()
	{
		return finish;
	}
	
	/*
	 * reset the beginning of the period
	 */
	public void setBeginDate(Date b)
	{
		begin = b;
	}
	
	/*
	 * reset the finish of the period
	 */
	public void setfinishDate(Date f)
	{
		finish = f;
	}
	
//	public List<Event> getReoccurrences()
//	{
//		
//	}
	
	public void updateReoccurences(List<Event> reoccurrences)
	{
		for(Event e: reoccurrences)
		{
			e.setTitle(this.getTitle());
			e.setLocation(this.getLocation());
			e.setDescription(this.getDescription());
			e.setStartTime(this.getStartTime());
			e.setEndTime(this.getEndTime());	
		}
	}
}


