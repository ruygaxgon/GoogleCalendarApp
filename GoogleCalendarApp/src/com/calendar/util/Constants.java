package com.calendar.util;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;

/**
 * 
 * @author Rodrigo Gaxiola
 * Util class to provide constants
 *
 */
public class Constants {
	
	/** Application name. */
	public static final String APPLICATION_NAME = "Google Calendar";
	
	/**
	 * Constants providing CLIENT_ID for both examples.
	 */
	public static final String CLIENT_ID_REST = "247556548358-l9brvmbi9aadu5tip8d6dvpv57ti0ht1.apps.googleusercontent.com";
	public static final String CLIENT_ID_SERVLET = "856774522400-l5mespjmb1t4nebhq785spc6r39ak822.apps.googleusercontent.com";
	/**
	 * Constants providing CLIENT_SECRET for both examples.
	 */
	public static final String CLIENT_SECRET_REST = "mJcQJ0ZHyvnZkIH5Y1FD9CjU";
	public static final String CLIENT_SECRET_SERVLET = "bHNeIA2cMRWoUCv0kdQJ5MI1";
	
	/**
	 * Callback URI that google will redirect to after successful authentication
	 */
	public static final String CALLBACK_URI_REST = "http://localhost:8080/GoogleCalendarApp/restExample.html";
	public static final String CALLBACK_URI_SERVLET = "http://localhost:8080/GoogleCalendarApp/CalendarServlet.do";
	
	/**
	 * Initialize constants for GoogleAuthorizationCodeFlow
	 */
	public static final List<String> SCOPES = Arrays
			.asList(CalendarScopes.CALENDAR_READONLY);
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

}
