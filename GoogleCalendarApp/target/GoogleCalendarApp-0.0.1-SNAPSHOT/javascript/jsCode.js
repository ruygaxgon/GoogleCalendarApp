$(document).ready(function() {
	var eventsCache;

	//This function will format the body of the email.
	var shareMyEventString = function(){
		var bodyString;
		if(eventsCache){
			bodyString = "Future events: ";
			eventsCache.forEach(function(item){
				bodyString += item.title + " on " + item.start.split('T')[0] + ", ";
			});
			//Remove unwanted spaces and last comma.
			bodyString = bodyString.trim();
			bodyString = bodyString.substring(0, bodyString.length - 1);
		} else {
			bodyString = "No events to share"; 
		}
		return bodyString;
	};

	//Verify email
	var emailValidator = {
			validateEmail: function(value) {
				var regex = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
				return (regex.test(value)) ? true : false;
			},
			validateEmails: function(string) {
				var self = emailValidator;
				var result = string.replace(/\s/g, "").split(/,|;/);
				for(var i = 0;i < result.length;i++) {
					if(!self.validateEmail(result[i])) {
						return false;
					}
				}
				return true;
			}
	};

	//Initialize the calendar...
	$('#calendar').fullCalendar({
		events: function(start, end, timezone, callback) {
			$.ajax({
				url: window.location.origin + '/GoogleCalendarApp/test/calendar',
				type: 'GET',
				success: function(doc) {
					//Put the events in the cache for future reference
					eventsCache = doc.calendarEvent;
					var events = [];
					doc.calendarEvent.forEach(function(item) {
						//Create the events in the calendar with just the summary, star and end date.
						events.push({
							title: item.title,
							start: item.start,
							end: item.end
						});
					});
					callback(events);
				}
			});
		}
	});

	//Button to open new Gmail compose and share events.
	$('#shareButton').click(function(){
		if(emailValidator.validateEmails($('#emails').val())){
			window.open('https://mail.google.com/mail/?view=cm&fs=1&to=' + $('#emails').val() + '&body=' + shareMyEventString());
		} else {
			alert('Please enter a valid email in the following format:　/　こちらのパターンで記入してください：　 xyz@mail.com, abc@mail.com');
		}
	});

});