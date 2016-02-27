package web.shares.model;

import java.util.ArrayList;

public class FollowingFriendship {

	
	private String followingUser;
	private String followedUser;
	private String followedUserFullname;
	private  ArrayList<User> followedList;
	//private User friend;
	
	public FollowingFriendship(){
		
	}

	public FollowingFriendship(String followingUser, String followedUser) {
		super();
		
		this.followingUser = followingUser;
		this.followedUser = followedUser;
	}
	
	
	
	
	public FollowingFriendship(String followingUser, ArrayList<User> followedList) {
		super();
	
		this.followingUser = followingUser;
		this.followedList=followedList;
	}
	
	public String getFollowingUser() {
		return followingUser;
	}
	public void setFollowingUser(String followingUser) {
		this.followingUser = followingUser;
	}
	public String getFollowedUser() {
		return followedUser;
	}
	public void setFollowedUser(String followedUser) {
		this.followedUser = followedUser;
	}
	
	public ArrayList<User> getFollowedList() {
		return followedList;
	}
	public void setFollowedList(ArrayList<User> followedList) {
		this.followedList = followedList;
	}

	public String getFollowedUserFullname() {
		return followedUserFullname;
	}

	public void setFollowedUserFullname(String followedUserFullname) {
		this.followedUserFullname = followedUserFullname;
	}
	
	
	
}
