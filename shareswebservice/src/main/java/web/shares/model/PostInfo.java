package web.shares.model;

import java.io.File;
import java.sql.Date;






public class PostInfo {
	
	private long id;
	private Date created;
	private String username;
	private String category;
	private double price_before;
	private String sale_discount;
	private String shop;
	//private String description;
	//private File image;
	
	
	public PostInfo(){
		
	}
	
	
   
	public PostInfo(long id, Date created, String username, String category, double price_before, String sale_discount,
			String shop) {
		super();
		this.id = id;
		this.created = created;
		this.username = username;
		this.category = category;
		this.price_before = price_before;
		this.sale_discount = sale_discount;
		this.shop = shop;
		//this.description = description;
		//this.image = image;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public double getPrice_before() {
		return price_before;
	}



	public void setPrice_before(double price_before) {
		this.price_before = price_before;
	}



	public String getSale_discount() {
		return sale_discount;
	}



	public void setSale_discount(String sale_discount) {
		this.sale_discount = sale_discount;
	}



	public String getShop() {
		return shop;
	}



	public void setShop(String shop) {
		this.shop = shop;
	}



	


	
	
	

}
