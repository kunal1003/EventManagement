package com.demo.event.resolver;

import com.demo.event.entity.AttendeeEventCountDTO;
import com.demo.event.entity.Event;
import com.demo.event.service.EventService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class EventResolver {

	@Autowired
	private EventService eventService;
	

	@DgsData(parentType = "Query", field = "getEventDetail")
	public Event getEventById(@InputArgument String id) {
		return eventService.getEventById(id);
	}

	@DgsData(parentType = "Query", field = "listEvents")
	public List<Event> listEvents(@InputArgument String dateTime, @InputArgument String duration,
			@InputArgument String organizer, @InputArgument List<String> attendees, @InputArgument String location) {
		return eventService.listEvents(dateTime, duration, organizer, attendees, location);
	}

	@DgsData(parentType = "Mutation", field = "createEvent")
	public Event createEvent(@InputArgument String name, @InputArgument String location,
			@InputArgument String organizerId, @InputArgument List<String> attendeeIds, @InputArgument String dateTime,
			@InputArgument String duration) {

		Event createdEvent = eventService.createEvent(name, location, organizerId, attendeeIds, dateTime, duration);
		return createdEvent;
	}

	@DgsData(parentType = "Mutation", field = "updateEvent")
	public Event updateEvent(String id, String name, String location, String organizerId, List<String> attendeeIds,
			String dateTime, String duration) {

		Event updatedEvent = eventService.updateEvent(id, name, location, organizerId, attendeeIds, dateTime, duration);


		return updatedEvent;
	}
	
	@DgsData(parentType = "Query", field = "getDailyAttendeesEventCount")
	 public List<AttendeeEventCountDTO> getDailyAttendeesEventCount(@InputArgument String startDate, @InputArgument String endDate) {
	     return eventService.getDailyAttendeesEventCount(startDate, endDate);
	 }
	

	

}
