package com.demo.event.entity;

import lombok.Data;

@Data
public class EventDetail {
	
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCount() {
		return count;
	}
	public EventDetail(String date, int count) {
		super();
		this.date = date;
		this.count = count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private int count;

}
