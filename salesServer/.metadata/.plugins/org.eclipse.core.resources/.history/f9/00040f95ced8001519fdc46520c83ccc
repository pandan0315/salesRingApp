package web.shares.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jersey.repackaged.com.google.common.collect.Lists;
import web.shares.database.DataHandler;
import web.shares.model.ErrorResponse;
import web.shares.model.FollowingFriendship;
import web.shares.model.PostInfo;
import web.shares.service.PostInfoService;

@Path("/salesinfo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostInfoResource {
	
	DataHandler dataHandler=new DataHandler();
	PostInfoService postService=new PostInfoService();
	
	/**
	 * @api {get}  /salesinfo    Get all salesinfo from followed friends or by category
	 * @apiName GetSalesInfo
	 * @apiGroup SalesInfo
	 * @apiExample {curl} Example usage:
	 *    curl -i http://localhost:8080/salesinfo?username=pan&category=electronics
	 * @apiParam {String} username   filter salesinfo by username(following user)
	 * @apiParam {String} category    filter salesinfo by category,if null, return all category
	 * @apiSuccess {Object}  postinfo              sales information.
	 * @apiSuccess {Number}  postinfo.id           salesinfo id.
	 * @apiSuccess {String}  postinfo.postUser     user who posted the saleinfo.
	 * @apiSuccess {String}  postinfo.taggedUser   user who tagged by this saleinfo.
	 * @apiSuccess {String}  postinfo.created      salesinfo created date.
	 * @apiSuccess {String}  postinfo.category     salesinfo's category.
	 * @apiSuccess {Number}  postinfo.price_before     sales item's before price
	 * @apiSuccess {String}  postinfo.sale_discount     sale discount.
	 * @apiSuccess {String}  postinfo.shop          sales shop.
	 * @apiSuccess {String}  postinfo.description     more details about saleinfo.
	 * @apiSuccess {String}  postinfo.imagePath     salesinfo photo url.
	 * @apiSuccessExample {json} Success-Response:
	 *       HTTP/1.1 200 OK
	 *       {
	 *           "category": "electronics",
    *             "created": "2016-02-16",
    *             "description": "best chance for whom wants to buy a new PS4",
    *              "id": 2,
    *              "imagePath": "/Users/danpan/Documents/eclipse/testApp/shareswebservice/image/1.jpeg",
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
	public Response getPostInfoByUserAndCategory(@QueryParam("username") String username,@QueryParam("category") String category){
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
	
	
	/**
	 * @api {post} /salesinfo Add new saleinfo to the server.
	 * @aipError (500) Internal Server Error
	 * @apiName  PostSalesInfo
	 * @apiGroup SalesInfo
	 * @apiParam {Object} newPostInfo new sale info will be sent to the server
	 * @apiParam {String} newPostInfo.postUser user who posted the saleinfo.
	 * @apiParam {String} newPostInfo.taggedUser user who tagged by this
	 *           saleinfo.
	 * @apiParam {String} newPostInfo.created salesinfo created date.
	 * @apiParam {String} newPostInfo.category salesinfo's category.
	 * @apiParam {Number} newPostInfo.price_before sales item's before price
	 * @apiParam {String} newPostInfo.sale_discount sale discount.
	 * @apiParam {String} newPostInfo.shop sales shop.
	 * @apiParam {String} newPostInfo.description more details about saleinfo.
	 * @apiParam {String} newPostInfo.encodeImage base64 encode string of
	 *           salesinfo photo.
	 * @apiParam {String} newPostInfo.imageName salesinfo photo's name.
	 * @apiParamExample {json} Request-Example:
	 *                 { 
	 *                 "category": "electronics",
	 *                  "created": "2016-02-16", "description":
	 *                  "best chance for whom wants to buy a new PS4",
	 *                  "encodeImage":
	 *                  "dfaodiopui0ere0jgvdkanvklfn;afjdasfdaffasgrafgdsfadsgdafgas",
	 *                  "imageName": "ps4.jpeg", "postUser": "Alice",
	 *                  "price_before": 4000, "sale_discount": "10%", "shop":
	 *                  "webhallen", "taggedUser": "dan"
	 *                   }
	 * @apiSuccess {Object} postinfo sales information(return the same object
	 *             attribute as post).
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 201 created
	 * {
	 *                  
	 *                 "category": "electronics",
	 *                  "created": "2016-02-16", "description":
	 *                  "best chance for whom wants to buy a new PS4",
	 *                  "encodeImage":
	 *                  "dfaodiopui0ere0jgvdkanvklfn;afjdasfdaffasgrafgdsfadsgdafgas",
	 *                  "imageName": "ps4.jpeg", "postUser": "Alice",
	 *                  "price_before": 4000, "sale_discount": "10%", "shop":
	 *                  "webhallen", "taggedUser": "dan"
	 *                     
	 * }
	 *@apiErrorExample Error-Response:
	 *   HTTP/1.1 500 Internal Server Error
	 * 
	 */
	@POST
	public Response addNewPost(PostInfo newPost){
		
		PostInfo postedInfo=postService.addNewPost(newPost);
		
		return Response.status(201).entity(postedInfo).build();
	}
	
	/**
	 * @api {put} /salesinfo   Update tagged user(add new tagged user) in some posted_saleinfo. 
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
