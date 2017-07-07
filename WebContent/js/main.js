/** Main JS file for Auto Calendar
 * 
 */

// Global variables
var MODALS = document.getElementsByClassName("modal");
var EVENTS = [];
var TASKS = [];
var ID = 0; // Temporary ID
var HOURS = [12,1,2,3,4,5,6,7,8,9,10,11];
generateHours('am');
generateHours('pm');
function generateHours(ampm){
	for(var i = 0; i < HOURS.length; i++){
		var off = 0;
		if(HOURS[i] === 12 && ampm === 'am')
			off = -12;
		else if(HOURS[i] < 12 && ampm === 'pm')
			off = 12;
		for(var min = 0; min < 60; min += 15){
			var $option = $("<option>");
			$option.val(addZero(HOURS[i]+off) + ':' + addZero(min).toString());
			$option.html(addZero(HOURS[i]) + ':' + addZero(min).toString() + ampm);
			$('.time-selection').append($option);
		}
	}
}

function addZero(num){
	return num > 9 ? "" + num: "0" + num;
}

// <Samuel Livingston> 30-Jun-2017
// Options for fullcalendar
var initialCalendarOptions = {
    	customButtons: {
            addEvent: {
                text: 'Add Event',
                click: function() {
                	addEvent();
                }
            },
            addTask: {
                text: 'Add Task',
                click: function() {
                    addTask();
                }
            },
            callDatabase: {
            	text: 'Call Database',
            	click: function() {
            	    var xhr = new XMLHttpRequest();
            	    xhr.onreadystatechange = function() {
            	        if (xhr.readyState == 4) {
            	            var data = xhr.responseText;
            	            alert(data);
            	        }
            	    }
            	    xhr.open('GET', 'AddEvent', true);
            	    xhr.send(null);
            	}
            }
        },
    	header:	{
    		left:   'prev,next addEvent addTask callDatabase title ',
            center: '',
            right:  'today month agendaWeek agendaDay'
    	},    	
    	eventClick: function(calElement, jsEvent, view) {
    		viewElement(calElement);
        },
        dayClick: function(date, jsEvent, view) {
            $('#calendar').fullCalendar('changeView', 'agendaDay', date.format());
        },
        selectable: false
    };

// <Samuel Livingston> 30-Jun-2017
// Options for when the date is being picked
var selectDayCalendarOptions = {
	    selectable: true,
	    selectConstraint: {
		      start: '00:00', 
		      end: '24:00', 
		    },
		    eventClick: function(calEvent, jsEvent, view) {

	        },
	        dayClick: function(date, jsEvent, view) {

	        },
	        header:	{
	    		left:   'prev,next',
	            center: 'title',
	            right:  'today'
	    	},
	    	navLinks: false,
	    	weekNumbers: false
	};

$(document).ready(function() {
    $('#calendar').fullCalendar(initialCalendarOptions); // Initialize fullcalendar
    showEvents();
});

// <Samuel Livingston> 30-Jun-2017
// Event object. Based on fullcalendar's event object but with extra fields.
function Event(args){
	this.id = args.id;
	this.title = args.title;
	this.allDay = false;
	this.start = args.start; // date-time
	this.end = args.end; // date-time
	this.eventType = args.eventType;	
	this.description = args.description;
	this.type = 'Event';
	this.color = '#4289f4';
}

// <Samuel Livingston> 30-Jun-2017
// Task object. Inherits from event object.
function Task(args){
	Event.call(this, args);
	Task.prototype = Object.create(Event.prototype);
	Task.prototype.constructor = Task;
	
	this.assignDate = args.assignDate;
	this.dueDate = args.dueDate;
	this.priority = args.priority;
	this.timeToComplete = args.timeToComplete;
	this.type = 'Task';
	this.color = '#ff9635';
}

// <Samuel Livingston> 30-Jun-2017
// Shows all the events on the calendar
function showEvents(){
	for( var i = 0; i < EVENTS.length; i++){
		$('#calendar').fullCalendar( 'renderEvent', EVENTS[i], true);
	}
}

