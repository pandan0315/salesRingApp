package web.shares.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.shares.database.DataHandler;
import web.shares.model.ClientGCMToken;
import web.shares.model.ErrorResponse;
import web.shares.model.PostInfo;

@Path("/gcmtoken")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientTokenResource {
	
	DataHandler dataHandler=new DataHandler();
	
	@POST
	public Response storeClientGCMTocken(String gcmToken){
		
		System.out.println(gcmToken);
		
		if(dataHandler.storeClientGCMToken(gcmToken)){
			return Response.ok().build();
		}
		return Response.status(400).entity(new ErrorResponse("can not store , please check!")).build();
		
		
		
		
		
	}
	
	@GET
	public Response sendClientGCMToken(){
		List<ClientGCMToken> tokenList=dataHandler.getAllGCMToken();
		
		GenericEntity<List<ClientGCMToken>> entity = new GenericEntity<List<ClientGCMToken>>(tokenList) {};
		
		if(tokenList!=null)
		{
			return Response.ok().entity(entity).build();
		}
		return Response.status(400).entity(new ErrorResponse("no matched user gcm tocken")).build();
	}
	

}
