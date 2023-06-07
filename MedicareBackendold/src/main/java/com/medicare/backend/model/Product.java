 package com.medicare.backend.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product")
public class Product {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long pid;
	private String name;
	private String brand;
	private float price;
	private boolean active = true;
	@Column(name="number_of_views")
	private int numberOfViews = 0;
	private int qty = 0;
	
	@JsonIgnore
	@Column(name="image_blob")
	@Lob
	private byte[] imageBlob;
	
	@ManyToOne
	private Category category;
	
	public Product() {
		this.pid = null;
	}

	public Product(Long pid, String name, String brand, float price, boolean active, int numberOfViews, byte[] imageBlob,
			 Category category, int qty) {
		this.pid = pid;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.active = active;
		this.numberOfViews = numberOfViews;
		this.imageBlob = imageBlob;
		this.category = category;
		this.qty =0;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public byte[] getImageBlob() {
		return imageBlob;
	}

	public void setImageBlo(byte[] imageBlob) {
		this.imageBlob = imageBlob;
	}

	public Long getPid() {
		return pid;
	}
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}
	

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [pid=");
		builder.append(pid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", brand=");
		builder.append(brand);
		builder.append(", price=");
		builder.append(price);
		builder.append(", active=");
		builder.append(active);
		builder.append(", numberOfViews=");
		builder.append(numberOfViews);
		builder.append(", imageBlob=");
		builder.append(imageBlob);
		builder.append(", category=");
		builder.append(category);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
}
