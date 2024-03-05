package com.demo.event.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.event.entity.Event;

@Repository
public interface EventRepository  extends MongoRepository<Event, String>{

	List<Event> findByDateTimeBetween(String startDate, String endDate);

}
