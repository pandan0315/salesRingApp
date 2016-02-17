package web.shares.resources;



import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.shares.service.AuthenticatorService;




@Path("/login")
public class LoginResource {
	
	
	AuthenticatorService authentication=new AuthenticatorService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String authenticateUser(@FormParam("username") String username, 
                                     @FormParam("password") String password) {

       if(authentication.authenticate(username, password)){
    	   
        return "login successful";
            
       }
       return "login failed, please check your username and password!";
       
           
    }
	
	

   
}
