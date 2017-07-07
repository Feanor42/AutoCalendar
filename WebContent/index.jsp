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
<link rel='stylesheet' href='css/responsive.css' />

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
    <form id="eventForm" action="" class="grid-12">
    	<div class="form-group span-12">
	    	<p class="error-message"></p>			
	    	<input id="eventTitle" type="text" placeholder="Title" name="eventTitle">
    	</div>
    	<div class="form-group span-12">
	    	<label for="eventDescription">Description</label>
	    	<p class="error-message"></p>
	    	<textarea id="eventDescription" placeholder="Description" name="eventDescription"></textarea>
    	</div>
    	<div class="form-group span-4">
	    	<label for="eventStartDate">Date</label>
	    	<p class="error-message"></p>
	    	<input id="eventStartDate" class="input-date readonly" type="text" name="eventStartDate" readonly>
    	</div>
    	<div class="form-group span-4">
    		<label for="eventStartTime">Start Time</label>
    		<p class="error-message"></p>
    		<select id="eventStartTime" class="time-selection" name="eventStartTime"></select>
    	</div>
    	<div class="form-group span-4">
	    	<label for="eventEndTime">End Time</label>
	    	<p class="error-message"></p>
	    	<select id="eventEndTime" class="time-selection" name="eventEndTime"></select>
    	</div>
    	<div class="btn-group span-12">
	    	<button id="saveEventBtn" type="submit">Save</button>
	    	<button id="cancelEventBtn" type="button">Cancel</button>
	    	<button id="deleteEventBtn" type="button">Delete</button>
    	</div>
	</form>
  </div>
</div>

<div id="taskModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content animate-zoom">
    <form id="taskForm" action="" class="grid-12">			
    	<div class="form-group span-12">
    		<p class="error-message"></p>
    		<input id="taskTitle" type="text" placeholder="Title">
    	</div>
    	<div class="form-group span-12">
    		<label for="taskDescription">Description</label>
    		<p class="error-message"></p>
    		<textarea id="taskDescription" placeholder="Description"></textarea>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskAssignDate">Assign Date</label>
    		<p class="error-message"></p>
    		<input id="taskAssignDate" class="input-date" type="text" placeholder="Click to select day" readonly >
    	</div>
    	<div class="form-group span-6">
    		<label for="taskAssignTime">Assign Time</label>
    		<p class="error-message"></p>
    		<select id="taskAssignTime" class="time-selection" name="taskAssignTime"></select>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskDueDate">Due Date</label>
    		<p class="error-message"></p>
    		<input id="taskDueDate" class="input-date" type="text" placeholder="Click to select day" readonly>
    	</div>
    	<div class="form-group span-6">
	    	<label for="taskDueTime">Due Time</label>
	    	<p class="error-message"></p>
	    	<select id="taskDueTime" class="time-selection" name="taskDueTime"></select>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskTimeToComplete">Time to Complete</label>
    		<p class="error-message"></p>
    		<div class="grid-12">
	    		<input id="taskTimeToComplete" class="span-8" type="number" name="quantity" min="15" max="1440" value="15" step="15" required>
	    		<span class="span-4 unit">min</span>
    		</div>
    	</div>
    	<div class="form-group span-6">
    		<label for="taskPriority">Priority</label>
    		<p class="error-message"></p>
    		<input id="taskPriority" type="number" name="quantity" min="1" max="10" value="5">
    	</div>
    	<div class="btn-group span-12">
	    	<button id="saveTaskBtn" type="submit">Save</button>
	    	<button id="cancelTaskBtn" type="button">Cancel</button>
	    	<button id="deleteTaskBtn" type="button">Delete</button>
    	</div>
    </form>
  </div>
</div>

</body>

<script src="js/main.js"></script>
</html>