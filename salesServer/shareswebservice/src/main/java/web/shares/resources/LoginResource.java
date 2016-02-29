package web.shares.resources;



import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.shares.model.ErrorResponse;
import web.shares.model.User;
import web.shares.model.UserProfile;
import web.shares.service.AuthenticatorService;




@Path("/login")
public class LoginResource {
	
	
	AuthenticatorService authentication=new AuthenticatorService();
	
	/**
	 * @api{post} /login  user login to the saleinfo app.
	 * @apiName  LoginToApp
	 * @apiGroup Authentication
	 * @apiParam (Login @FormParam) {String} email  emailaddress for entering the salesinfo app.
	 * @apiParam (Login @FormParam) {String} password   password for entering the salesinfo app.
	 * @apiSuccessExample Success-Response:
	 *  HTTP/1.1 200 OK
	 * @apiError {Object} errorResponse return error Response object
	 * @apiError {String} errorResponse.error_message  error message
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 401 Unauthorized
	 * {
	 *  "error_message":"Unanthorized User!"
	 * }
	 * 
     */
  	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("email") String email, 
                                     @FormParam("password") String password) {
  		
  		UserProfile user=authentication.authenticate(email, password);

       if(user!=null){
    	   
        return Response.status(200).entity(user).build();
            
       }
       return Response.status(401).entity(new ErrorResponse("Unauthorized User!")).build();
       
           
    }
	
	

   
}
