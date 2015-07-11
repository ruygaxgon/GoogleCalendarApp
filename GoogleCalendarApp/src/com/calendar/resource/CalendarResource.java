package com.calendar.resource;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.calendar.access.AuthHelper;
import com.calendar.entity.CalendarEvent;
import com.calendar.util.Constants;
import com.google.common.collect.Lists;

/**
 * 
 * @author Rodrigo Gaxiola
 * RESTful service that returns Google Calendar Events.
 *
 */
@Path("/calendar")
public class CalendarResource {
	
	/**
	 * Service check for the authorization code to call the authorization page
	 * or to retrieve the calendar events if it is already authorized
	 * @param code
	 * 			authentication code provided by google
	 * @return List of events in json format
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CalendarEvent> checkAuth(@QueryParam("code") String code) throws IOException {
		List<CalendarEvent> result = Lists.newArrayList();
		AuthHelper ah = new AuthHelper(Constants.CLIENT_ID_REST, Constants.CLIENT_SECRET_REST);
		try {
			if(code == null){
				ah.sendGet(Constants.CALLBACK_URI_REST);
			} else {
				result = ah.getCalendarEvents(code, Constants.CALLBACK_URI_REST);
			}
		} catch (Exception e) {
			System.err.println("Error getting calendars");
		}
		return result;
	}

}
