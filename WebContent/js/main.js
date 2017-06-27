/** Main javascript file for Auto Calendar
 * 
 */

function addEvent(){
	alert('Added event!');
	var myEvent = {
			  title:"Test Event",
			  allDay: true,
			  start: new Date(),
			  end: new Date()
	};
	$('#calendar').fullCalendar( 'renderEvent', myEvent );
}

function addTask(){
	alert('Added task!');
}