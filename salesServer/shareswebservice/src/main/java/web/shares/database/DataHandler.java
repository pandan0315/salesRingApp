package web.shares.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import web.shares.model.ClientGCMToken;
import web.shares.model.FollowingFriendship;
import web.shares.model.PostInfo;
import web.shares.model.User;
import web.shares.model.UserProfile;

public class DataHandler {
	

     private Connection con = null;
    
    private Statement stmt=null;
    
    public DataHandler(){
    	
    	 try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/share", "pan", "123456");
            
          
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
           
        }
            
    }
    
    public User getUserFullname(String name){
    	
    	PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM share.users " + 
			        "WHERE username = ?");
			stmt.setString(1, name);
	    	ResultSet rs=stmt.executeQuery();
	    	if(rs.next()){
	    		String userName=rs.getString("username");
	    		String fullName=rs.getString("fullname");
	    		//String password=rs.getString("password");
	    		stmt.close();
	    		if(fullName!=null)
	    		{return new User(userName,fullName);}
	    		else
	    		{
	    			return null;
	    		}
	    	}
	    	stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    	
    	
    }
 
    
    public User getUserByemail(String email){
    	PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM share.users " + 
			        "WHERE email = ?");
			stmt.setString(1, email);
	    	ResultSet rs=stmt.executeQuery();
	    	if(rs.next()){
	    		String userName=rs.getString("username");
	    		String fullName=rs.getString("fullname");
	    		String emailaddress=rs.getString("email");
	    		String password=rs.getString("password");
	    		stmt.close();
	    		return new User(userName,fullName,emailaddress,password);
	    	}
	    	stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    public User getUserByname(String name){
    	PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM share.users " + 
			        "WHERE username = ?");
			stmt.setString(1, name);
	    	ResultSet rs=stmt.executeQuery();
	    	if(rs.next()){
	    		String userName=rs.getString("username");
	    		String fullName=rs.getString("fullname");
	    		String emailaddress=rs.getString("email");
	    		String password=rs.getString("password");
	    		stmt.close();
	    		return new User(userName,fullName,emailaddress,password);
	    	}
	    	stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
    public User getUserByNameAndPassword(String email, String password){
    	
    	try{System.out.println(email+password);
    		
    		PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.users WHERE (email = ? AND password = ?)");
            stmt.setString(1, email);
            stmt.setString(2, password);
            System.out.println("get in!!!");
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
            	
            	String username=rs.getString("username");
            	//ArrayList<String> userInterests=this.getUserInterest(username).getCategoryLists();
            	String fullname=rs.getString("fullname");
            	String emailaddress=rs.getString("email");
            	String passStr=rs.getString("password");
            	stmt.close();
            	System.out.println(username+fullname);
            	return new User(username,fullname);
            	
            }
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    }
    	return null;

    }
    
    public void storeUserAccount(User newUser)  {
        try {
            // Prepare the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO share.users " +
                    "(username,fullname, email,password) VALUES (?,?, ?,?)");
            
            stmt.setString(1, newUser.getUserName());  
            stmt.setString(2, newUser.getFullName());
            stmt.setString(3, newUser.getEmailAddress());
            stmt.setString(4, newUser.getPassword());
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
      public PostInfo getPostById(long postId){
    	
    	try {
            // Prepare the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.postedinfo" +
                    " WHERE postid = ?");
            stmt.setLong(1, postId);;
            ResultSet rs = stmt.executeQuery();
            // Create the client account object and return it
            if (rs.next()) {
            	long id=rs.getLong("postid");
            	String created=rs.getString("date");
            	String postUser=rs.getString("post_user");
            	String posterfullname=rs.getString("posterfullname");
            	String taggedUser=rs.getString("tagged_user");
            	String category=rs.getString("category");
            	String is_pricebefore=rs.getString("is_pricebefore");
            	String price=rs.getString("price");
            	String sale_discount=rs.getString("salediscount");
            	String shop=rs.getString("shop");    	
            	String imageName=rs.getString("image_name");
            	String description=rs.getString("description");          
                stmt.close();
                PostInfo newPost= new PostInfo(id,posterfullname,taggedUser,created,postUser,category,is_pricebefore,price,sale_discount,shop,imageName,description);
                
                return newPost;
              
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    	
    }
    
    
    public HashMap<Long,PostInfo> getAllPosts(){
    	HashMap<Long,PostInfo> allPosts=new HashMap<>();
    	
    	 try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.postedinfo");
			
			while(rs.next()){
			
				allPosts.put(rs.getLong("postid"), this.getPostById(rs.getLong("postid")));
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	
    	return allPosts;
    	
    	
    }
    public ArrayList<PostInfo> getAllPostsByName(String name){
    	
    	ArrayList<PostInfo>allPostsByName=new ArrayList<>();
    	FollowingFriendship allfriendsByName=this.getAllFriends(name);
    	ArrayList<String> friends=new ArrayList<>();
    	ArrayList<User> followedfriendsList= allfriendsByName.getFollowedList();
    	for(User user:followedfriendsList)
     	{
    		friends.add(user.getUserName());
    	}
    	StringBuilder b = new StringBuilder("'" + name + "', ");
    	
    	for (String friendName : friends) {
    		b.append("'" + friendName + "', ");
    	}
    	String whereClause = b.toString().replaceAll(", $", "");
    	
    	 try {
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.postedinfo" + " WHERE post_user IN (" + whereClause + ") ");
			//stmt.setString(1,whereClause);
	    	//stmt.executeQuery();
	    	while(rs.next()){
	    	
	    		allPostsByName.add(this.getPostById(rs.getLong("postid")));
	    	}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return allPostsByName;
    }
    
    public PostInfo storePostInfo(PostInfo newPost,String imageName)  {
        try {
            // Prepare the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO share.postedinfo" +
                    "(postid,post_user,posterfullname,tagged_user,category,shop,is_pricebefore,price,date,salediscount,image_name,description) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            
            stmt.setLong(1, newPost.getId());        
            stmt.setString(2, newPost.getPostUser());
            stmt.setString(3, newPost.getPosterfullname());
            stmt.setString(4, newPost.getTaggedUser());
            stmt.setString(5, newPost.getCategory());
            stmt.setString(6, newPost.getShop());
            stmt.setString(7, newPost.getIs_pricebefore());
            stmt.setString(8, newPost.getPrice());
            stmt.setString(9, newPost.getCreated());
            stmt.setString(10, newPost.getSale_discount());
            
            stmt.setString(11, imageName);
            stmt.setString(12, newPost.getDescription());
            /* Read image file path in the server side */
			//File image = new File(newPost.getImageFilePath());
			//FileInputStream fis = new FileInputStream(image);
			//stmt.setBinaryStream(8, fis, (int) image.length());

            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
		return new PostInfo(newPost.getId(),newPost.getPosterfullname(),newPost.getTaggedUser(),newPost.getCreated(), newPost.getPostUser(), newPost.getCategory(), newPost.getIs_pricebefore(),newPost.getPrice(), newPost.getSale_discount(),
			newPost.getShop(), imageName, newPost.getDescription());
        
    }
    public PostInfo updatePostInfo(long postid,String tagged_user){
    	
    	PostInfo currentPost=this.getPostById(postid);
    	String tagged_userBefore=currentPost.getTaggedUser();
    	try{
    		
    		if(tagged_user==null){
    			return null;
    		}
    		PreparedStatement stmt = con.prepareStatement("UPDATE  share.postedinfo SET " +
                     "tagged_user = ? WHERE postid = ?");      
    		 
    		 stmt.setString(1, tagged_userBefore+","+tagged_user);
    		 stmt.setLong(2, postid);
    		 stmt.executeUpdate();
    		 stmt.close();
    		
    	}catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    	return this.getPostById(postid);
    }
    
    
    public UserProfile getUserInterest(String name){
    	UserProfile userInterest=new UserProfile();
    	   	   	 
    	 ArrayList<String> categoryLists=new ArrayList<>();
    	 
    	 try {
 			stmt=con.createStatement();
 			ResultSet rs=stmt.executeQuery("SELECT * FROM share.userprofile");
 			
 			while(rs.next()){
 			
 				if(rs.getString("username").equals(name)){
 					categoryLists.add(rs.getString("interest_category"));
 				}
 				
 			
 			}
 			
 			
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		
 		}
    	 userInterest.setCategoryLists(categoryLists);
    	 userInterest.setFullname(this.getUserFullname(name).getFullName());
    	 userInterest.setUsername(name);
    	
    	return userInterest;
		
    	
    }
    
    	

    public UserProfile getUserInterestByNameAndInterest(String name, String category){
    	
    	
    		
    	try{
    		PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.userprofile WHERE (username = ? AND interest_category = ?)");
            stmt.setString(1, name);
            stmt.setString(2, category);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
            	String userName=rs.getString("username");

            	String interest=rs.getString("interest_category");
            	stmt.close();
            	return new UserProfile(userName,this.getUserFullname(name).getFullName(),interest);
            }
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    }
    	return null;

    }
    	
    
    
   /* public ArrayList<UserProfile> getAllInterest(){
    	ArrayList<UserProfile> allInterest=new ArrayList<>();
    	 try {
  			stmt=con.createStatement();
  			ResultSet rs=stmt.executeQuery("SELECT * FROM share.userprofile");
  			
  			while(rs.next()){
  			   
  			//	allInterest.add(new UserProfile(rs.getString("username"),rs.getString("interest_category")));
  				}
  		
  			
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
     	return allInterest;
    	
    }
    */	
    
    public void editProfileByfullname(String username,String fullname){
    	
    	if(fullname==null){
    		return;
    	}
    	 try {
             // Prepare the statement with SQL update command
   		 
            
             
             PreparedStatement stmt = con.prepareStatement("UPDATE  share.users SET " +
                     "fullname = ? WHERE username= ?");    
            
          
            stmt.setString(1,fullname);
            
            stmt.setString(2,username);

             // Execute and update the data
            
             stmt.executeUpdate();
             
             stmt.close();
    	 }catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
    	
    }
    public void editProfileByInterest(String username,ArrayList<String> categories, ArrayList<String> interestLists){
    	
    	 ArrayList<String> categoriesNotSame=new ArrayList<>();

        if(categories.isEmpty()){
       	 return;
        }
      

       
         if(interestLists.isEmpty()){
        	categoriesNotSame=categories;
        	
        }
         else{
        for(String category:categories){
           if(!interestLists.contains(category)){
        	   categoriesNotSame.add(category);
           }
           }
         }
        System.out.println(categoriesNotSame);
        if(categoriesNotSame.isEmpty()){
        	return;
        }
   	
        
   	  try {
             // Prepare the statement with SQL update command
   		PreparedStatement stmt = con.prepareStatement("INSERT INTO share.userprofile" +
                "(username,interest_category) VALUES (?,?)");
          
           for(String category:categoriesNotSame){
             
             stmt.setString(1, username);

             stmt.setString(2, category);
             stmt.executeUpdate();
             stmt.close();  
             
           }
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
        }
    	
    
   
  
  public UserProfile editProfile(UserProfile newProfile){
	  ArrayList<String> interestLists = new ArrayList<>();
	  
	   String username=newProfile.getUsername();
	   String fullname=newProfile.getFullname();
	   ArrayList<String> categories=newProfile.getCategoryLists();
	   
        if(this.getUserInterest(username).getCategoryLists()!=null){
        	interestLists=this.getUserInterest(username).getCategoryLists();
        }
	   
	   this.editProfileByfullname(username, fullname);
	   this.editPostInfo(username,fullname);
	   if(categories.isEmpty()){
		   return newProfile;
	   }
	   this.editProfileByInterest(username, categories, interestLists);
	   
	   
	  
	   return newProfile;
	  
  }
  
  
  private void editPostInfo(String username, String fullname) {
	// TODO Auto-generated method stub
	  if(fullname==null){
		  return;
		  
	  }
	  
	  try {
		PreparedStatement stmt = con.prepareStatement("UPDATE  share.postedinfo SET " +
		          "posterfullname = ? WHERE post_user = ?");
		stmt.setString(1, fullname);
		stmt.setString(2, username);
		stmt.executeUpdate();
		
		stmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}     
	  
	
}

//get friends object information , include friends username and fullname
  public FollowingFriendship getAllFriends(String username){
	  
	
	
 	 ArrayList<User> friends=new ArrayList<>();
 	 
 	 try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.followingfriendship");
			
			while(rs.next()){
			    if(rs.getString("following_user").equals(username))
			    {
			    	String friend_username=rs.getString("followed_user");
			    	String friend_fullname=this.getUserFullname(friend_username).getFullName();
			    friends.add(new User(friend_username,friend_fullname));
			    }
			}
			
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	 
 	
 	return new FollowingFriendship(username,friends);
 }
  public FollowingFriendship getFriendshipByFollowingAndFollowed(String followingUser, String followedUser){
  	try{
  		PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.followingfriendship WHERE (following_user = ? AND followed_user = ?)");
          stmt.setString(1, followingUser);
          stmt.setString(2, followedUser);
          ResultSet rs=stmt.executeQuery();
          if(rs.next()){
          	String following=rs.getString("following_user");
          	String followed=rs.getString("followed_user");
          	stmt.close();
          	return new FollowingFriendship(following,followed);
          }
  	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
  }
  	return null;

  }
  
     public FollowingFriendship addNewFriend(FollowingFriendship newFriend){
    	 if(this.getFriendshipByFollowingAndFollowed(newFriend.getFollowingUser(), newFriend.getFollowedUser())!=null){
    		 return null;
    	 }
    	if( this.getUserByname(newFriend.getFollowedUser())==null)
    	{
    		return null;
    	}
    	 try {
             // Prepare the statement with SQL update command
             PreparedStatement stmt = con.prepareStatement("INSERT INTO share.followingfriendship" +
                     "(following_user,followed_user) VALUES (?,?)");
             
            
            stmt.setString(1,newFriend.getFollowingUser());
            stmt.setString(2,newFriend.getFollowedUser());

             // Execute and update the data
             stmt.executeUpdate();
             stmt.close();
             
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
   	  
   	  return newFriend;
     }
    	 

     public FollowingFriendship removeFollowedFriend(FollowingFriendship friendShip) {
    	 if(this.getFriendshipByFollowingAndFollowed(friendShip.getFollowingUser(), friendShip.getFollowedUser())==null)
    	 {
    		 return null;
    		 
    	 }
         try {
             // Prepare the statement with SQL update command
             PreparedStatement stmt = con.prepareStatement("DELETE FROM share.followingfriendship WHERE " + 
                     "(following_user = ? AND followed_user=?)");
             stmt.setString(1, friendShip.getFollowingUser());
             stmt.setString(2, friendShip.getFollowedUser());
             // Execute the delete action
             stmt.executeUpdate();
             stmt.close();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
		return friendShip;
     }

     public Boolean getClientGCMToken(String gcmToken){
    	 String token=null;
    	 try {
             // Prepare the statement with SQL update command
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.client_token" +
                     " WHERE gcmToken = ?");
             stmt.setString(1, gcmToken);;
             ResultSet rs = stmt.executeQuery();
             
             if (rs.next()) {
             	
             	token=rs.getString("gcmtoken");
             	
                 stmt.close();
                
                 
               
             }
             stmt.close();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
    	 
    	 return token!=null;
    	 
     }
     
     public ArrayList<ClientGCMToken> getAllGCMToken(){
    	 ArrayList<ClientGCMToken> allToken=new ArrayList<>();
    	 try{
    		 stmt=con.createStatement();
 			 ResultSet rs=stmt.executeQuery("SELECT * FROM share.client_token");
 			 while(rs.next()){
 				 allToken.add(new ClientGCMToken(rs.getString("gcmtoken")));
 			 }
 			    		 
    	 }catch(SQLException ex){
    		 ex.printStackTrace();
    	 }
    	 
    	 return allToken;
     }
     
     
	public Boolean storeClientGCMToken(String gcmToken) {
		
		System.out.println(gcmToken);
		if(this.getClientGCMToken(gcmToken)){
			return false;
		}
		
			 try {
	             // Prepare the statement with SQL update command
	             PreparedStatement stmt = con.prepareStatement("INSERT INTO share.client_token" +
	                     "(gcmtoken) VALUES (?)");
	             
	            
	            stmt.setString(1,gcmToken);
	           

	             // Execute and update the data
	             stmt.executeUpdate();
	             stmt.close();
	             return true;
	             
	         } catch (SQLException ex) {
	             System.err.println(ex.getMessage());
	         }
	   	  
			
			
		
		
		return false; 
		
		
		
	}
	
	
	 public Map<String,ArrayList<String>> getAllInterest(){
		 
	    	Map<String,ArrayList<String>> allInterest=new HashMap<>();
	    	
	    	//this.getUserInterest(name)
	    	
	    	
	    	 try {
				stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("SELECT * FROM share.users");
				
				while(rs.next()){
				
					allInterest.put(rs.getString("username"), this.getUserInterest(rs.getString("username")).getCategoryLists());
					
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 
	    	
	    	return allInterest;
	    	
	    	
	    }
}
 

