package web.shares.model;


import java.sql.Date;






public class PostInfo {
	
	private long id;
	private Date created;
	private String postUser;
	private String taggedUser;
	private String category;
	private double price_before;
	private String sale_discount;
	private String shop;
	//private String description;
	private String imageName;
	private String encodeImage;
	private String imagePath;
	
	
	
	public PostInfo(){
		
	}
	
	
 
	


	public PostInfo(long id,String taggedUser, Date created, String postUser, String category, double price_before, String sale_discount,
			String shop,String imagePath) {
		super();
		this.id=id;
		this.taggedUser=taggedUser;
		this.created = created;
		this.postUser = postUser;
		this.category = category;
		this.price_before = price_before;
		this.sale_discount = sale_discount;
		this.shop = shop;
	    this.imagePath=imagePath;

		
	}

	public PostInfo(long id, String postUser,String taggedUser){
		this.id=id;
		this.postUser=postUser;
		this.taggedUser=taggedUser;
	}


	public PostInfo(long id, Date created, String postUser, String category, double price_before, String sale_discount,
			String shop, String imageName, String encodeImage) {
		super();
		this.id = id;
		this.created = created;
		this.postUser = postUser;
		this.category = category;
		this.price_before = price_before;
		this.sale_discount = sale_discount;
		this.shop = shop;
		this.imageName = imageName;
		this.encodeImage = encodeImage;
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

	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
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






	public String getImagePath() {
		return imagePath;
	}






	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}





	


	
	
	

}
