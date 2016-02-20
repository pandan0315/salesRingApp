package com.example.zhehuan.saleapp;

/**
 * Created by zhehuan on 20/02/2016.
 */
public class FriendDetail {
    private String userName;
    private String userCate;

    public FriendDetail(String userName, String userCate){
        this.userName = userName;
        this.userCate = userCate;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserCate(String userCate){
        this.userCate = userCate;
    }

    public String getUserCate(){
        return userCate;
    }
}
