package com.event;
/* The goal of identifier is to create an 
 * easy way to identify events and their 
 * relationship with other events*/

public class Identifier {

	private int eventIdNum;
	private String eventId = "";
	private int eventRelationshipNum;
	private String eventRelationshipId = "";
	private String identifier;
	
	public Identifier()
	{
		eventRelationshipNum = -1;
		eventIdNum = -1;
		
	}
	
	public Identifier(int id, int rel)
	{
		eventRelationshipNum = rel;
		eventIdNum = id;
		setIdentifier();
	}
	
	private void setIdentifier()
	{
		if(eventIdNum>99)
		{
			eventId = Integer.toString(eventIdNum);
		}
		else if(eventIdNum>9)
		{
			eventId = "0" +Integer.toString(eventIdNum);
		}
		else
		{
			eventId = "00" +Integer.toString(eventIdNum);
		}
		if(eventRelationshipNum>99)
		{
			eventRelationshipId = Integer.toString(eventRelationshipNum);
		}
		else if(eventRelationshipNum>9)
		{
			eventRelationshipId = "0" +Integer.toString(eventRelationshipNum);
		}
		else
		{
			eventRelationshipId = "00" +Integer.toString(eventRelationshipNum);
		}
		identifier = eventId + "-"+ eventRelationshipId;

	}
	
	public String getEventId()
	{
		return eventId;
	}
	
	public String getEventRelationshipId()
	{
		return eventRelationshipId;
	}
	
	public String toString()
	{
		return identifier;
	}
	
}
