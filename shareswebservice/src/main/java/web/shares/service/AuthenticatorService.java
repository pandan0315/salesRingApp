package web.shares.service;


import web.shares.database.DataHandler;
import web.shares.model.User;


public class AuthenticatorService {
	
	DataHandler dataHandler=new DataHandler();
   

    public boolean authenticate(String name,String password){
    	
    	
    	User user= dataHandler.getUserByNameAndPassword(name, password);
    
    	if(user==null)
    	{
    		return false;
    	}
    	return true;

}
    public boolean checkUser(String name){
    	
    	User user=dataHandler.getUserByname(name);
    	return user!=null;
    }
    
}
