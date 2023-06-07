package com.medicare.backend.resource;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.backend.model.Cart;
import com.medicare.backend.model.CartItem;
import com.medicare.backend.model.Product;
import com.medicare.backend.model.User;
import com.medicare.backend.service.CartItemServiceImpl;
import com.medicare.backend.service.GenericService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/cart/user")
public class CartResource {

	@Autowired
	private GenericService<Cart, Long> cartService;
	
	@Autowired
	private GenericService<User, Long> userService;
	
	@Autowired
	private CartItemServiceImpl cartItemService;
	
	@Autowired
	private GenericService<Product, Long> productService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCartItem(@PathVariable("id") Long id){
		
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<String>("{User:Not_Found}", HttpStatus.NOT_FOUND);
		}
		
		Cart cart = user.getCart();
		
		if(cart == null) {
			return new ResponseEntity<String>("{Cart:Not_Found}", HttpStatus.NOT_FOUND);
		}	
		
		
		return new ResponseEntity<>(cart.getCartItems(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> addToCart(@PathVariable("id") Long id, @RequestBody HashMap<String, Long> request){
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<>("{User:Not_Found}", HttpStatus.NOT_FOUND);
		}
		
		Cart cart = user.getCart();
		if(cart == null) {
			return new ResponseEntity<>("{Cart:Not_found}", HttpStatus.NOT_FOUND);
		}
		
		Long productId = request.get("productId");
		
		if(productId == null) {
			return new ResponseEntity<>("{Invalid:data_passed}", HttpStatus.BAD_REQUEST);
		}
		
		Product product = productService.findById(productId);
		if(product == null) {
			return new ResponseEntity<> ("{product:Not_found}",HttpStatus.NOT_FOUND);
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setProduct(product);
		

		cartService.save(cart);
		cartItemService.save(cartItem);
		
		
		return new ResponseEntity<>(cart.getCartItems(), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}/item/{itemId}")
	public ResponseEntity<?> RemoveItemFromCart(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId){
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<>("{User:Not_found}", HttpStatus.NOT_FOUND);
		}
		
		Cart cart = user.getCart();
		if(cart == null) {
			return new ResponseEntity<>("{Cart:Not_found}", HttpStatus.NOT_FOUND);
		}
		
		
		if(itemId == null) {
			return new ResponseEntity<>("{Invalid:Data_Passed}", HttpStatus.BAD_REQUEST);
		}
		
		
		cartItemService.delete(itemId);
		
		
		return new ResponseEntity<>("{CartItem:Deleted}", HttpStatus.OK);
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity<?>updateCartItem(@PathVariable("id") Long id, @RequestBody CartItem cartItem){
		
		User user = userService.findById(id);
		Cart cart = user.getCart();
		
		CartItem newCartItem = cartItemService.findById(cartItem.getId());
		if(newCartItem == null) {
			return new ResponseEntity<>("{Cart:Item_Not_Found", HttpStatus.NOT_FOUND);
		}
		
		cartItem.setCart(cart);
		CartItem save = cartItemService.save(cartItem);
		return new ResponseEntity<>(save, HttpStatus.OK);
			
	}
}
