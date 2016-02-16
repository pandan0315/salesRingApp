package web.shares.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.shares.database.DataHandler;
import web.shares.model.PostInfo;

public class PostInfoService {
	
	DataHandler dataHandler=new DataHandler();
	HashMap<Long,PostInfo> allposts=dataHandler.getAllPosts();
	
	/*private Map<Long, Discount> discounts=DataHandler.getDiscounts();
	
	
	
	public DiscountService() {
		discounts.put(1L,new Discount(1,"H&M all on sale!","pan","Clothes",34,"/Users/danpan/Downloads/image/1.jpeg"));
		discounts.put(2L,new Discount(2,"All books are on sale now!","pan","education",560,"/Users/danpan/Downloads/image/2.jpeg"));
	}

	public List<Discount> getAllDiscounts(){
		return new ArrayList<Discount>(discounts.values());
		
		 
	}
	
	public List<Discount> getAllDiscountForCategory(String category){
		List<Discount> DiscountForCategory=new ArrayList<>();
		
		for(Discount discount:discounts.values()){
			if(discount.getCategory().equals(category)){
				DiscountForCategory.add(discount);
				
			}
		}
		
		return DiscountForCategory;
		
	}
	
	public Discount getDiscount(Long discountId){
		
		return discounts.get(discountId);
		
	}
	
	
	
	public Discount addDiscount(Discount discount){
		discount.setId(discounts.size()+1);
		discounts.put(discount.getId(), discount);
		return discount;
	}
	
   */ 
	public PostInfo addNewPost(PostInfo newPost){
		
		newPost.setId(allposts.size()+1);
		//allposts.put(newPost.getId(), newPost);
		dataHandler.storePostInfo(newPost);
		
		return newPost;
	}
	public List<PostInfo> getAllPosts(){
		return new ArrayList<PostInfo>(allposts.values());
		
		 
	}
	public List<PostInfo> getAllPostsForCategory(String category){
		List<PostInfo> PostsForCategory=new ArrayList<>();
		
		
		for(PostInfo postInfo:allposts.values()){
			if(postInfo.getCategory().equals(category)){
				PostsForCategory.add(postInfo);
				
			}
		}
		
		return PostsForCategory;
		
	}
}
