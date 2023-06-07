package com.medicare.backend.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cart_item")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne(fetch =FetchType.EAGER)
	private Product product;

	public CartItem() {
		this.id = null;
		this.quantity = 1;
	}
	
	public CartItem(Long id, Cart cart, Product product) {
		this.id = id;
		this.cart = cart;
		this.product = product;
		this.quantity =1;
	}

	public Long getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CartItem [id=");
		builder.append(id);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", cart=");
		builder.append(cart);
		builder.append(", product=");
		builder.append(product);
		builder.append("]");
		return builder.toString();
	}
	
	
}
