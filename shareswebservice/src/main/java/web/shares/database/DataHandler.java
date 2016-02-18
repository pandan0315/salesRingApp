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
	    		String password=rs.getString("password");
	    		stmt.close();
	    		return new User(userName,password);
	    	}
	    	stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public User getUserByNameAndPassword(String name, String password){
    	try{
    		PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.users WHERE (username = ? AND password = ?)");
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
            	String userName=rs.getString("username");
            	String passStr=rs.getString("password");
            	stmt.close();
            	return new User(userName,passStr);
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
                    "(username, password) VALUES (?, ?)");
            
            stmt.setString(1, newUser.getUserName());        
            stmt.setString(2, newUser.getPassword());
            
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
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM share.posts" +
                    " WHERE postid = ?");
            stmt.setLong(1, postId);;
            ResultSet rs = stmt.executeQuery();
            // Create the client account object and return it
            if (rs.next()) {
            	long id=rs.getLong("postid");
            	Date created=rs.getDate("date");
            	String postUser=rs.getString("post_user");
            	String taggedUser=rs.getString("tagged_user");
            	String category=rs.getString("category");
            	double price_before=rs.getDouble("price_before");
            	String sale_discount=rs.getString("salediscount");
            	String shop=rs.getString("shop");
            	
            	
            	String imagepath=rs.getString("image_path");
            
            
                stmt.close();
                PostInfo newPost= new PostInfo(id,taggedUser,created,postUser,category,price_before,sale_discount,shop,imagepath);
                
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
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.posts");
			
			while(rs.next()){
			
				allPosts.put(rs.getLong("postid"), this.getPostById(rs.getLong("postid")));
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	
    	return allPosts;
    	
    	
    }
    
    public void storePostInfo(PostInfo newPost,String imagePath)  {
        try {
            // Prepare the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO share.posts" +
                    "(postid,post_user,tagged_user,category,shop,price_before,date,salediscount,image_path) VALUES (?,?,?,?,?,?,?,?,?)");
            
            stmt.setLong(1, newPost.getId());        
            stmt.setString(2, newPost.getPostUser());
            stmt.setString(3, newPost.getTaggedUser());
            stmt.setString(4, newPost.getCategory());
            stmt.setString(5, newPost.getShop());
            stmt.setDouble(6, newPost.getPrice_before());
            stmt.setDate(7, newPost.getCreated());
            stmt.setString(8, newPost.getSale_discount());
            
            stmt.setString(9, imagePath);
            /* Read image file path in the server side */
			//File image = new File(newPost.getImageFilePath());
			//FileInputStream fis = new FileInputStream(image);
			//stmt.setBinaryStream(8, fis, (int) image.length());

            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    
    public UserProfile getUserInterest(String name){
    	
    	 
    	 ArrayList<String> categoryLists=new ArrayList<>();
    	 
    	 try {
 			stmt=con.createStatement();
 			ResultSet rs=stmt.executeQuery("SELECT * FROM share.user_interest");
 			
 			while(rs.next()){
 			
 				if(rs.getString("username").equals(name)){
 					categoryLists.add(rs.getString("interest_category"));
 				}
 				
 			
 			}
 			
 			
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	return new UserProfile(name,categoryLists);
    }
    
    public ArrayList<UserProfile> getAllInterest(){
    	ArrayList<UserProfile> allInterest=new ArrayList<>();
    	 try {
  			stmt=con.createStatement();
  			ResultSet rs=stmt.executeQuery("SELECT * FROM share.user_interest");
  			
  			while(rs.next()){
  			
  				allInterest.add(new UserProfile(rs.getString("username"),rs.getString("interest_category")));
  				}
  		
  			
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
     	return allInterest;
    	
    }
    	
  public UserProfile addUserInterest(UserProfile newInterest){
    	
    	//newInterest.setId(this.getAllUserInterest().size()+1);
    	  try {
              // Prepare the statement with SQL update command
              PreparedStatement stmt = con.prepareStatement("INSERT INTO share.user_interest" +
                      "(username,interest_category) VALUES (?,?)");
              
             
             stmt.setString(1, newInterest.getUsername());
             stmt.setString(2,newInterest.getInterest_category());

              // Execute and update the data
              stmt.executeUpdate();
              stmt.close();
              
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    	  
    	  return newInterest;
      }
  
  public FollowingFriendship getAllFriends(String username){
  	
 	
 	 ArrayList<String> friends=new ArrayList<>();
 	 
 	 try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM share.following_table");
			
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
 	return new FollowingFriendship(username,friends);
 }
  
  
     public FollowingFriendship addNewFriend(FollowingFriendship newFriend){
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
    	 

     public void removeFollowedFriend(FollowingFriendship friendShip) {
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
     }
	  
	  
	  
    }
 