// <Samuel Livingston> 30-Jun-2017
// Sets up calendar to allow user to select a day from the calendar. 
function selectDayCalendar(){
	$('#calendar').fullCalendar('option', selectDayCalendarOptions);
	$('#pageTitle').addClass("pageTitleMove");
	$('#selectDayTitle').addClass("selectDayTitleMove");
	$('#calendar').fullCalendar('changeView', 'month');
}

// <Samuel Livingston> 30-Jun-2017
// Resets to the normal calendar
function normalCalendar(){
	$('#calendar').fullCalendar('option', initialCalendarOptions);
	$('#pageTitle').removeClass("pageTitleMove");
	$('#selectDayTitle').removeClass("selectDayTitleMove");
}

// <Samuel Livingston> 02-Jul-2017
// Adds an event to the calendar
function addEvent(){
	selectDayCalendar(); // First select a day for the event
	document.getElementById("eventForm").reset();
	
	// Day selection callback
	$('#calendar').fullCalendar('option', 'select', function(start, end, jsEvent, view){
		normalCalendar();
		$("#eventModal").css("display", "block");
		$("#eventStartDate").val(start.format('MM/DD/YYYY'));
		$("#eventStartDate").change();
	});
	
	// <Samuel Livingston> 30-Jun-2017
	// Event form submit callback. Prevent the event form from submitting. 
	// This is where ajax call will go.
	$('#eventForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		if(!validateEventData())
			return false;
		
		$("#eventModal").css("display", "none");
		var args = {
				id: ID,
				title: $("#eventTitle").val(),
				start: $("#eventStartDate").val() + ' ' + $("#eventStartTime").val(), 
				end: $("#eventStartDate").val() + ' ' + $("#eventEndTime").val(), 
				eventType: 0,
				description: $("#eventDescription").val()
		}
		newEvent = new Event( args );
		$('#calendar').fullCalendar( 'renderEvent', newEvent, true);
		EVENTS.push(newEvent);
		ID++;
	});
	
	// Cancel button callback
	$('#cancelEventBtn').off().click(function(event){
		$("#eventModal").css("display", "none");
	});
}

// <Samuel Livingston> 02-Jul-2017
// Adds a task to the calendar
function addTask(){
	$("#taskModal").css("display", "block");
	document.getElementById("taskForm").reset();
	
	// Assign Date input callback
	$('#taskAssignDate').click(function(){
		$("#taskModal").css("display", "none");
		selectDayCalendar();
		
		// Day selection callback
		$('#calendar').fullCalendar('option', 'select', function(start, end, jsEvent, view){
			normalCalendar();
			$("#taskModal").css("display", "block");
			$("#taskAssignDate").val(start.format('MM/DD/YYYY'));
			$("#taskAssignDate").change();
		});
	});
	
	// Due Date input callback
	$('#taskDueDate').click(function(){
		$("#taskModal").css("display", "none");
		selectDayCalendar();
		
		// Day selection callback
		$('#calendar').fullCalendar('option', 'select', function(start, end, jsEvent, view){
			normalCalendar();
			$("#taskModal").css("display", "block");
			$("#taskDueDate").val(start.format('MM/DD/YYYY'));
			$("#taskDueDate").change();
		});
	});

	// <Samuel Livingston> 30-Jun-2017
	// Task form submit callback. Prevent the task form from submitting. 
	// This is where ajax call will go.
	$('#taskForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		if(!validateTaskData())
			return false;
		
		$("#taskModal").css("display", "none");
		var args = {
				id: ID,
				title: $("#taskTitle").val(),
				start: $("#taskAssignDate").val() + ' ' + $("#taskAssignTime").val(),
				end: $("#taskDueDate").val() + ' ' +  $("#taskDueTime").val(),
				eventType: 0,
				description: $("#taskDescription").val(),
				assignDate: moment($("#taskAssignDate").val() + ' ' +  $("#taskAssignTime").val()),
				dueDate: moment($("#taskDueDate").val() + ' ' +  $("#taskDueTime").val()),
				priority: $("#taskPriority").val(),
				timeToComplete: $("#taskTimeToComplete").val()
		}
		newTask = new Task( args );
		$('#calendar').fullCalendar( 'renderEvent', newTask, true);
		TASKS.push(newTask);
		ID++;
	});
	
	// Cancel button callback
	$('#cancelTaskBtn').off().click(function(event){
		$("#taskModal").css("display", "none");
	});
}

