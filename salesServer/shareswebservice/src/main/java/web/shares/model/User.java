package web.shares.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	

	private String userName;
	private String password;
	private String email;
	private String fullName;
	private ArrayList<String> userInterests;
	
	
	
	
	
	public User(){
		
		
	}
	
	
	public User(String userName, String fullName) {
		super();
		this.userName = userName;
		this.fullName = fullName;
	}


	public User(String username, String fullname,ArrayList<String> userInterests){
		this.userName=username;
		this.fullName=fullname;
		this.userInterests=userInterests;
		
	}
	
	public User(String userName,String fullname,String email,String password) {
	   this.fullName=fullname;
		this.userName = userName;
		this.password = password;
		this.email=email;
	
	}
	
	
	public ArrayList<String> getUserInterests() {
		return userInterests;
	}
	public void setUserInterests(ArrayList<String> userInterests) {
		this.userInterests = userInterests;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return email;
	}

	public void setEmailAddress(String emailAddress) {
		this.email = emailAddress;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	

}
