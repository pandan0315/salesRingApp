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
import web.shares.model.ErrorResponse;
import web.shares.model.User;
import web.shares.service.AuthenticatorService;

@Path("/signup")
public class SignupResource {

	
     AuthenticatorService authentication=new AuthenticatorService();
	 DataHandler dataHandler=new DataHandler();

	/**
	 * @api{post} /signup user register the saleinfo app.
	 * @apiName SignupToApp
	 * @apiGroup Authentication
	 * @apiParam (Signup @FormParam) {String} username username needed to
	 *           register the salesinfo app.
	 * @apiParam (Signup @FormParam) {String} password password needed to
	 *           register the salesinfo app.
	 * @apiSuccessExample Success-Response: 
	 *         HTTP/1.1 201 Created
	 * @apiError {Object} errorResponse return error Response object
	 * @apiError {String} errorResponse.error_message error message
	 * @apiErrorExample {json} Error-Response:
	 *      HTTP/1.1 400 Bad Request
	 *       {
	 *           "error_message":"Bad Request,User name already existed"
	 *       }
	 * 
	 */
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response Signup(@FormParam("username") String username,
    		                 
                                     @FormParam("password") String password) {

   if(!authentication.checkUser(username)){
	   
	   User newUser=new User(username,password);
	   dataHandler.storeUserAccount(newUser);
    	  
	   return  Response.status(201).entity("User created successfully!").build();
            
       }
       
       return  Response.status(400).entity(new ErrorResponse("user name already existed!")).build();
       
           
    }
	
}