$(".readonly").on('keydown paste', function(e){
    e.preventDefault();
});

// <Samuel Livingston> 30-Jun-2017
// Callback for date input in event dialog. Closes the event
// dialog and allows user to pick a new date.
$('#eventStartDate').click(function(){
	$("#eventModal").css("display", "none");
	selectDayCalendar();
});

// <Samuel Livingston> 30-Jun-2017
// View task or event when clicked. calEvent is a generic element. 
function viewElement(calElement){
	if( calElement.type === 'Event' ){
		editEvent(calElement);
	}
	else if(calElement.type === 'Task'){
		editTask(calElement);
	}
	else{
	}
}

function editEvent(calEvent){
	$("#eventModal").css("display", "block");
	$("#eventTitle").val(calEvent.title);
	$("#eventDescription").val(calEvent.description);
	$("#eventStartDate").val(calEvent.start.format('MM/DD/YYYY'));
	$("#eventStartTime").val(calEvent.start.format('HH:mm')).change();
	$("#eventEndTime").val(calEvent.end.format('HH:mm')).change();
	
	// <Samuel Livingston> 30-Jun-2017
	// Event form submit callback. Prevent the event form from submitting. 
	// This is where ajax call will go.
	$('#eventForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		if(!validateEventData())
			return false;
		
		$("#eventModal").css("display", "none");
		calEvent.title = $("#eventTitle").val();
		calEvent.description = $("#eventDescription").val();
		calEvent.start = $("#eventStartDate").val() + ' ' + $("#eventStartTime").val();
		calEvent.end = $("#eventStartDate").val() + ' ' + $("#eventEndTime").val();
		$('#calendar').fullCalendar( 'updateEvent', calEvent );
	});
	
	// Day selection callback
	$('#calendar').fullCalendar('option', 'select', function(start, end, jsEvent, view){
		normalCalendar();
		$("#eventModal").css("display", "block");
		$("#eventStartDate").val(start.format('MM/DD/YYYY'));
		$("#eventStartDate").change();
	});
	
	// Cancel button callback
	$('#cancelEventBtn').off().click(function(event){
		$("#eventModal").css("display", "none");
	});
	
}

function editTask(calTask){
	$("#taskModal").css("display", "block");
	$("#taskTitle").val(calTask.title);
	$("#taskDescription").val(calTask.description);
	$("#taskAssignDate").val(calTask.assignDate.format('MM/DD/YYYY'));
	$("#taskDueDate").val(calTask.dueDate.format('MM/DD/YYYY'));
	$("#taskAssignTime").val(calTask.assignDate.format('HH:mm'));
	$("#taskDueTime").val(calTask.dueDate.format('HH:mm'));
	$("#taskPriority").val(calTask.priority);
	$("#taskTimeToComplete").val(calTask.timeToComplete);
	
	// <Samuel Livingston> 30-Jun-2017
	// Task form submit callback. Prevent the task form from submitting. 
	// This is where ajax call will go.
	$('#taskForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		if(!validateTaskData())
			return false;
		
		$("#taskModal").css("display", "none");
		calTask.title = $("#taskTitle").val();
		calTask.description = $("#taskDescription").val();
		calTask.start = $("#taskAssignDate").val() + ' ' + $("#taskAssignTime").val();
		calTask.end = $("#taskDueDate").val() + ' ' +  $("#taskDueTime").val();
		calTask.assignDate = moment($("#taskAssignDate").val() + ' ' + $("#taskAssignTime").val());
		calTask.dueDate = moment($("#taskDueDate").val() + ' ' + $("#taskDueTime").val());
		calTask.priority = $("#taskPriority").val();
		calTask.timeToComplete = $("#taskTimeToComplete").val();
		
		$('#calendar').fullCalendar( 'updateEvent', calTask );
	});
	
	// Cancel button callback
	$('#cancelTaskBtn').off().click(function(event){
		$("#taskModal").css("display", "none");
	});
}

