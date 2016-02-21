package web.shares.model;

public class ErrorResponse {
	
	
	private String error_message;
	
	public ErrorResponse(){
		
	}
	public ErrorResponse( String error_message) {
		super();
		
		this.error_message = error_message;
	}


	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	

}
