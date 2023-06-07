package com.medicare.backend.resource.MedicareBackend;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org. mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.medicare.backend.model.Cart;
import com.medicare.backend.model.CartItem;
import com.medicare.backend.model.Category;
import com.medicare.backend.model.Product;
import com.medicare.backend.model.User;
import com.medicare.backend.service.CartItemServiceImpl;
import com.medicare.backend.service.CartServiceImpl;
import com.medicare.backend.service.ProductServiceImpl;
import com.medicare.backend.service.UserServiceImpl;



@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class CartResourceTest {
	
	@Autowired
	private MockMvc mockMvc;


	@MockBean
	private CartServiceImpl cartService;
	
	@MockBean
    private CartItemServiceImpl cartItemService;
	
	@MockBean
	private UserServiceImpl userService;
	
	@MockBean
	private ProductServiceImpl productService;

	private User user;
	private Cart cart;
	private List<CartItem> cartItems;
	
	
	@BeforeEach
	public  void setup(){
		user = new User();
		user.setFirstname("John");
		user.setLastname("William");
		user.setUid(1l);
		
		
		cart = new Cart();
		cartItems = new ArrayList<>();
		Category category = new Category(1L,"Pain Relief");
		
		//Cart Items here
		cartItems.add(new CartItem(1L, cart, new Product(1L, "Aspirin", "Aspirin inc", 8.99f, true,40,null,
				category, 45)));
		
		
		cartItems.stream().forEach(item -> {cart.setCartItems(item);});
        cart.setCartUser(user);
        user.setCart(cart);
	}
	
	@Test
	public void getCartItem_no_user_test() throws Exception {
		

		when(cartService.findById(1l)).thenReturn(null);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/v1/cart/user/-1").accept(MediaType.APPLICATION_JSON);
		        
	    MvcResult result = mockMvc.perform(request)
			.andExpect(status().isNotFound())
			.andReturn();	
	
	    assertEquals("{User:Not_Found}", result.getResponse().getContentAsString());
	
	}
	
	@Test
	public void getCartItem_user_test() throws Exception {
        
        when(userService.findById(Mockito.anyLong())).thenReturn(user);
        this.cartItems.get(0).setQuantity(1);
	    when(cartItemService.findAllById(Mockito.anyLong())).thenReturn(cartItems);
	    
	    RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/cart/user/1")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		    .andExpect(jsonPath("$[0].id").value(1))
		    .andExpect(jsonPath("$[0].product.pid").value(1))
		    .andExpect(jsonPath("$[0].product.brand").value("Aspirin inc"))
		    .andExpect(jsonPath("$[0].product.price").value(8.99))
		    .andExpect(jsonPath("$[0].product.active").value(true))
		    .andExpect(jsonPath("$[0].product.numberOfViews").value(40))
		    .andExpect(jsonPath("$[0].product.qty").value(0))
		    .andExpect(jsonPath("$[0].product.category.id").value(1))
		    .andExpect(jsonPath("$[0].product.category.name").value("Pain Relief"))
			.andExpect(status().isOk());
		

	}
	
	@Test
	public void addItemToCart_test() throws Exception {
     	
        when(userService.findById(Mockito.anyLong())).thenReturn(user);
        when(productService.findById(Mockito.anyLong())).thenReturn(cartItems.get(0).getProduct());
	    when(cartItemService.save(Mockito.any(CartItem.class))).thenReturn(cartItems.get(0));
	    
	    RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/cart/user/1")
				.accept(MediaType.APPLICATION_JSON)
	    		.content("{\"productId\":1}")
	    		.contentType(MediaType.APPLICATION_JSON);
	    
		
		mockMvc.perform(request)
		    .andExpect(jsonPath("$[0].id").value(1))
		    .andExpect(jsonPath("$[0].quantity").value(1))
		    .andExpect(jsonPath("$[0].product.pid").value(1))
		    .andExpect(jsonPath("$[0].product.brand").value("Aspirin inc"))
		    .andExpect(jsonPath("$[0].product.price").value(8.99))
		    .andExpect(jsonPath("$[0].product.active").value(true))
		    .andExpect(jsonPath("$[0].product.numberOfViews").value(40))
		    .andExpect(jsonPath("$[0].product.qty").value(0))
		    .andExpect(jsonPath("$[0].product.category.id").value(1))
		    .andExpect(jsonPath("$[0].product.category.name").value("Pain Relief"))
			.andExpect(status().isCreated());
		

	}
	
	
	@Test
	public void deleteItemToCart_test() throws Exception {
     	
        when(userService.findById(Mockito.anyLong())).thenReturn(user);
	    when(cartItemService.delete(Mockito.anyLong())).thenReturn(true);
	    
	    RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/cart/user/1/item/1")
				.accept(MediaType.APPLICATION_JSON);
	    
		
		mockMvc.perform(request)
			.andExpect(status().isOk());
		

	}
	

}
