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
    	return new User(user.getUserName(),user.getFullName());

}
    public boolean checkUser(String name,String email){
    	
    	User user1=dataHandler.getUserByname(name);
    	User user2=dataHandler.getUserByemail(email);
    	return ((user1!=null)&&(user2!=null));
    }
    
   
    
}
