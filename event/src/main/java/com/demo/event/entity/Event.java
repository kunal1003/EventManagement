package com.demo.event.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class Event {
	@Id
    private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public List<String> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<String> attendees) {
		this.attendees = attendees;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	private String name;
    private String location;
    private String organizer;
    private List<String> attendees;
    private String dateTime;
    private String duration;

}
