/** Main JS file for Auto Calendar
 * 
 */

// Global variables
var MODALS = document.getElementsByClassName("modal");
var EVENTS = [];
var TASKS = [];
var ID = 0; // Temporary ID

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
            	    xhr.open('GET', 'CallDatabase', true);
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
function Event( args ){
	this.id = args.id;
	this.title = args.title;
	this.allDay = false;
	this.start = args.start; // date-time
	this.end = args.end; // date-time
	this.eventType = args.eventType;	
	this.description = args.description;
	this.type = 'Event';
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
	this.color = '#FFA500';
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
	});
	
	// <Samuel Livingston> 30-Jun-2017
	// Event form submit callback. Prevent the event form from submitting. 
	// This is where ajax call will go.
	$('#eventForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		
		$("#eventModal").css("display", "none");
		var args = {
				id: ID,
				title: $("#eventTitle").val(),
				start: $("#eventStartDate").val(), // need to add time to this
				end: $("#eventStartDate").val(), // need to add time to this
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
		});
	});

	// <Samuel Livingston> 30-Jun-2017
	// Task form submit callback. Prevent the task form from submitting. 
	// This is where ajax call will go.
	$('#taskForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		
		$("#taskModal").css("display", "none");
		var args = {
				id: ID,
				title: $("#taskTitle").val(),
				start: $("#taskAssignDate").val(),
				end: $("#taskDueDate").val(),
				eventType: 0,
				description: $("#taskDescription").val(),
				assignDate: $("#taskAssignDate").val(),
				dueDate: $("#taskDueDate").val(),
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
	
	// <Samuel Livingston> 30-Jun-2017
	// Event form submit callback. Prevent the event form from submitting. 
	// This is where ajax call will go.
	$('#eventForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		
		$("#eventModal").css("display", "none");
		calEvent.title = $("#eventTitle").val();
		calEvent.description = $("#eventDescription").val();
		calEvent.start = $("#eventStartDate").val();
		calEvent.end = $("#eventStartDate").val();
		$('#calendar').fullCalendar( 'updateEvent', calEvent );
	});
	
	// Day selection callback
	$('#calendar').fullCalendar('option', 'select', function(start, end, jsEvent, view){
		normalCalendar();
		$("#eventModal").css("display", "block");
		$("#eventStartDate").val(start.format('MM/DD/YYYY'));
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
	$("#taskAssignDate").val(calTask.assignDate);
	$("#taskDueDate").val(calTask.dueDate);
	$("#taskPriority").val(calTask.priority);
	$("#taskTimeToComplete").val(calTask.timeToComplete);
	
	// <Samuel Livingston> 30-Jun-2017
	// Task form submit callback. Prevent the task form from submitting. 
	// This is where ajax call will go.
	$('#taskForm').off().submit(function(event){
		event.preventDefault();
		
		// Here we will validate the data
		
		$("#taskModal").css("display", "none");
		calTask.title = $("#taskTitle").val();
		calTask.description = $("#taskDescription").val();
		calTask.assignDate = $("#taskAssignDate").val();
		calTask.dueDate = $("#taskDueDate").val();
		calTask.priority = $("#taskPriority").val();
		calTask.timeToComplete = $("#taskTimeToComplete").val();
		
		$('#calendar').fullCalendar( 'updateEvent', calTask );
	});
	
	// Cancel button callback
	$('#cancelTaskBtn').off().click(function(event){
		$("#taskModal").css("display", "none");
	});
}