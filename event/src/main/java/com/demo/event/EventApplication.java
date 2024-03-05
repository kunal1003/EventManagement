package com.demo.event;

import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.demo.event.repository")

public class EventApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(EventApplication.class, args);
        checkMongoDBConnection(context);

	}

	 private static void checkMongoDBConnection(ConfigurableApplicationContext context) {
	        try {
	            MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);
	            Set<String> collectionNames =  mongoTemplate.getCollectionNames();
	            System.out.println("Connected to MongoDB. Collections: " + collectionNames);
	        } catch (Exception e) {
	            System.err.println("Error checking MongoDB connection: " + e.getMessage());
	        }
	    }
	
}
