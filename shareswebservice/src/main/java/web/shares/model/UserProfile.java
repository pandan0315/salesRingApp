package web.shares.model;

import java.util.ArrayList;

public class UserProfile {

	
	
	private String username;
	private String interest_category;
	private ArrayList<String> categoryLists;
	
	public UserProfile(){
		
	}

	
	public UserProfile( String username, ArrayList<String> categoryLists) {
		super();
		
		this.username = username;
		this.categoryLists = categoryLists;
	}


	public UserProfile(String username, String interest_category) {
		super();
		
		this.username = username;
		this.interest_category = interest_category;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public ArrayList<String> getCategoryLists() {
		return categoryLists;
	}


	public void setCategoryLists(ArrayList<String> categoryLists) {
		this.categoryLists = categoryLists;
	}

	public String getInterest_category() {
		return interest_category;
	}

	public void setInterest_category(String interest_category) {
		this.interest_category = interest_category;
	}

	

	
}
