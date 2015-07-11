package com.calendar.access;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import com.calendar.entity.CalendarEvent;
import com.calendar.util.Constants;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.common.collect.Lists;

/**
 * 
 * @author Rodrigo Gaxiola
 * A helper class to allow Google Calendar Authorization
 * 
 */
public final class AuthHelper {
	
	private String stateToken;
	private GoogleAuthorizationCodeFlow flow;

	/**
	 * Constructor initializes the Google Authorization Code Flow with CLIENT
	 * ID, SECRET, and SCOPE also generates the state token
	 * @param client_id and client_secret
	 * 			Sending this as for example reasons using two different applications
	 * 			declared in the Google Developer Console.
	 */
	public AuthHelper(String client_id, String client_secret) {
		flow = new GoogleAuthorizationCodeFlow.Builder(Constants.HTTP_TRANSPORT,
				Constants.JSON_FACTORY, client_id, client_secret, Constants.SCOPES).build();
		generateStateToken();
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, scope and
	 * setting a state token
	 * @param callback_uri
	 * 			Both examples use different callback_uris
	 * @return login url
	 */
	public String buildLoginUrl(String callback_uri) {
		final GoogleAuthorizationCodeRequestUrl url = flow
				.newAuthorizationUrl();
		return url.setRedirectUri(callback_uri).setState(stateToken).build();
	}

	/**
	 * Generates a secure state token
	 */
	private void generateStateToken() {
		SecureRandom sr1 = new SecureRandom();
		stateToken = "stateToken;" + sr1.nextInt();
	}

	/**
	 * Calls generateStateToken and buildLoginUrl,
	 * open a new browser window with the login url and
	 * ask for user authorization.
	 * @param callback_uri
	 * 			Both examples use different callback_uris
	 * @throws Exception
	 */
	public void sendGet(String callback_uri) throws Exception {
		generateStateToken();
		String url = buildLoginUrl(callback_uri);
		
		//Open new browser with the login url
		if(Desktop.isDesktopSupported())
		{
		  Desktop.getDesktop().browse(new URI(url));
		}
	}

	/**
	 * Expects an Authentication Code, and makes an authenticated request for
	 * the user's calendar information
	 * 
	 * @return Calendar events list to utilize it as a json
	 * @param authCode
	 *            authentication code provided by google
	 *        callback_uri
	 *        	  Both examples use different callback_uris
	 * @throws IOException
	 */
	public List<CalendarEvent> getCalendarEvents(String authCode, String callback_uri)
			throws IOException {

		// Create the token, retrieve credential and pull calendar.
		GoogleTokenResponse response = flow.newTokenRequest(authCode)
				.setRedirectUri(callback_uri).execute();
		Credential credential = flow.createAndStoreCredential(response, null);
		Calendar calendar = new Calendar.Builder(Constants.HTTP_TRANSPORT, Constants.JSON_FACTORY,
				credential).setApplicationName(Constants.APPLICATION_NAME).build();

		// Set new limit date
		Date date = new Date(
				new DateTime(System.currentTimeMillis()).getValue());
		java.util.Calendar utilCalendar = java.util.Calendar.getInstance();
		utilCalendar.setTime(date);
		utilCalendar.add(java.util.Calendar.DATE, 14);
		date = utilCalendar.getTime();

		// List the next events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = calendar.events().list("primary").setTimeMin(now)
				.setTimeMax(new DateTime(date)).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		List<CalendarEvent> result = Lists.newArrayList();
		if (items.size() != 0) {
			//Iterate by each event
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				DateTime end = event.getEnd().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				if (end == null) {
					end = event.getEnd().getDate();
				}

				result.add(new CalendarEvent(event.getSummary(), start
						.toStringRfc3339(), end.toStringRfc3339()));
			}
		}
		return result;

	}

}
