package web.shares.model;

public class User {
	
	
	//private Long id;
	private String userName;
	private String password;
	//private String email;
	
	
	public User(){
		
		
	}
	
	public User(String userName, String password) {
	
		//this.id = id;
		this.userName = userName;
		this.password = password;
		//this.email=email;
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
	
	
	
	

}
