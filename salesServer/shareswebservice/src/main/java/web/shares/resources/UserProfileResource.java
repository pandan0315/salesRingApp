package web.shares.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.shares.database.DataHandler;
import web.shares.model.ErrorResponse;
import web.shares.model.FollowingFriendship;
import web.shares.model.User;
import web.shares.model.UserProfile;

@Path("/{username}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileResource {
	
	
	DataHandler dataHandler=new DataHandler();
	

	/**
	 * @api {get} /{username}     Get specified user's profile
	 * @apiName  GetUserProfile
	 * @apiGroup UserProfile
	 * @apiExample {curl} Example usage:
	 *     curl -i http://localhost:8080/webapi/{username}
	 * @apiParam {String} username   specify the user name .
	 * @apiSuccess {Object} userprofile  user profile object.
	 * @apiSuccess {String}  userprofile.username     user name.
	 * @apiSuccess {String[]} userprofile.categoryLists   List of interested category
	 * @apiSuccessExample Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 *    "username":"pan",
	 *    "categoryList":
	 *          [
	 *            "education",
	 *            "hobbies",
	 *            "clothes"
	 *          ]
	 *    
	 *    
	 *  }
	 * @apiError {Object} errorResponse          return error Response object
	 * @apiError {String} errorResponse.error_message   error message
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 404 Not Found
	 * {
	 *  "error_message":"UserNotFound"
	 * }
	 * 
	 */
	@GET
	public Response getProfileByUsername(@PathParam("username")  String username){

		UserProfile userprofile=dataHandler.getUserInterest(username);
		if(userprofile!=null)
		{
			return Response.status(200).entity(userprofile).build();
		}
		
		return Response.status(404).entity(new ErrorResponse("UserNotFound")).build();
		
	}
	
	
	/**
	 * @api{post} /{username}    Add new user profile, username can be duplicated
	 * @apiName AddNewUserProfile 
	 * @apiGroup  UserProfile
	 * @apiParam {object} userprofile   specify userprofile object
	 * @apiParam {String} userprofile.username   specify username.
	 * @apiParam {String} userprofile.interest_category   specify interest category
	 * @apiParamExample {json} Request-Example:
	 * {
	 *   "username":"pan",
	 *   "interest_category":"electronics"
	 * }
	 * @apiSuccess{Object} userprofile  userprofile object
	 * @apiSuccess {String}  userprofile.username     user name.
	 * @apiSuccess {String}  userprofile.interest_category   intereste category
	 * @apiSuccessExample Success-Response:
	 * HTTP/1.1 201 Created
	 * {
	 *  "username":"pan",
	 *  "interest_category":"electronics"
	 * }
	 * @apiError {Object} errorResponse   return error Response object
	 * @apiError {String} errorResponse.error_message  error message
	 * @apiErrorExample Error-Response:
	 *    HTTP/1.1 400 Bad Request
	 *    {
	 *       "error_message":"Bad Request,Check Param!"
	 *    }
	 */
	@POST
	public Response addNewInterest(UserProfile newProfile){
		
		if(newProfile.getUsername()==null||newProfile.getInterest_category()==null)          
		{
			return Response.status(400).entity(new ErrorResponse("Bad Request,Check Param!")).build();
		}
        
		else if(dataHandler.addUserInterest(newProfile)!=null)
		   
		   {return Response.status(201).entity(newProfile).build();}
		else{
		return Response.status(400).entity(new ErrorResponse("Bad Request,request interest category already existed")).build();
		}
	}
	
	/**
	 * @api {get} /{username}/friends     Get specified user's all followed friends
	 * @apiName  GetUserFriend
	 * @apiGroup UserFriendship
	 * @apiExample {curl} Example usage:
	 *     curl -i http://localhost:8080/webapi/{username}/friends
	 * @apiParam {String} username   specify the user name.
	 * @apiSuccess {Object} followingFriendship   followingFriendship object.
	 * @apiSuccess {String}  FollowingFriendship.followingUser   specify followingUser name(request param:username).
	 * @apiSuccess {String[]} followingUser.followedLists   List of followed users.
	 * @apiSuccessExample Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 *    "followingUser": "pan"
	 *    "followedList":
	 *          [
	 *          "bob",
     *           "Alice"
	 *          ]
	 *    
	 *  }
	 * @apiError {Object} errorResponse  return error Response object
	 * @apiError {String} errorResponse.error_message   error message
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 404 Not Found
	 * {
	 *  "error_message":"UserNotFound"
	 * }
	 * 
	 */
	@GET
	@Path("/friends")
	public Response getFriendsByUsername(@PathParam("username")  String username){
		    
		FollowingFriendship allfriendsByName=dataHandler.getAllFriends(username);
		
		if(allfriendsByName!=null)
		{
			return Response.status(200).entity(allfriendsByName).build();
		}
		
		    return Response.status(404).entity(new ErrorResponse("UserNotFound")).build();
		
	}
	
	/**
	 * @api {post} /{username}/friends     Add  specified user's new followed friend
	 * @apiName  AddUserFriend
	 * @apiGroup UserFriendship
	 * @apiParam {Object} FollowingFriendship         new following-followed friendship object.
	 * @apiParam {String} FollowingFriendship.followingUser  specify following user.
	 * @apiParam {String} FollowingFriendship.followedUser  specify followed user.
	 * @apiSuccess {Object} followingFriendship   followingFriendship object.
	 * @apiSuccess {String}  FollowingFriendship.followingUser   specify followingUser name(request param:username).
	 * @apiSuccess {String} FollowingFriendship.followedUser    specify user wanted to be followed.
	 * @apiSuccessExample Success-Response:
	 *  HTTP/1.1 201 created
	 *  {
	 *    "followingUser": "pan"
	 *    "followedUser": "Alice"
	 
	 *  }
	 * @apiError {Object} errorResponse return error Response object
	 * @apiError {String} errorResponse.error_message error message
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 400 Bad Request
	 * {
	 *  "error_message":"Bad Request,Check Param"
	 * }
	 */
	@POST
	@Path("/friends")
	public Response addNewFriend(FollowingFriendship newFriend){
		
		if(newFriend.getFollowingUser()==null||newFriend.getFollowedUser()==null)          
		{
			return Response.status(400).entity(new ErrorResponse("Bad Request,Check Param!")).build();
		}
        
		else if(dataHandler.addNewFriend(newFriend)!=null)
		   
		   {return Response.status(201).entity(newFriend).build();}
		else{
		return Response.status(400).entity(new ErrorResponse("Bad Request,request new friendship already existed")).build();
		}
	}
	
	
	/**
	 * @api {delete} /{username}/friends     Delete  specified user's followed friend
	 * @apiName  DeleteUserFriend
	 * @apiGroup UserFriendship
	 * @apiParam {Object} FollowingFriendship         specified deleted following-followed friendship object.
	 * @apiParam {String} FollowingFriendship.followingUser  specify following user.
	 * @apiParam {String} FollowingFriendship.followedUser  specify followed user.
	 * @apiSuccess {Object} followingFriendship   followingFriendship object.
	 * @apiSuccess {String}  FollowingFriendship.followingUser   specify followingUser name(request param:username).
	 * @apiSuccess {String} FollowingFriendship.followedUser    specify the followed user wanted to be deleted.
	 * @apiSuccessExample Success-Response:
	 *  HTTP/1.1 204 no content
	 * @apiError {Object} errorResponse return error Response object
	 * @apiError {String} errorResponse.error_message  error message
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 400 Bad Request
	 * {
	 *  "error_message":"Bad Request,request deleted friendship not exist"
	 * }
	 */
	@DELETE
	@Path("/friends")
	public Response removeFriend(FollowingFriendship friendShip){
		if(friendShip.getFollowingUser()==null||friendShip.getFollowedUser()==null)          
		{
			return Response.status(400).entity(new ErrorResponse("Bad Request,Check Param!")).build();
		}
		
		else if(dataHandler.removeFollowedFriend(friendShip)!=null)
			   
		   {return Response.status(204).build();}
		else{
		return Response.status(400).entity(new ErrorResponse("Bad Request,request deleted friendship not exist")).build();
		}
	}
	 

}

