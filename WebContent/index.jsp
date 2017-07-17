<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<%-- <c:set var="userName" value="Default" scope="session" /> --%>
<c:choose>
	<c:when test='${sessionScope["username"] != null}'>
		<div id='calendar'></div>

		<!-- Create Event Modal -->
		<div id="eventModal" class="modal">
		  <!-- Modal content -->
		  <div class="modal-content animate-zoom">
		    <form id="eventForm" action="" class="">
		    	<div class="title-group">
			    	<input id="eventTitle" class="title" type="text" placeholder="Title" name="eventTitle">
			    	<p class="error-message"></p>	
		    	</div>
		    	<div class="desc-group">
			    	<label for="eventDescription">Description</label>
			    	<textarea id="eventDescription" placeholder="Description" name="eventDescription"></textarea>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="date-group">
			    	<label for="eventStartDate">Date</label>
			    	<input id="eventStartDate" class="date" type="text" name="eventStartDate" readonly>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="start-time-group">
		    		<label for="eventStartTime">Start Time</label>
		    		<select id="eventStartTime" class="time" name="eventStartTime"></select>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="end-time-group">
			    	<label for="eventEndTime">End Time</label>
			    	<select id="eventEndTime" class="time" name="eventEndTime"></select>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="btn-group">
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
		    <form id="taskForm" action="" class="">			
		    	<div class="title-group">
		    		<input id="taskTitle" class="title" type="text" placeholder="Title">
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="desc-group">
		    		<label for="taskDescription">Description</label>
		    		<textarea id="taskDescription" placeholder="Description"></textarea>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="assign-date-group">
		    		<label for="taskAssignDate">Assign Date</label>
		    		<input id="taskAssignDate" class="date" type="text" placeholder="Click to select day" readonly >
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="assign-time-group">
		    		<label for="taskAssignTime">Assign Time</label>
		    		<select id="taskAssignTime" class="time" name="taskAssignTime"></select>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="due-date-group">
		    		<label for="taskDueDate">Due Date</label>
		    		<input id="taskDueDate" class="date" type="text" placeholder="Click to select day" readonly>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="due-time-group">
			    	<label for="taskDueTime">Due Time</label>
			    	<select id="taskDueTime" class="time" name="taskDueTime"></select>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="est-time-group">
		    		<label for="taskTimeToComplete">Time to Complete</label>
		    		<div class="input-with-unit">
			    		<input id="taskTimeToComplete" class="" type="number" name="quantity" min="15" max="1440" value="15" step="15" required>
			    		<span class="unit">min</span>
		    		</div>
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="priority-group">
		    		<label for="taskPriority">Priority</label>
		    		<input id="taskPriority" type="number" name="quantity" min="1" max="10" value="5">
		    		<p class="error-message"></p>
		    	</div>
		    	<div class="btn-group">
			    	<button id="saveTaskBtn" type="submit" class="btn-default">Save</button>
			    	<button id="cancelTaskBtn" type="button" class="btn-default">Cancel</button>
			    	<button id="deleteTaskBtn" type="button" class="btn-default">Delete</button>
		    	</div>
		    </form>
		  </div>
		</div>
	</c:when>
	<c:otherwise>
		<div id="startPage">
			<h2 class="title">Welcome to Auto Calendar!</h2>
			<div class="login-group">
				<h3>Login</h3>
				<form id="loginForm" action="Login" method="post">
					<input id="username" type="text" name="username" placeholder="Username">
					<input id="password" type="password" name="password" placeholder="Password">
					<button class="btn-default btn-wide">Login</button>
				</form> 
			</div>
			
			<p class="or-divider">Or</p>
			
			<div class="signup-group">
				<h3>Sign up</h3>
				<form id="signupForm" action="Signup" method="post">
					<input id="username" type="text" name="username" placeholder="Username">
					<input id="password" type="password" name="password" placeholder="Password">
					<input id="password2" type="password" name="password2" placeholder="Re-enter Password">
					<input id="email" type="text" name="email" placeholder="Email">
					<button class="btn-default btn-wide">Sign up</button>
				</form>  
			</div>
		</div>
    </c:otherwise>  
</c:choose>


</div>
</body>

<script src="js/main.js"></script>
</html>