	package com.demo.event.service;
	
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Optional;
	import java.util.stream.Collectors;
	
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.geo.Circle;
	import org.springframework.data.geo.Distance;
	import org.springframework.data.geo.Point;
	import org.springframework.data.mongodb.core.MongoTemplate;
	import org.springframework.data.mongodb.core.query.Criteria;
	import org.springframework.data.mongodb.core.query.Query;
	import org.springframework.stereotype.Service;
	
	import com.demo.event.entity.AttendeeEventCountDTO;
	import com.demo.event.entity.Event;
	import com.demo.event.entity.EventDetail;
	import com.demo.event.repository.EventRepository;
	import com.netflix.graphql.dgs.DgsMutation;
	import com.netflix.graphql.dgs.DgsQuery;
	
	@Service
	public class EventService {
	
		@Autowired
		private EventRepository eventRepository;
	
		@Autowired
		private MongoTemplate mongoTemplate;
	
		private static final Logger logger = LoggerFactory.getLogger(EventService.class);
	
		@DgsMutation
		public Event createEvent(String name, String location, String organizerId, List<String> attendeeIds,
				String dateTime, String duration) {
			try {
				Event newEvent = new Event();
				newEvent.setName(name);
				newEvent.setLocation(location);
				newEvent.setOrganizer(organizerId);
				newEvent.setAttendees(attendeeIds);
				newEvent.setDateTime(dateTime);
				newEvent.setDuration(duration);
	
				Event savedEvent = eventRepository.save(newEvent);
	
				if (savedEvent != null) {
					return savedEvent;
				} else {
					System.out.println("Null response here ");
					return null;
				}
			} catch (Exception e) {
				logger.error("An error occurred while creating the event", e);
			}
			return null;
		}
	
		@DgsQuery
		public Event getEventById(String id) {
	
			try {
				Optional<Event> optionalEvent = eventRepository.findById(id);
				return optionalEvent.orElse(null);
			} catch (Exception e) {
				logger.error("An error occurred while fetching the event by ID: {}", id, e);
	
			}
			return null;
		}
	
		@DgsQuery
		public List<Event> listEvents(String dateTime, String duration, String organizer, List<String> attendees,
				String location) {
	
			try {
				Query query = new Query();
	
				if (dateTime != null && !dateTime.isEmpty()) {
					// Add date-time criteria
					query.addCriteria(Criteria.where("dateTime").is(dateTime));
				}
	
				if (duration != null && !duration.isEmpty()) {
					// Add duration criteria
					query.addCriteria(Criteria.where("duration").is(duration));
				}
	
				if (organizer != null && !organizer.isEmpty()) {
					// Add organizers criteria
					query.addCriteria(Criteria.where("organizer").is(organizer));
				}
	
				if (attendees != null && !attendees.isEmpty()) {
					// Add attendees criteria
					query.addCriteria(Criteria.where("attendees").in(attendees));
				}
	
				if (location != null && !location.isEmpty()) {
					// Add location criteria
					query.addCriteria(Criteria.where("location").is(location));
				}
	
				return mongoTemplate.find(query, Event.class);
			} catch (Exception e) {
				logger.error(
						"An error occurred while fetching events with criteria: [dateTime={}, duration={}, organizer={}, attendees={}, location={}]",
						dateTime, duration, organizer, attendees, location, e);
	
			}
			return null;
	
		}
	
		@DgsMutation
		public Event updateEvent(String id, String name, String location, String organizerId, List<String> attendeeIds,
				String dateTime, String duration) {
	
			try {
				Event existingEvent = eventRepository.findById(id).orElse(null);
	
				if (existingEvent != null) {
					existingEvent.setName(name);
					existingEvent.setLocation(location);
					existingEvent.setOrganizer(organizerId);
					existingEvent.setAttendees(attendeeIds);
					existingEvent.setDateTime(dateTime);
					existingEvent.setDuration(duration);
					return eventRepository.save(existingEvent);
	
				} else {
	
					return null;
				}
	
			} catch (Exception e) {
	
				e.printStackTrace();
			}
			return null;
		}
	
		private List<Event> getEventsFromDatabase(String startDate, String endDate) {
			Query query = new Query(Criteria.where("dateTime").gte(startDate).lte(endDate));
			return mongoTemplate.find(query, Event.class);
		}
	
		public List<Event> getEventsBetweenDates(String startDate, String endDate) {
			Query query = new Query(Criteria.where("dateTime").gte(startDate).lte(endDate));
	
			return mongoTemplate.find(query, Event.class);
		}
	
		@DgsQuery
		public List<AttendeeEventCountDTO> getDailyAttendeesEventCount(String startDate, String endDate) {
	
			List<Event> events = eventRepository.findByDateTimeBetween(startDate, endDate);
			List<AttendeeEventCountDTO> result = new ArrayList<>();
	
			Map<String, Map<String, Integer>> attendeeDateCountMap = groupEventsByAttendee(events);
	
			for (Map.Entry<String, Map<String, Integer>> attendeeEntry : attendeeDateCountMap.entrySet()) {
				String firstName = attendeeEntry.getKey();
				Map<String, Integer> dateCountMap = attendeeEntry.getValue();
	
				AttendeeEventCountDTO attendeeDTO = new AttendeeEventCountDTO();
				attendeeDTO.setFirstName(firstName);
	
				List<EventDetail> eventDetails = new ArrayList<>();
	
				for (Map.Entry<String, Integer> dateCountEntry : dateCountMap.entrySet()) {
					String eventDate = dateCountEntry.getKey();
					int eventCount = dateCountEntry.getValue();
	
					eventDetails.add(new EventDetail(eventDate, eventCount));
				}
	
				attendeeDTO.setEvents(eventDetails);
				result.add(attendeeDTO);
			}
	
			return result;
		}
	
		private Map<String, Map<String, Integer>> groupEventsByAttendee(List<Event> events) {
			Map<String, Map<String, Integer>> attendeeDateCountMap = new HashMap<>();
	
			for (Event event : events) {
				String attendeeFirstName = event.getName();
				String eventDate = event.getDateTime();
	
				attendeeDateCountMap.computeIfAbsent(attendeeFirstName, k -> new HashMap<>());
				attendeeDateCountMap.get(attendeeFirstName).merge(eventDate, 1, Integer::sum);
			}
	
			return attendeeDateCountMap;
		}
	
	}
