package web.shares.model;

import java.util.ArrayList;

public class UserProfile {

	
	
	private String username;
	private String fullname;
	private String interest_category;
	private ArrayList<String> categoryLists;
	private String encodeProfileIcon;
	
	public UserProfile(){
		
	}

	
	public UserProfile( String username, String fullname,ArrayList<String> categoryLists) {
		super();
		
		this.username = username;
		this.fullname = fullname;
		this.categoryLists = categoryLists;
	}

	
	public UserProfile(String username, String fullname,String interest_category) {
		super();
		
		this.username = username;
		this.fullname = fullname;
		this.interest_category = interest_category;
	}

	
	

	public UserProfile(String username, String fullname, ArrayList<String> categoryLists, String encodeProfileIcon) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.categoryLists = categoryLists;
		this.encodeProfileIcon = encodeProfileIcon;
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


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getEncodeProfileIcon() {
		return encodeProfileIcon;
	}


	public void setEncodeProfileIcon(String encodeProfileIcon) {
		this.encodeProfileIcon = encodeProfileIcon;
	}


	

	
	

	
}
