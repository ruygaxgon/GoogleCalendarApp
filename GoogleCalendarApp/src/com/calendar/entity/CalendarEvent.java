package com.calendar.entity;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author Rodrigo Gaxiola
 * Model to map calendar events
 *
 */
@XmlRootElement
public class CalendarEvent {

	String title;
	String start;
	String end;
	
	public CalendarEvent(){}
	
	public CalendarEvent(String title, String start, String end) {
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
