package web.shares.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jersey.repackaged.com.google.common.base.Joiner;
import jersey.repackaged.com.google.common.collect.Lists;
import web.shares.database.DataHandler;
import web.shares.model.ClientGCMToken;
import web.shares.model.ErrorResponse;
import web.shares.model.FollowingFriendship;
import web.shares.model.Message;
import web.shares.model.PostInfo;
import web.shares.model.Sender;
import web.shares.service.PostInfoService;

@Path("/{username}/salesinfo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostInfoResource {
	
	DataHandler dataHandler=new DataHandler();
	PostInfoService postService=new PostInfoService();
	
	/**
	 * @api {get}  /{username}/salesinfo    Get all salesinfo from followed friends and himself/herself or filtered by category
	 * @apiName GetSalesInfo
	 * @apiGroup SalesInfo
	 * @apiExample {curl} Example usage:
	 *    curl -i http://localhost:8080/username/salesinfo?category=electronics
	 * @apiParam {String} username   filter salesinfo by username(following user)
	 * @apiParam {String} category    filter salesinfo by category,if null, return all categories
	 * @apiSuccess {Object}  postinfo              sales information.
	 * @apiSuccess {Number}  postinfo.id           salesinfo id.
	 * @apiSuccess {String}  postinfo.postUser     user who posted the saleinfo.
	 * @apiSuccess {String}  postinfo.taggedUser   user who tagged by this saleinfo.
	 * @apiSuccess {String}  postinfo.created      salesinfo created date.
	 * @apiSuccess {String}  postinfo.category     salesinfo's category.
	 * @apiSuccess {String}  postinfo.price_before     sales item's before price
	 * @apiSuccess {String}  postinfo.sale_discount     sale discount.
	 * @apiSuccess {String}  postinfo.shop          sales shop.
	 * @apiSuccess {String}  postinfo.description     more details about saleinfo.
	 * @apiSuccess {String}  postinfo.imageName    salesinfo photo name.
	 * @apiSuccessExample {json} Success-Response:
	 *       HTTP/1.1 200 OK
	 *       {
	 *           "category": "electronics",
    *             "created": "2016-02-16",
    *             "description": "best chance for whom wants to buy a new PS4",
    *              "id": 2,
    *              "imageName": "course.jpeg",
    *              "postUser": "Alice",
    *              "price_before": 4000,
    *              "sale_discount": "10%",
    *              "shop": "webhallen",
    *              "taggedUser": "dan"
	 *       }
	 *@apiErrorExample Error-Response:
	 *       HTTP/1.1 500 Internal Server Error
	 */
	@GET
	public Response getPostInfoByUserAndCategory(@PathParam("username") String username, @QueryParam("category") String category){
		 List<PostInfo> postsByUser=dataHandler.getAllPostsByName(username);
		  List<PostInfo> PostsByCategory=new ArrayList<>();
		if(category==null){
			GenericEntity<List<PostInfo>> entity = new GenericEntity<List<PostInfo>>(postsByUser) {};
			return Response.ok(entity).build();
			
		}
	
		for(PostInfo postInfo:postsByUser){
			if(postInfo.getCategory().equals(category)){
				PostsByCategory.add(postInfo);
				
			}
		}
		GenericEntity<List<PostInfo>> entity = new GenericEntity<List<PostInfo>>(PostsByCategory) {};
		 return Response.ok(entity).build();
		
		
	}
	
	@GET
	@Path("/{id}")
	public Response getPostInfoById(@PathParam("id") String id ){
		long postId=Long.valueOf(id).longValue();
		PostInfo postInfoByid=dataHandler.getPostById(postId);
		if(postInfoByid!=null){
			return Response.ok(postInfoByid).build();
		}
		
		return Response.status(400).entity(new ErrorResponse("no matched id post")).build();
		
	}
	
	
	/**
	 * @api {post} /{username}/salesinfo     Add new saleinfo to the server.
	 * @apiName  PostSalesInfo
	 * @apiGroup SalesInfo
	 * @apiParam {Object} newPostInfo new sale info will be sent to the server
	 * @apiParam {String} newPostInfo.postUser user who posted the saleinfo.
	 * @apiParam {String} newPostInfo.taggedUser user who tagged by this saleinfo.
	 * @apiParam {String} newPostInfo.created salesinfo created date.
	 * @apiParam {String} newPostInfo.category salesinfo's category.
	 * @apiParam {String} newPostInfo.price_before sales item's before price
	 * @apiParam {String} newPostInfo.sale_discount sale discount.
	 * @apiParam {String} newPostInfo.shop sales shop.
	 * @apiParam {String} newPostInfo.description more details about saleinfo.
	 * @apiParam {String} newPostInfo.encodeImage base64 encode string of
	 *           salesinfo photo.
	 * @apiParam {String} newPostInfo.imageName salesinfo photo's name.
	 * @apiParamExample {json} Request-Example:
	 *                 { 
	 *                 "category": "electronics",
	 *                  "created": "2016-02-16",
	 *                   "description":"best chance for whom wants to buy a new PS4",
	 *                  "encodeImage":"dfaodiopui0ere0jgvdkanvklfn;afjdasfdaffasgrafgdsfadsgdafgas",
	 *                  "imageName": "ps4.jpeg",
	 *                   "postUser": "dan",
	 *                   "posterfullname":"Pan Dan",
	 *                   "taggedUser": "dan"
	 *                   "price_before": 4000, 
	 *                   "sale_discount": "10%",
	 *                   "shop":"webhallen", 
	 *                   }
	 * @apiSuccess {Object} postinfo sales information(return the same object attribute as post).
	 * @apiSuccessExample {json} Success-Response:
	 *            HTTP/1.1 201 created
	 * {
	 *                  
	 *                 "category": "electronics",
	 *                  "created": "2016-02-16",
	 *                   "description":"best chance for whom wants to buy a new PS4",
	 *                  "imageName": "ps4.jpeg",
	 *                   "postUser": "dan",
	 *                   "postfullname":"Pan Dan"
	 *                  "price_before": 4000,
	 *                   "sale_discount": "10%",
	 *                    "shop":"webhallen", 
	 *                    "taggedUser": "dan"
	 *                     
	 * }
	 *@apiErrorExample Error-Response:
	 *   HTTP/1.1 500 Internal Server Error
	 * 
	 */
	@POST
	public Response addNewPost(PostInfo newPost){
		
		PostInfo postedInfo=postService.addNewPost(newPost);
		
		
		String postString=null;;
		
		if(postedInfo!=null){
			ObjectMapper mapper=new ObjectMapper();
		   try {
			postString=mapper.writeValueAsString(postedInfo);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			String poster=postedInfo.getPostUser();
			
			
			String newCategory=postedInfo.getCategory();
			
			ArrayList<String> nameList=new ArrayList<>();
			ArrayList<String> notifyNameList=new ArrayList<>();
			
			ArrayList<ClientGCMToken> tokenList=this.dataHandler.getAllGCMToken();
			
			ArrayList<String> registraion_ids=new ArrayList<>();
			
		
			
			for(ClientGCMToken token:tokenList){
				registraion_ids.add(token.getToken());
			}
			
			Map<String, ArrayList<String>> allinterests=this.dataHandler.getAllInterest();
			for (Map.Entry<String, ArrayList<String>> entry : allinterests.entrySet()) {  
				
				if((entry.getValue().contains(newCategory)) && (!entry.getKey().equals(poster))){
					
						nameList.add(entry.getKey());			
				
							
				}
			for(String name:nameList){
				if(this.dataHandler.getFriendshipByFollowingAndFollowed(name, poster)!=null){
					notifyNameList.add(name);
				}
				
				
			}
				  
			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			     	  
			}  
			 System.out.println("notifyNameList = " + notifyNameList);
			 
			
			 System.out.println( "Sending POST to GCM" );
			 
			 String apikey="AIzaSyBU4DRf-0XqkIpInxjsymQ7hYGo_oDKLqw";
			 Message message=new Message();
					 
			 
			 for(String token:registraion_ids){
				 message.addRegId(token);
			 }
			 
			 String notifyNames=Joiner.on(",").join(notifyNameList);
			 
			 System.out.println( "postString: "+postString );
			 message.createData(notifyNames, postString);
			 
			 Sender.post(apikey, message);		 
			 return Response.status(201).entity(postedInfo).build();
		
			
			
		}
		
	return Response.status(401).entity(new ErrorResponse("Can not store your post")).build();
	}
	
	
  

	/**
	 * @api {put} /{username}/salesinfo   Update tagged user(add new tagged user) in some posted_saleinfo. 
	 * @apiName  UpdateSalesInfo
	 * @apiGroup SalesInfo
	 * @apiParam {Object} updatePostInfo   update specified postInfo with new tagged user.
	 * @apiParam {Number} postid           specify the postInfo id which will be updated.
	 * @apiParam {String} postUser         specify the postUser
	 * @apiParam {String} taggedUser       specify the new taggedUser.
	 * @apiParamExample {json} Request-Example:
	 * {
	 *      "postid":1,
	 *      "postUser":"pan",
	 *      "taggedUser":"yi"
	 * }
   	 * @apiSuccessExample Success-Response:
   	 *      HTTP/1.1 202 Accepted
   	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 500 Internal Server Error
	 */
    @PUT
	public Response updatePost(PostInfo post){
   
		postService.updatePost(post.getId(), post.getTaggedUser());
		
		return Response.status(202).build();
    	
	}
	

}
