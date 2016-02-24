package com.example.zhehuan.saleapp;

import java.util.Date;

/**
 * Created by khalid on 2/11/2016.
 */
public class SalePost {
    private long postID;
    private String poster;
    private String taggedUser;
    private String store;
    private String category;
    private String Description;
    private String saleValue;
    private double price;
    private String imageName;
    private String is_pricebefore;
    private String postDate;
    private String encodeImge;


    public SalePost(){

    }
    public SalePost(long postID, String poster, String taggedUser, String store, String category, String description, String saleValue, double price, String is_pricebefore,String imageName,String postDate) {
        this.postID = postID;
        this.poster = poster;
        this.taggedUser=taggedUser;
        this.store = store;
        this.is_pricebefore=is_pricebefore;
        this.category = category;
        Description = description;
        this.saleValue = saleValue;
        this.price = price;
        this.postDate = postDate;
        this.price = price;
        this.imageName =imageName;

    }

    public SalePost(String poster, String taggedUser, String store, String category, String description, String saleValue, double price, String imageName, String is_pricebefore, String postDate, String encodeImge) {
        this.poster = poster;
        this.taggedUser = taggedUser;
        this.store = store;
        this.category = category;
        Description = description;
        this.saleValue = saleValue;
        this.price = price;
        this.imageName = imageName;
        this.is_pricebefore = is_pricebefore;
        this.postDate = postDate;
        this.encodeImge = encodeImge;
    }

    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTaggedUser() {
        return taggedUser;
    }

    public void setTaggedUser(String taggedUser) {
        this.taggedUser = taggedUser;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(String saleValue) {
        this.saleValue = saleValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getIs_pricebefore() {
        return is_pricebefore;
    }

    public void setIs_pricebefore(String is_pricebefore) {
        this.is_pricebefore = is_pricebefore;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getEncodeImge() {
        return encodeImge;
    }

    public void setEncodeImge(String encodeImge) {
        this.encodeImge = encodeImge;
    }
}
