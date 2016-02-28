package web.shares.model;


import java.sql.Date;






public class PostInfo {
	
	private long id;
	private String created;
	private String postUser;
	private String posterfullname;
	private String taggedUser;
	private String category;
	private String is_pricebefore;
	private String price;
	private String sale_discount;
	private String shop;
	private String description;
	private String imageName;
	private String encodeImage;
	//private String imagePath;
	
	
	
	public PostInfo(){
		
	}
	
	
 
	


	public PostInfo(long id,String posterfullname,String taggedUser, String created, String postUser, String category, String is_pricebefore,String price, String sale_discount,
			String shop,String imageName,String description) {
		super();
		this.id=id;
		this.taggedUser=taggedUser;
		this.posterfullname=posterfullname;
		this.created = created;
		this.postUser = postUser;
		this.category = category;
		this.is_pricebefore=is_pricebefore;
		this.price=price;
		this.sale_discount = sale_discount;
		this.shop = shop;
	    this.imageName=imageName;
	    this.description=description;

		
	}

	public PostInfo(long id, String postUser,String taggedUser){
		this.id=id;
		this.postUser=postUser;
		this.taggedUser=taggedUser;
	}


	public PostInfo(String taggedUser,String posterfullname,String created, String postUser, String category, String is_pricebefore,String price, String sale_discount,
			String shop, String imageName, String encodeImage,String description) {
		super();
		this.taggedUser=taggedUser;
		this.posterfullname=posterfullname;
		this.created = created;
		this.postUser = postUser;
		this.category = category;
		this.is_pricebefore=is_pricebefore;
		this.price=price;
		this.sale_discount = sale_discount;
		this.shop = shop;
		this.imageName = imageName;
		this.encodeImage = encodeImage;
		this.description=description;
	}






	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}

	public String getTaggedUser() {
		return taggedUser;
	}


	public void setTaggedUser(String taggedUser) {
		this.taggedUser = taggedUser;
	}

	






	public String getPostUser() {
		return postUser;
	}






	public void setPostUser(String postUser) {
		this.postUser = postUser;
	}






	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
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






	public String getImageName() {
		return imageName;
	}






	public void setImageName(String imageName) {
		this.imageName = imageName;
	}






	public String getEncodeImage() {
		return encodeImage;
	}






	public void setEncodeImage(String encodeImage) {
		this.encodeImage = encodeImage;
	}






	public String getDescription() {
		return description;
	}






	public void setDescription(String description) {
		this.description = description;
	}







	public String getCreated() {
		return created;
	}






	public void setCreated(String created) {
		this.created = created;
	}






	public String getPrice() {
		return price;
	}






	public void setPrice(String price) {
		this.price = price;
	}






	public String getIs_pricebefore() {
		return is_pricebefore;
	}






	public void setIs_pricebefore(String is_pricebefore) {
		this.is_pricebefore = is_pricebefore;
	}






	public String getPosterfullname() {
		return posterfullname;
	}






	public void setPosterfullname(String posterfullname) {
		this.posterfullname = posterfullname;
	}





	


	
	
	

}
