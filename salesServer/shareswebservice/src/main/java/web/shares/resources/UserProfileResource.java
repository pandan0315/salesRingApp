package web.shares.resources;



import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.shares.database.DataHandler;
import web.shares.model.ErrorResponse;
import web.shares.model.FollowingFriendship;

import web.shares.model.UserProfile;
import web.shares.service.PostInfoService;

@Path("/{username}/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileResource {
	
	
	DataHandler dataHandler=new DataHandler();
	PostInfoService postedImage=new PostInfoService();
	

	/**
	 * @api {get} /{username}/profile     Get specified user's profile
	 * @apiName  GetUserProfile
	 * @apiGroup UserProfile
	 * @apiExample {curl} Example usage:
	 *     curl -i http://localhost:8080/webapi/{username}/profile
	 * @apiParam {String} username   specify the user name .
	 * @apiSuccess {Object} userprofile  user profile object.
	 * @apiSuccess {String}  userprofile.username     user name.
	 * 
	 * @apiSuccess {String[]} userprofile.categoryLists   List of interested category
	 * @apiSuccessExample Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 *    "username":"pan",
	 *    "fullname":"Pan Dan"
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
	 * @api{post} /{username}/profile    Add new user profile, username can be duplicated
	 * @apiName EditUserProfile 
	 * @apiGroup  UserProfile
	 * @apiParam {object} userprofile   specify userprofile object
	 * @apiParam {String} userprofile.username   specify username.
	 * @apiParam {String} userprofile.fullname   specify fullname
	 * @apiParam {String} userprofile.interest_category   specify interest category
	 * @apiParamExample {json} Request-Example:
	 * {
	 *   "username":"pan",
	 *   "fullname":"Pan Dan"
	 *   "interest_category":"electronics"
	 *   "encodeprofileimage":"da;lfgje00ejkdkdklakdjjdftgkrdlasfg"(if profile image not changed,then leave this null)
	 * }
	 * @apiSuccess{Object} userprofile  userprofile object
	 * @apiSuccess {String}  userprofile.username     user name.
	 *  @apiSuccess {String}  userprofile.fullname     fullname.
	 * @apiSuccess {String}  userprofile.interest_category   intereste category
	 * @apiSuccessExample Success-Response:
	 * HTTP/1.1 201 Created
	 * @apiError {Object} errorResponse   return error Response object
	 * @apiError {String} errorResponse.error_message  error message
	 * @apiErrorExample Error-Response:
	 *    HTTP/1.1 400 Bad Request
	 *    {
	 *       "error_message":"Bad Request,Check Param!"
	 *    }
	 */
	@POST
	public Response editProfile(UserProfile newProfile){
		

		
			if(dataHandler.editProfile(newProfile)==null){
				return Response.status(400).build();
			}
			if(newProfile.getEncodeProfileIcon()!=null){
			this.postedImage.convertStringtoImage(newProfile.getEncodeProfileIcon(), newProfile.getUsername()+".jpeg");}
			
			return Response.status(201).entity(newProfile).build();
			
		
		
		
	}
	
	
	/**
	 * @api {get} /{username}/profile/friends Get specified user's all followed friends
	 * @apiName GetUserFriend
	 * @apiGroup UserFriendship
	 * @apiExample {curl} Example usage: curl -i
	 *             http://localhost:8080/webapi/{username}/friends
	 * @apiParam {String} username specify the user name.
	 * @apiSuccess {Object} followingFriendship followingFriendship object.
	 * @apiSuccess {String} FollowingFriendship.followingUser specify
	 *             followingUser name(request param:username).
	 * @apiSuccess {String[]} followingUser.followedLists List of followed
	 *             users.
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * { "followedList": [
	 *                    { 
	 *                    "fullName": "Gu yuqing", 
	 *                    "userName": "yuqing"
	 *                     }, 
	 *                    {
	 *                    "fullName": "Liu zhehuan",
	 *                     "userName": "zhehuan" 
	 *                     },
	 *                      {
	 *                    "fullName": "Khalid Mahgoub",
	 *                     "userName": "khalid" 
	 *                     }
	 *                    ], 
	 *  "followingUser": "pan"
	 *   }
	 * @apiError {Object} errorResponse return error Response object
	 * @apiError {String} errorResponse.error_message error message
	 * @apiErrorExample {json} Error-Response: HTTP/1.1 404 Not Found {
	 *                  "error_message":"UserNotFound" }
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
	 * @api {post} /{username}/profile/friends     Add  specified user's new followed friend
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
		return Response.status(400).entity(new ErrorResponse("Bad Request,request new friendship already existed or not existed in the system")).build();
		}
	}
	
	
	/**
	 * @api {delete} /{username}/profile/friends     Delete  specified user's followed friend
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


