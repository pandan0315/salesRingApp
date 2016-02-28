package com.example.zhehuan.saleapp;

/**
 * Created by zhehuan on 20/02/2016.
 */
public class FriendDetail {
    private String userName;
    //private String userCate;
    private String fullName;

    public FriendDetail(String userName, String fullName){
        this.userName = userName;
      //  this.userCate = userCate;
        this.fullName=fullName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

  /*  public void setUserCate(String userCate){
        this.userCate = userCate;
    }

    public String getUserCate(){
        return userCate;
    }

    */
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
