package com.demo.event.entity;

import java.util.List;

import lombok.Data;


@Data
public class AttendeeEventCountDTO {
	
	
		public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<EventDetail> getEvents() {
		return events;
	}
	public void setEvents(List<EventDetail> events) {
		this.events = events;
	}
		private String firstName;
	    private String lastName;
	    private List<EventDetail> events;
	    
	    private String startDate;
	    private String endDate;

}
