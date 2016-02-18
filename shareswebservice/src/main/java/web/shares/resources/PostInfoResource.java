package web.shares.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import web.shares.model.PostInfo;
import web.shares.service.PostInfoService;

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostInfoResource {
	
	PostInfoService postService=new PostInfoService();
	

	
	@GET
	public List<PostInfo> getPostsForCategory(@QueryParam("category")  String category){
		
		if(category != null)
		{
			return postService.getAllPostsForCategory(category);
		}
		
		
		
		return  postService.getAllPosts();
		
		
	}
	
	

	@POST
	public PostInfo addNewPost(PostInfo newPost){
        
		return postService.addNewPost(newPost);
	}
	
	/*
	
	@GET
	@Path("/{discountId}")
	public Discount getMessage(@PathParam("discountId") Long discountId){
		return discountService.getDiscount(discountId);
	}
	
*/
}
