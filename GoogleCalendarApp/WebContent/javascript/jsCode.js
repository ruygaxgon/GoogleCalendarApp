$(document).ready(function() {
	var eventsCache;

	/**
	 * User in REST example.
	 * This function will format the body of the email.
	 * @return String
	 * 			String contains the body of the email with the future events or a default value
	 */
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

	/**
	 * Email validator to ensure proper input of the user.
	 * @return boolean
	 * 			Indicates if the email is valid or not
	 */
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
	
	/**
	 * Get query parameter from actual page url
	 * @param name
	 * 			name of the parameter
	 * @return value of parameter
	 */
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	/**
	 * Verify if the url has the parameter code and appends it to the REST call
	 * @return queryParam
	 */
	function hasCode() {
		var code = getParameterByName("code");
		if(code){
			return "?code=" + code;
		}
		return "";
	}
	
	/**
	 * Initialize the calendar for REST example
	 */
	$('#calendar').fullCalendar({
		events: function(start, end, timezone, callback) {
			$.ajax({
				url: window.location.origin + '/GoogleCalendarApp/test/calendar' + hasCode(),
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

	/**
	 * Button to open a new Gmail compose and fill it with events and email(s)
	 */
	$('#shareButton').click(function(){
		if(emailValidator.validateEmails($('#emails').val())){
			window.open('https://mail.google.com/mail/?view=cm&fs=1&to=' + $('#emails').val() + '&body=' + shareMyEventString());
		} else {
			alert('Please enter a valid email in the following format:　/　こちらのパターンで記入してください：　 xyz@mail.com, abc@mail.com');
		}
	});

});