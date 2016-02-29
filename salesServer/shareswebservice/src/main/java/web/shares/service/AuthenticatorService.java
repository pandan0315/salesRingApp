package web.shares.service;


import web.shares.database.DataHandler;
import web.shares.model.User;
import web.shares.model.UserProfile;


public class AuthenticatorService {
	
	DataHandler dataHandler=new DataHandler();
   

    public UserProfile authenticate(String email,String password){
    	
    	
    	User user= dataHandler.getUserByNameAndPassword(email, password);
    	
    
    	if(user==null)
    	{
    		return null;
    	}
    	return dataHandler.getUserInterest(user.getUserName());

}
    public boolean checkUser(String name,String email){
    	
    	User user1=dataHandler.getUserByname(name);
    	User user2=dataHandler.getUserByemail(email);
    	return ((user1!=null)&&(user2!=null));
    }
    
   
    
}
