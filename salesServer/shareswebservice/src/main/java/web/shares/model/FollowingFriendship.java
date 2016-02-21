package web.shares.model;

import java.util.ArrayList;

public class FollowingFriendship {

	
	private String followingUser;
	private String followedUser;
	private  ArrayList<String> followedList;
	
	public FollowingFriendship(){
		
	}

	public FollowingFriendship(String followingUser, String followedUser) {
		super();
		
		this.followingUser = followingUser;
		this.followedUser = followedUser;
	}
	
	public FollowingFriendship(String followingUser, ArrayList<String> followedList) {
		super();
		this.followingUser = followingUser;
		this.setFollowedList(followedList);
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
	
	public ArrayList<String> getFollowedList() {
		return followedList;
	}
	public void setFollowedList(ArrayList<String> followedList) {
		this.followedList = followedList;
	}
	
	
	
}
