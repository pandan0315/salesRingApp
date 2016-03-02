package web.shares.model;

public class ClientGCMToken {
	
	//private String username;
	private String token;
	
	public ClientGCMToken(){
		
	}
	public ClientGCMToken(String token) {
		super();
		
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	

}
