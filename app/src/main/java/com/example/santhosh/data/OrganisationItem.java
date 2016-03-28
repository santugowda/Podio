package com.example.santhosh.data;

public class OrganisationItem implements Item {

	private final String title;
	
	public OrganisationItem(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	@Override
	public boolean isSection() {
		return true;
	}

}
