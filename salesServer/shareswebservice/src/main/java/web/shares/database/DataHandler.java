package web.shares.database;


import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.StringUtils;

import jersey.repackaged.com.google.common.base.Joiner;
import web.shares.model.User;
import web.shares.model.UserProfile;
import web.shares.model.FollowingFriendship;
import web.shares.model.PostInfo;

import web.shares.model.User;

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
 
    
    public User getUserByname(String name){
    	PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM share.users " + 
			        "WHERE username = ?");
			stmt.setString(1, name);
	    	ResultSet rs=stmt.executeQuery();
	    	if(rs.next()){
	    		String userName=rs.getString("username");
	    		String email=rs.getString("email");
	    		String password=rs.getString("password");
	    		stmt.close();
	    		return new User(userName,email,password);
	    	}
	    	stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public User getUserByNameAndPassword(String email, String password){
    	try{
    		PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.users WHERE (email = ? AND password = ?)");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
            	String username=rs.getString("username");
            	String emailaddress=rs.getString("email");
            	String passStr=rs.getString("password");
            	stmt.close();
            	return new User(username,emailaddress);
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
                    "(username, email,password) VALUES (?,?, ?)");
            
            stmt.setString(1, newUser.getUserName());        
            stmt.setString(2, newUser.getEmailAddress());
            stmt.setString(3, newUser.getPassword());
            
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
            	String taggedUser=rs.getString("tagged_user");
            	String category=rs.getString("category");
            	String is_pricebefore=rs.getString("is_pricebefore");
            	String price=rs.getString("price");
            	String sale_discount=rs.getString("salediscount");
            	String shop=rs.getString("shop");    	
            	String imageName=rs.getString("image_name");
            	String description=rs.getString("description");          
                stmt.close();
                PostInfo newPost= new PostInfo(id,taggedUser,created,postUser,category,is_pricebefore,price,sale_discount,shop,imageName,description);
                
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
    	
    	ArrayList<PostInfo> allPostsByName=new ArrayList<>();
    	FollowingFriendship allfriendsByName=this.getAllFriends(name);
    	ArrayList<String> followedfriendsList= allfriendsByName.getFollowedList();
    	StringBuilder b = new StringBuilder("'" + name + "', ");
    	
    	for (String friendName : followedfriendsList) {
    		b.append("'" + friendName + "', ");
    	}
    	String whereClause = b.toString().replaceAll(", $", "");
    	
    	 try {
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.postedinfo" + " WHERE post_user IN (" + whereClause + ")");
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
                    "(postid,post_user,tagged_user,category,shop,is_pricebefore,price,date,salediscount,image_name,description) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            
            stmt.setLong(1, newPost.getId());        
            stmt.setString(2, newPost.getPostUser());
            stmt.setString(3, newPost.getTaggedUser());
            stmt.setString(4, newPost.getCategory());
            stmt.setString(5, newPost.getShop());
            stmt.setString(6, newPost.getIs_pricebefore());
            stmt.setString(7, newPost.getPrice());
            stmt.setString(8, newPost.getCreated());
            stmt.setString(9, newPost.getSale_discount());
            
            stmt.setString(10, imageName);
            stmt.setString(11, newPost.getDescription());
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
		return newPost;
        
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
 			return null;
 		}
    	 if (!categoryLists.isEmpty())
    	{return new UserProfile(name,categoryLists);}
		return null;
    	
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
            	return new UserProfile(userName,interest);
            }
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    }
    	return null;

    }
    	
    
    
    public ArrayList<UserProfile> getAllInterest(){
    	ArrayList<UserProfile> allInterest=new ArrayList<>();
    	 try {
  			stmt=con.createStatement();
  			ResultSet rs=stmt.executeQuery("SELECT * FROM share.userprofile");
  			
  			while(rs.next()){
  			
  				allInterest.add(new UserProfile(rs.getString("username"),rs.getString("interest_category")));
  				}
  		
  			
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
     	return allInterest;
    	
    }
    	
  public UserProfile addUserInterest(UserProfile newProfile){
	  
	  
    	
    	//newInterest.setId(this.getAllUserInterest().size()+1);
    	  try {
              // Prepare the statement with SQL update command
              PreparedStatement stmt = con.prepareStatement("INSERT INTO share.userprofile" +
                      "(username,interest_category) VALUES (?,?,?)");
              
             
             stmt.setString(1, newProfile.getUsername());

             stmt.setString(3,newProfile.getInterest_category());

              // Execute and update the data
              stmt.executeUpdate();
              stmt.close();
              
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    	  return newProfile;
  }
  
  public FollowingFriendship getAllFriends(String username){
  	
 	
 	 ArrayList<String> friends=new ArrayList<>();
 	 
 	 try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.followingfriendship");
			
			while(rs.next()){
			    if(rs.getString("following_user").equals(username))
			    {
			    friends.add(rs.getString("followed_user"));
			    }
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	 if(!friends.isEmpty())
 	{return new FollowingFriendship(username,friends);}
 	 return null;
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
    	 try {
             // Prepare the statement with SQL update command
             PreparedStatement stmt = con.prepareStatement("INSERT INTO share.following_table" +
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
             PreparedStatement stmt = con.prepareStatement("DELETE FROM share.following_table WHERE " + 
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
	  
  
	  
	  
    }
 

