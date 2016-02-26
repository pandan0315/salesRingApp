package web.shares.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	

	private String userName;
	private String password;
	private String email;
	private String fullName;
	
	
	
	public User(){
		
		
	}
	public User(String username, String fullname){
		this.userName=username;
		this.fullName=fullname;
		
	}
	
	public User(String userName,String fullname,String email,String password) {
	   this.fullName=fullname;
		this.userName = userName;
		this.password = password;
		this.email=email;
	
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
