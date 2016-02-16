package web.shares.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.shares.database.DataHandler;
import web.shares.model.User;
import web.shares.service.AuthenticatorService;

@Path("/signup")
public class SignupResource {

	
     AuthenticatorService authentication=new AuthenticatorService();
	 DataHandler dataHandler=new DataHandler();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String Signup(@FormParam("username") String username,
    		                 
                                     @FormParam("password") String password) {

   if(!authentication.checkUser(username)){
	   
	   User newUser=new User(username,password);
	   dataHandler.storeUserAccount(newUser);
    	  
	   return "user successfully created"; 
            
       }
      //do insert the data to database
       
       return "Account is already existed!";
       
           
    }
	
}
