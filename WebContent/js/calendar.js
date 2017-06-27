/** Specify all the options/callbacks for fullcalendar
 * 
 */
$(document).ready(function() {
    // page is now ready, initialize the calendar...
    $('#calendar').fullCalendar({
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
    		left:   'prev,next addEvent addTask callDatabase title',
            center: '',
            right:  'today'
    	},
    	eventClick: function(calEvent, jsEvent, view) {

            alert('Event: ' + calEvent.title);
            alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            alert('View: ' + view.name);

            // change the border color just for fun
            $(this).css('border-color', 'red');

        }
    	
        // put your options and callbacks here
    })
});