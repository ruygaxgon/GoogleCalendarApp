package com.calendar.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.calendar.access.AuthHelper;
import com.calendar.entity.CalendarEvent;
import com.calendar.util.Constants;

/**
 * 
 * @author Rodrigo Gaxiola
 * Servlet implementation returns Google Calendar Events
 * 
 */
@WebServlet("/CalendarServlet.do")
public class CalendarServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default doGet method to process servlet request.
	 * Method uses AuthHelper to authenticate the user with client_id and client_secret,
	 * 	this will execute again as the callback_uri call to this servlet then retrieve calendar events
	 * 	with the code passed via callback_uri.
	 * @param HttpSevletRequest request, HttpServletResponse response
	 * @throws ServletException, IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthHelper authHelper = new AuthHelper(Constants.CLIENT_ID_SERVLET, Constants.CLIENT_SECRET_SERVLET);
		String url = authHelper.buildLoginUrl(Constants.CALLBACK_URI_SERVLET);
		//If the request does not have the code, redirect to the authorization page
		if(!request.getParameterMap().containsKey("code")){
			response.sendRedirect(url);
		} else {
			//If the request does have the code, get the events, format the result to json and forward it to the jsp
			List<CalendarEvent> eventList = authHelper.getCalendarEvents(request.getParameter("code"), Constants.CALLBACK_URI_SERVLET);
			JSONArray toJsonArray = new JSONArray(eventList);
			String events = toJsonArray.toString();
			request.setAttribute("events", events);
			RequestDispatcher rd = request.getRequestDispatcher("/servletExample.jsp");
			rd.forward(request, response);
		}
	}

}
