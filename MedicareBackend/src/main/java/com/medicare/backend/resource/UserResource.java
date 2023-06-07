package com.medicare.backend.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

import com.medicare.backend.model.Role;
import com.medicare.backend.model.User;
import com.medicare.backend.service.GenericService;
import com.medicare.backend.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private GenericService<Role, Long> roleService;
	
	private static final List<String> userFieldsList = new ArrayList<>();
	
	static {
		userFieldsList.add("firstname");
		userFieldsList.add("lastname");
		userFieldsList.add("email");
		userFieldsList.add("username");
		userFieldsList.add("password");
		userFieldsList.add("role");
		//userFieldsList.add("products");
					  
	}
	
	@PostMapping()
	public ResponseEntity<?> addUser(@RequestBody Map<String,Object> map){
	
		//Validate map all input fields return bad request if a field is missing
		for(String field : userFieldsList) {
			if(!map.containsKey(field)) return new ResponseEntity<>("{missing_field : " + field + "}", HttpStatus.BAD_REQUEST);
		}
		
		//Create user and set values from map
		User user = new User();
		user.setFirstname(map.get("firstname").toString());
		user.setLastname(map.get("lastname").toString());
		user.setEmail(map.get("email").toString());
		user.setPassword(map.get("password").toString());
		user.setUsername(map.get("username").toString());
		
		//Check if User exist
		User userExist = userService.findByUsername(user.getUsername());
		if(userExist != null) {
			return new ResponseEntity<>("{User:User_already_exist}", HttpStatus.CONFLICT);
		}
		
		//Deserialize Collections object of Products from map and assign it to the user
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
				
		
		//Parse id value from role key and retrieve the role from the database then assign it to the user
		Role role = null;
		try {
			String jsonString = new ObjectMapper().writeValueAsString(map.get("role"));
			role = objectMapper.readValue(jsonString, new TypeReference<Role>() {});
		}catch(JsonProcessingException e) {
			return new ResponseEntity<>("{exception:bad_products_field " + e, HttpStatus.BAD_REQUEST);
		}
		
		role = roleService.findById(role.getId());
		if(role == null) {
			return new ResponseEntity<>("{not_found:role}", HttpStatus.NOT_FOUND);
		}
		
		user.setRole(role);
				

		//Save user to the database
		userService.save(user);

		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}

	
	@GetMapping()
	public ResponseEntity<List<User>> getUserById(){
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id){
		User user = userService.findById(id);
		
		if(user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		User user = userService.findById(id);
		if(user != null) {
			userService.delete(id);
			return new ResponseEntity<>("User was deleted", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("{not_found:user}", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping()
	public ResponseEntity<?> updateUser(@RequestBody Map<String,Object> map){
		
		if(!map.containsKey("uid")) return new ResponseEntity<>("{missing_field : uid}", HttpStatus.BAD_REQUEST);
	
		//Validate map all input fields return bad request if a field is missing
		for(String field : userFieldsList) {
			if(!map.containsKey(field)) return new ResponseEntity<>("{missing_field : " + field + "}", HttpStatus.BAD_REQUEST);
		}
		

		
		//Create user and set values from map
		User user = new User();
		user.setFirstname(map.get("firstname").toString());
		user.setLastname(map.get("lastname").toString());
		user.setUsername(map.get("username").toString());
		user.setEmail(map.get("email").toString());
		user.setPassword(map.get("password").toString());
		
		//Deserialize user id and assign it to the users
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Long id = null;
		try {
			String jsonString = new ObjectMapper().writeValueAsString(map.get("uid"));
			id = objectMapper.readValue(jsonString, new TypeReference<Long>() {});
			user.setUid(id);
			

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("{exception:user_field}",HttpStatus.BAD_REQUEST);
		}
		
		//Return error if user doesn't exist
		if(userService.findById(id) == null) {
			return new ResponseEntity<>("{exception:user_not_found}", HttpStatus.NOT_FOUND);
		}
				
		
		//Parse id value from role key and retrieve the role from the database then assign it to the user
		Long roleId = null;
		try {
			String jsonString = new ObjectMapper().writeValueAsString(map.get("role"));
			Role role = objectMapper.readValue(jsonString, new TypeReference<Role>() {});
			System.out.println(role.getId());
			user.setUid(role.getId());
			roleId = role.getId();
			

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("{exception:user_field}" + id,HttpStatus.BAD_REQUEST);
		}
		
		Role role = roleService.findById(roleId);
		if(role == null) {
			return new ResponseEntity<>("{not_found:role}", HttpStatus.NOT_FOUND);
		}
		
		user.setRole(role);
		
		userService.update(user);

		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}

	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map){
		
		String username = map.get("username");
		String password = map.get("password");
		
		User user = userService.findByUsername(username);
		if(user == null) {
			return new ResponseEntity<>("Login:User_Not_Foudn", HttpStatus.NOT_FOUND);
		}
		
		
		System.out.println("Password " + password + " USER " + user.getPassword());
		if(password.equals(user.getPassword())) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Login:Bad_Password", HttpStatus.UNAUTHORIZED);
	}
	


}
