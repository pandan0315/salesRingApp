package web.shares.service;


import web.shares.database.DataHandler;
import web.shares.model.User;


public class AuthenticatorService {
	
	DataHandler dataHandler=new DataHandler();
   

    public User authenticate(String email,String password){
    	
    	
    	User user= dataHandler.getUserByNameAndPassword(email, password);
    
    	if(user==null)
    	{
    		return null;
    	}
    	return new User(user.getUserName(),user.getEmailAddress());

}
    public boolean checkUser(String email){
    	
    	User user=dataHandler.getUserByname(email);
    	return user!=null;
    }
    
}
