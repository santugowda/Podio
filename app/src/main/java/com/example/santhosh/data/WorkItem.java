package com.example.santhosh.data;


public class WorkItem implements Item {

	public final String title;


	public WorkItem(String title) {
		this.title = title;

	}
	
	@Override
	public boolean isSection() {
		return false;
	}

}
