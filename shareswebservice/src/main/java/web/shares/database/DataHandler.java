package web.shares.database;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;

import web.shares.model.User;
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
            	String username=rs.getString("username");
            	String category=rs.getString("category");
            	double price_before=rs.getDouble("price_before");
            	String sale_discount=rs.getString("salediscount");
            	String shop=rs.getString("shop");
            	
                stmt.close();
                return new PostInfo(id,created,username,category,price_before,sale_discount,shop);
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
				allPosts.put(rs.getLong("postid"),this.getPostById(rs.getLong("postid")) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	
    	return allPosts;
    	
    	
    }
    
    public void storePostInfo(PostInfo newPost)  {
        try {
            // Prepare the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO share.posts" +
                    "(postid,username,category,shop,price_before,date,salediscount) VALUES (?,?,?,?,?,?,?)");
            
            stmt.setLong(1, newPost.getId());        
            stmt.setString(2, newPost.getUsername());
            stmt.setString(3, newPost.getCategory());
            stmt.setString(4, newPost.getShop());
            stmt.setDouble(5, newPost.getPrice_before());
            stmt.setDate(6, newPost.getCreated());
            stmt.setString(7, newPost.getSale_discount());
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
}
