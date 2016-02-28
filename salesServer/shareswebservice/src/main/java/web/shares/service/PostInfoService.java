package web.shares.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.commons.codec.binary.Base64;

import web.shares.database.DataHandler;
import web.shares.model.FollowingFriendship;
import web.shares.model.PostInfo;

public class PostInfoService {
	
	DataHandler dataHandler=new DataHandler();
	HashMap<Long,PostInfo> allposts=dataHandler.getAllPosts();

	
	public PostInfo addNewPost(PostInfo newPost){
		
		newPost.setId(allposts.size()+1);
		//allposts.put(newPost.getId(), newPost);
		
		if(dataHandler.storePostInfo(newPost,this.convertStringtoImage(newPost.getEncodeImage(),newPost.getImageName()))==null)
		{
			return null;
		}
		
		return newPost;
	}
	
	public void updatePost(long postid,String tagged_user){
		dataHandler.updatePostInfo(postid, tagged_user);
	}
	
	
	public List<PostInfo> getAllPosts(){
		return new ArrayList<PostInfo>(allposts.values());
		
		 
	}
	
	
	public List<PostInfo> getAllPostsByCategory(String category){
		List<PostInfo> PostsForCategory=new ArrayList<>();
		
		
		for(PostInfo postInfo:allposts.values()){
			if(postInfo.getCategory().equals(category)){
				PostsForCategory.add(postInfo);
				
			}
		}
		
		return PostsForCategory;
		
	}
	
	public String convertStringtoImage(String encodedImageStr,String imageName){
		// Decode String using Base64 Class
        byte[] imageByteArray = Base64.decodeBase64(encodedImageStr); 
        String imagePath="E:\\salesring\\image\\"+imageName;
     // Write Image into File system - Make sure you update the path
        FileOutputStream imageOutFile;
		try {
			
			imageOutFile = new FileOutputStream(imagePath);
			 imageOutFile.write(imageByteArray);
			 imageOutFile.close();
			 return imageName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
       
	}

	
}
