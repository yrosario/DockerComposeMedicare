package com.medicare.backend.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	private String firstname;
	private String lastname;
	@Email 
	private String email;
	private String password;
	private String username;

	@ManyToMany(cascade = { CascadeType.ALL})
	@JoinTable(
			name="o_order",
			joinColumns = { @JoinColumn(name = "uid", nullable = false )},
			inverseJoinColumns = { @JoinColumn(name = "pid", nullable = false ) }
			)

	
	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY)
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	public User() {
		this.cart = new Cart();
		this.cart.setCartUser(this);
	}
	
	public User(String firstname, String lastname, String email, String password, String username) {
		this();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Long getUid() {
		return uid;
	}

	public void setUid(Long id) {
		this.uid = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}




	
	


	
	
	
	
	

}
