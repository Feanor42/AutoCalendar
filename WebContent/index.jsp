<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auto Calendar</title>
<link rel='stylesheet' href='lib/fullcalendar/fullcalendar.css' />
<script src='lib/jquery.js'></script>
<script src='lib/moment.js'></script>
<script src='lib/fullcalendar/fullcalendar.js'></script>
</head>
<body>
	<!-- DELETE THIS FORM BELOW ONCE FINISHED WITH IT.  THIS IS JUST BRENTS EXAMPLE OF ONE FORM OF A JAVA CALL -->
	<form action = "CallDatabase" method = "GET">
		<button type ="submit" action = "CallDatabase">
			Press for DB Call
		</button>
	</form>
	<!-- DELETE THE FORM ABOVE -->
<h1><% out.println("Auto Calendar"); %></h1>
<div id='calendar'></div>
</body>
<script>
$(document).ready(function() {
    // page is now ready, initialize the calendar...
    $('#calendar').fullCalendar({
    	customButtons: {
            addEvent: {
                text: 'Add Event',
                click: function() {
                    alert('Added event!');
                }
            },
            addTask: {
                text: 'Add Task',
                click: function() {
                    alert('Added task!');
                }
            }
        },
    	header:	{
    		left:   'prev,next addEvent addTask title',
            center: '',
            right:  'today'
    	}
    	
        // put your options and callbacks here
    })
});
</script>
</html>