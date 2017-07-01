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
<link rel='stylesheet' href='css/default.css' />

</head>
<body>
<div id="header" class="header">
	<h1 id="pageTitle" class="title">Auto Calendar</h1>
	<h1 id="selectDayTitle" class="title">Select a Day</h1>
</div>

<div id='calendar'></div>

<!-- Create Event Modal -->
<div id="eventModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content animate-zoom">
    <form id="eventForm" action="">			
    	<input id="eventTitle" type="text" placeholder="Title" class="">
    	<label for="eventDescription">Description</label>
    	<textarea id="eventDescription" placeholder="Description"></textarea>
    	<label for="eventStartDate">Date</label>
    	<input id="eventStartDate" class="input-date" type="text">
    	<button id="saveEventBtn">Save</button>
    	<button id="cancelEventBtn">Cancel</button>
    	<button id="deleteEventBtn">Delete</button>
    </form>
  </div>
</div>

</body>

<script src="js/main.js"></script>
</html>