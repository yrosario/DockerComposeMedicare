package com.medicare.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User cartUser;
	
	@OneToMany(mappedBy="cart", fetch=FetchType.LAZY, cascade = { CascadeType.ALL})
    @JsonIgnore
	private List<CartItem> cartItems = new ArrayList<CartItem>();

	public Cart() {
	}


	public Cart(User cartUser) {
		this();
		this.cartUser = cartUser;
		
	}


	public User getCartUser() {
		return cartUser;
	}


	public void setCartUser(User cartUser) {
		this.cartUser = cartUser;
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(CartItem cartItem) {
		this.cartItems.add(cartItem);
	}


	
	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}


	@Override
	public String toString() {
		return "Cart [id=" + id + ", cartUser=" + cartUser + ", cartItems=" + cartItems + "]";
	}
	
	
	
	
	
	
}
