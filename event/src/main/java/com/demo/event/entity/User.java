package com.demo.event.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User {
	 	@Id
	    private String id;
	    public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}


		private String firstName;
		private String lastName;
		
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

		
		@Override
		public String toString() {
			return "User [firstName=" + firstName + ", lastName=" + lastName + "]";
		}
	    
	

}
