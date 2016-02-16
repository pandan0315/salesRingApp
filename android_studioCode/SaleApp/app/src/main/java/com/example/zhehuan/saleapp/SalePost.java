package com.example.zhehuan.saleapp;

/**
 * Created by khalid on 2/11/2016.
 */
public class SalePost {
    private String postID;
    private String poster;
    private String store;
    private String category;
    private String Description;
    private String saleValue;
    private String price;

    public SalePost(String postID, String poster, String store, String category, String description, String saleValue, String price, String postDate, boolean priceBeforeDiscount, String posterThumbnail, String postImage) {
        this.postID = postID;
        this.poster = poster;
        this.store = store;
        this.category = category;
        Description = description;
        this.saleValue = saleValue;
        this.price = price;
        this.postDate = postDate;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.posterThumbnail = posterThumbnail;
        this.postImage = postImage;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    private String postDate;
    private boolean priceBeforeDiscount;
    private String posterThumbnail;

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(boolean priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public void setPosterThumbnail(String posterThumbnail) {
        this.posterThumbnail = posterThumbnail;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    private String postImage;
}
