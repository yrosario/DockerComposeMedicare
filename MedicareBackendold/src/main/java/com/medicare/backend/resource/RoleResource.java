package com.medicare.backend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.backend.model.Role;
import com.medicare.backend.service.GenericService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleResource {
	
	@Autowired
	private GenericService<Role,Long> roleService;
	
	@PostMapping()
	public ResponseEntity<Role> addRole(@RequestBody Role r){
		Role Role = roleService.save(r);
		if(Role != null) {
			return new ResponseEntity<Role>(Role, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Role>(Role, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping()
	public ResponseEntity<List<Role>> getRoleById(){
		return new ResponseEntity<List<Role>>(roleService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getRoleById(@PathVariable Long id){
		Role Role = roleService.findById(id);
		
		if(Role != null) {
			return new ResponseEntity<>(Role, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(Role, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable Long id){
		Role Role = roleService.findById(id);
		if(Role != null) {
			roleService.delete(id);
			return new ResponseEntity<>("Role was deleted", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Role was not found", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping()
	public ResponseEntity<?> updateRole(@RequestBody Role r){
		
		if(r.getId() == null || roleService.findById(r.getId()) == null)  {
			return new ResponseEntity<>("{role:not_found}",HttpStatus.NOT_FOUND);
		}
		
		Role Role = roleService.save(r);
		if(Role != null) {
			return new ResponseEntity<Role>(Role, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Role>(Role, HttpStatus.BAD_REQUEST);
	}
	


}
