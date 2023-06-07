package com.medicare.backend.resource.MedicareBackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;

import com.medicare.backend.model.Role;
import com.medicare.backend.model.User;
import com.medicare.backend.service.RoleServiceImpl;
import com.medicare.backend.service.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class UserResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserServiceImpl userService;
	
	@MockBean
	private RoleServiceImpl roleService;
	
	private User user;
	
	@BeforeEach
	public void setUser() {
		user = new User();
		user.setUid(12L);
		user.setFirstname("Jimmie");
		user.setLastname("Smith");
		user.setEmail("jsmithm@gmail.com");
		user.setUsername("jsmith");
		user.setPassword("password");
		Role role = new Role(1L, "User");
		user.setRole(role);
	}
	
	@Test
	public void getUser_test() throws Exception {

		when(userService.findById(Mockito.anyLong())).thenReturn(user);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/v1/user/12").accept(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("uid").value("12"))
				.andExpect(jsonPath("firstname").value("Jimmie"))
				.andExpect(jsonPath("lastname").value("Smith"))
				.andExpect(jsonPath("password").value("password"))
				.andExpect(jsonPath("username").value("jsmith"))
				.andExpect(jsonPath("$.role.id").value("1"))
				.andExpect(jsonPath("$.role.name").value("User"))
				.andReturn();	

	}
	
	@Test
	public void saveUser_test() throws Exception {
		
		Role role = new Role(2l,"Admin");
		role.setId(2L);
	    user.setRole(role);
	    when(roleService.findById(Mockito.anyLong())).thenReturn(user.getRole());
		when(userService.save(user)).thenReturn(user);
			
		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/v1/user")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"firstname\":\"Bob\",\"lastname\":\"Washington\",\"email\":\"bwashington@gmail.com\",\"password\":\"password\","
						+ "\"username\":\"bwashington\",\"role\":{\"id\":2,\"name\":\"Admin\"}}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("firstname").value("Bob"))
				.andExpect(jsonPath("lastname").value("Washington"))
				.andExpect(jsonPath("password").value("password"))
				.andExpect(jsonPath("username").value("bwashington"))
				.andExpect(jsonPath("email").value("bwashington@gmail.com"))
				.andExpect(jsonPath("$.role.id").value("2"))
				.andExpect(jsonPath("$.role.name").value("Admin"))
				.andReturn();	
	}
	
	@Test
	public void updateUser_test() throws Exception {
		
		Role role = new Role(2L,"Admin");
		role.setId(2L);
	    user.setRole(role);
	    
	    when(roleService.findById(Mockito.anyLong())).thenReturn(user.getRole());
	    when(userService.findById(Mockito.anyLong())).thenReturn(user);
		when(userService.save(user)).thenReturn(user);

			
		RequestBuilder request = MockMvcRequestBuilders
				.put("/api/v1/user")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"uid\":12,\"firstname\":\"Bob\",\"lastname\":\"Washington\",\"email\":\"bwashington@gmail.com\",\"password\":\"password\","
						+ "\"username\":\"bwashington\",\"role\":{\"id\":2,\"name\":\"Admin\"}}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
	    mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("firstname").value("Bob"))
				.andExpect(jsonPath("lastname").value("Washington"))
				.andExpect(jsonPath("password").value("password"))
				.andExpect(jsonPath("username").value("bwashington"))
				.andExpect(jsonPath("email").value("bwashington@gmail.com"))
				.andExpect(jsonPath("$.role.id").value("2"))
				.andExpect(jsonPath("$.role.name").value("Admin"))
				.andReturn();	
	}
	
	@Test
	public void deleteUser_test() throws Exception {
		
		Role role = new Role(2L,"Admin");
		role.setId(2L);
	    user.setRole(role);
	    
	    when(userService.findById(Mockito.anyLong())).thenReturn(user);
	    when(userService.delete(Mockito.anyLong())).thenReturn(true);
		//when(userService.save(user)).thenReturn(user);

			
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/api/v1/user/12")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
	    mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();	
	}
	
	
	@Test
	public void deleteUser_not_found_test() throws Exception {

	    when(userService.delete(Mockito.anyLong())).thenReturn(true);

			
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/api/v1/user/12")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		        
	    mockMvc.perform(request)
				.andExpect(status().isNotFound())
				.andReturn();	
	}

}
