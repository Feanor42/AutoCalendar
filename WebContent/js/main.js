/** Main JS file for Auto Calendar
 * 
 */

// Global variables
var MODALS = document.getElementsByClassName("modal");
var SELECTED_DAY;
var EVENTS = [];
var TASKS = [];

// <Samuel Livingston> 30-Jun-2017
// Options for fullcalendar
var initialCalendarOptions = {
    	customButtons: {
            addEvent: {
                text: 'Add Event',
                click: function() {
                    selectDay(); // First select a day for the event
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
    	eventClick: function(calEvent, jsEvent, view) {
    		viewEvent(calEvent);
        },
        dayClick: function(date, jsEvent, view) {
            $('#calendar').fullCalendar('changeView', 'agendaDay',date.format());
        },
        eventMouseover: function( calEvent, jsEvent, view ) { 
        	this.appendChild(calEvent.details);
        },
        eventMouseout: function( calEvent, jsEvent, view){
        	this.removeChild(calEvent.details);
        },
        selectable: false,
        select: function(start, end, jsEvent, view){
        	addEventDialog(start, end, jsEvent, view);
        }
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
function Event(id, title, startTime, endTime, eventType, description){
	this.id = id;
	this.title = title;
	this.allDay = false;
	this.start = startTime; // date-time
	this.end = endTime; // date-time
	this.eventType = eventType;	
	this.description = description;
	
	this.details = document.createElement("span");
	var desc = document.createElement("p");
	desc.innerHTML = 'Description: ' + description;
	this.details.appendChild(desc);
	this.details.classList.add('details');
}

// <Samuel Livingston> 30-Jun-2017
// Task object. Inherits from event object.
function task(id, title, startTime, endTime, eventType, description){
	event.call(this, id, title, startTime, endTime, eventType, description);
	task.prototype = Object.create(event.prototype);
	task.prototype.constructor = task;
	this.assignDate = assignDate;
	this.dueDate = dueDate;
	this.priority = priority;
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
function selectDay(){
	$('#calendar').fullCalendar('option', selectDayCalendarOptions);
	$('#pageTitle').addClass("pageTitleMove");
	$('#selectDayTitle').addClass("selectDayTitleMove");
	$('#calendar').fullCalendar('changeView', 'month');
}

function addTask(){
	alert('Added task!');
}

// <Samuel Livingston> 30-Jun-2017
// Open the event dialog when a date is picked.
function addEventDialog(start, end, jsEvent, view){
	$('#calendar').fullCalendar('option', initialCalendarOptions);
	$('#pageTitle').removeClass("pageTitleMove");
	$('#selectDayTitle').removeClass("selectDayTitleMove");
	$("#eventModal").css("display", "block");
	SELECTED_DAY = start;
	$("#eventStartDate").val(start.format('MM/DD/YYYY'));
}

// <Samuel Livingston> 30-Jun-2017
// Callback for date input in event dialog. Closes the event
// dialog and allows user to pick a new date.
document.getElementById('eventStartDate').onclick = function(){
	$("#eventModal").css("display", "none");
	selectDay();
};

// <Samuel Livingston> 30-Jun-2017
// cancelEventBtn callback. Allows user to cancel edits/creation of event.
document.getElementById('cancelEventBtn').onclick = function(event){
	$("#eventModal").css("display", "none");
}

// <Samuel Livingston> 30-Jun-2017
// Event form callback. Prevent the event form from submitting. 
document.getElementById('eventForm').onsubmit = function(event){
	event.preventDefault();
	return false;
}

// <Samuel Livingston> 30-Jun-2017
// Save event button callback
document.getElementById('saveEventBtn').onclick = function(event){
	$("#eventModal").css("display", "none");
	newEvent = new Event(0, $("#eventTitle").val(), SELECTED_DAY.format(),	SELECTED_DAY.format(), 0, $("#eventDescription").val() );
	$('#calendar').fullCalendar( 'renderEvent', newEvent, true);
	EVENTS.push(newEvent);
}

// <Samuel Livingston> 30-Jun-2017
// View event when clicked
function viewEvent(calEvent){
	$("#eventModal").css("display", "block");
	$("#eventTitle").val(calEvent.title);
	$("#eventDescription").val(calEvent.description);
	$("#eventStartDate").val(calEvent.start.format('MM/DD/YYYY'));
}