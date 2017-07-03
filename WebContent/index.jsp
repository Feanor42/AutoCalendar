<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, height=device-height">
<title>Auto Calendar</title>
<link rel='stylesheet' href='lib/fullcalendar/fullcalendar.css' />
<script src='lib/jquery.js'></script>
<script src='lib/moment.js'></script>
<script src='lib/fullcalendar/fullcalendar.js'></script>
<link rel='stylesheet' href='css/default.css' />
<link rel='stylesheet' href='css/responsive.css' />

</head>
<body>
<div class="body">
<div id="header" class="header">
	<h1 id="pageTitle" class="title">Auto Calendar</h1>
	<h1 id="selectDayTitle" class="title">Select a Day</h1>
</div>

<div id='calendar'></div>

<!-- Create Event Modal -->
<div id="eventModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content animate-zoom">
    <form id="eventForm" action="" class="grid-12">
    	<div class="form-group span-12">
	    	<input id="eventTitle" type="text" placeholder="Title" name="eventTitle">
	    	<p class="error-message"></p>	
    	</div>
    	<div class="form-group span-12">
	    	<label for="eventDescription">Description</label>
	    	<textarea id="eventDescription" placeholder="Description" name="eventDescription"></textarea>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-4">
	    	<label for="eventStartDate">Date</label>
	    	<input id="eventStartDate" class="input-date readonly" type="text" name="eventStartDate" readonly>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-4">
    		<label for="eventStartTime">Start Time</label>
    		<select id="eventStartTime" class="time-selection" name="eventStartTime"></select>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-4">
	    	<label for="eventEndTime">End Time</label>
	    	<select id="eventEndTime" class="time-selection" name="eventEndTime"></select>
    		<p class="error-message"></p>
    	</div>
    	<div class="btn-group span-12">
	    	<button id="saveEventBtn" type="submit" class="btn-default">Save</button>
	    	<button id="cancelEventBtn" type="button" class="btn-default">Cancel</button>
	    	<button id="deleteEventBtn" type="button" class="btn-default">Delete</button>
    	</div>
	</form>
  </div>
</div>

<div id="taskModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content animate-zoom">
    <form id="taskForm" action="" class="grid-12">			
    	<div class="form-group span-12">
    		<input id="taskTitle" type="text" placeholder="Title">
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-12">
    		<label for="taskDescription">Description</label>
    		<textarea id="taskDescription" placeholder="Description"></textarea>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskAssignDate">Assign Date</label>
    		<input id="taskAssignDate" class="input-date" type="text" placeholder="Click to select day" readonly >
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskAssignTime">Assign Time</label>
    		<select id="taskAssignTime" class="time-selection" name="taskAssignTime"></select>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskDueDate">Due Date</label>
    		<input id="taskDueDate" class="input-date" type="text" placeholder="Click to select day" readonly>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-6">
	    	<label for="taskDueTime">Due Time</label>
	    	<select id="taskDueTime" class="time-selection" name="taskDueTime"></select>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskTimeToComplete">Time to Complete</label>
    		<div class="grid-12">
	    		<input id="taskTimeToComplete" class="span-8" type="number" name="quantity" min="15" max="1440" value="15" step="15" required>
	    		<span class="span-4 unit">min</span>
    		</div>
    		<p class="error-message"></p>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskPriority">Priority</label>
    		<input id="taskPriority" type="number" name="quantity" min="1" max="10" value="5">
    		<p class="error-message"></p>
    	</div>
    	<div class="btn-group span-12">
	    	<button id="saveTaskBtn" type="submit">Save</button>
	    	<button id="cancelTaskBtn" type="button">Cancel</button>
	    	<button id="deleteTaskBtn" type="button">Delete</button>
    	</div>
    </form>
  </div>
</div>
</div>
</body>

<script src="js/main.js"></script>
</html>