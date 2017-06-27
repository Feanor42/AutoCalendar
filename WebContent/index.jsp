<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auto Calendar</title>
<link rel='stylesheet' href='css/default.css' />
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
<div class="header">
	<h1 class="title">Auto Calendar</h1>
</div>
<div id='calendar'></div>
</body>
<script src='js/main.js'></script>
<script src='js/calendar.js'></script>
<script>

</script>
</html>