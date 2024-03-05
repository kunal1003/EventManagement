- [Prerequisites](#prerequisites)
  - [Sample Queries and Mutations](#sample-queries-and-mutations)
    - [GraphQL Playground or IDE](#graphql-playground-or-ide)
  


## Prerequisites

- Java 17
- MongoDB 7.0.6
- Maven 

##Sample Queries and Mutations

mutation {
  createEvent(
    name: "event11"
    location: "Sample Location"
    organizerId: "organizer123"
    attendeeIds: ["attendee3", "attendee4"]
    dateTime: "2024-03-04"
    duration: "2 hours"
  ) {
    id
    name
    location
    organizer
    attendees
    dateTime
    duration
  }
}

query {
  listEvents(
    duration: ["2"]       # Replace with your desired duration range
  ) {
    id
    name
    location
    organizer
    attendees
    dateTime
    
    
  }
}

mutation {
  updateEvent(
    id: "65e635a05798635a91a7de89"
    name: "Updated Event Name1"
    location: "Updated Event Location"
    organizerId: "updatedOrganizerId"
    attendeeIds: ["updatedAttendee1", "updatedAttendee2"]
    dateTime: "2024-03-05T15:00:00"
    duration: "3 hours"
  ) {
    id
    name
    location
    organizer
    attendees
    dateTime
    duration
  }
}


##GraphQL Playground or IDE
http://localhost:8082/graphiql