//Validate input as user inputs data
$('#eventForm').find("input,select,textarea").on('change input',function(e){
	validateEventData();
});
function validateEventData(){
	var isValid = true;
	
	// Validate that end time is greater than start time
	var startTime = $("#eventStartTime").val();
	var endTime = $("#eventEndTime").val();
	if(timeToDec(endTime) <= timeToDec(startTime)){
		$("#eventEndTime").siblings('.error-message').html('End time is not greater than start time');
		isValid = false;
	}
	else{
		$("#eventEndTime").siblings('.error-message').html('');
	}
		
	// Validate that the date is a date
	var isDate = moment($("#eventStartDate").val()).isValid();
	if(!isDate){
		$("#eventStartDate").siblings('.error-message').html('Please pick a date');
		isValid = false;
	}
	else{
		$("#eventStartDate").siblings('.error-message').html('');
	}
	
	// Validate that the title is not empty
	if($("#eventTitle").val() === ""){
		$("#eventTitle").siblings('.error-message').html('Title is empty');
		isValid = false;
	}
	else{
		$("#eventTitle").siblings('.error-message').html('');
	}
	
	return isValid;
}

// Validate input as user inputs data
$('#taskForm').find("input,select,textarea").on('change input', function(){
	validateTaskData();
});

function validateTaskData(){
	var isValid = true;
		
	// Validate that the assign date is a date
	var isDate = moment($("#taskAssignDate").val()).isValid();
	if(!isDate){
		$("#taskAssignDate").siblings('.error-message').html('Please pick a date');
		isValid = false;
	}
	else{
		$("#taskAssignDate").siblings('.error-message').html('');
	}
	
	// Validate that the due date is a date
	var isDate = moment($("#taskDueDate").val()).isValid();
	if(!isDate){
		$("#taskDueDate").siblings('.error-message').html('Please pick a date');
		isValid = false;
	}
	else{
		$("#taskDueDate").siblings('.error-message').html('');
	}
	
	// Validate that the due date is after or the same day as the assign date
	if(isValid){
		if(moment($("#taskDueDate").val()).isAfter(moment($("#taskAssignDate").val())) ||
				moment($("#taskDueDate").val()).isSame(moment($("#taskAssignDate").val()))){
			$("#taskDueDate").siblings('.error-message').html('');
		}
		else{
			$("#taskDueDate").siblings('.error-message').html('Due date is before assign date');
			isValid = false;
		}
	}
	
	// Validate that the due time is greater than the assign time
	// if the due date and assign date are the same day
	if(isValid){
		// If the days are the same
		if(moment($("#taskDueDate").val()).isSame(moment($("#taskAssignDate").val()))){
			
			var assignTime = $("#taskAssignTime").val();
			var dueTime = $("#taskDueTime").val();
			
			// If assign time is greater than or equal to the due time
			if(timeToDec(dueTime) <= timeToDec(assignTime)){
				$("#taskDueTime").siblings('.error-message').html('Due time must be greater than assign time');
				isValid = false;
			}
			else{
				$("#taskDueTime").siblings('.error-message').html('');
			}
		}
	}
	
	// Validate that the title is not empty
	if($("#taskTitle").val() === ""){
		$("#taskTitle").siblings('.error-message').html('Title is empty');
		isValid = false;
	}
	else{
		$("#taskTitle").siblings('.error-message').html('');
	}
	
	return isValid;
}

function timeToDec(time){
	var hoursMin = time.split(':');
	var hours = Number(hoursMin[0]);
	var mins = Number(hoursMin[1])/60;
	return hours + mins;
}