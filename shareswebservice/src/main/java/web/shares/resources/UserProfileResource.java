package web.shares.resources;

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

import web.shares.database.DataHandler;
import web.shares.model.FollowingFriendship;
import web.shares.model.User;
import web.shares.model.UserProfile;

@Path("/{username}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileResource {
	
	
	DataHandler dataHandler=new DataHandler();
	
 
  
	
	@GET
	public UserProfile getProfileByUsername(@PathParam("username")  String username){

		return dataHandler.getUserInterest(username);
		
	}
	
	@POST
	public UserProfile addNewInterest(UserProfile newInterest){
        
		return dataHandler.addUserInterest(newInterest);
	}
	
	@GET
	@Path("/friends")
	public FollowingFriendship getFriendsByUsername(@PathParam("username")  String username){
		
		return dataHandler.getAllFriends(username);
		
	}
	
	@POST
	@Path("/friends")
	public FollowingFriendship addNewFriend(FollowingFriendship newFriend){
		return dataHandler.addNewFriend(newFriend);
	}
	
	@DELETE
	@Path("/friends")
	public void removeFriend(FollowingFriendship friendShip){
		dataHandler.removeFollowedFriend(friendShip);
	}
	 

}


