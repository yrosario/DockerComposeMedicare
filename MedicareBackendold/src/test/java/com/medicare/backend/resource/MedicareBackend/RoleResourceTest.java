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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import com.medicare.backend.model.Role;
import com.medicare.backend.service.RoleServiceImpl;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class RoleResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RoleServiceImpl roleService;
	
	private List<Role> roles;
	
	@BeforeEach
	private void setUp() {
		
		roles = new ArrayList<>();
		
		roles.add(new Role(1L, "User"));
		roles.add(new Role(2L, "Admin"));
		
	}
	
	@Test
	public void getRole_test() throws Exception {

		when(roleService.findAll()).thenReturn(roles);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/v1/role").accept(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("$[0].id").value("1"))
				.andExpect(jsonPath("$[0].name").value("User"))
				.andExpect(status().isOk())
				.andReturn();		
	}
	
	@Test
	public void getRoleById_test() throws Exception {

		when(roleService.findById(Mockito.anyLong())).thenReturn(roles.get(0));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/v1/role/1").accept(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("id").value("1"))
				.andExpect(jsonPath("name").value("User"))
				.andExpect(status().isOk())
				.andReturn();		
	}
	
	@Test
	public void saveRole_test() throws Exception {

		when(roleService.save(Mockito.any(Role.class))).thenReturn(roles.get(0));
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/v1/role").accept(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"User\"}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("id").value("1"))
				.andExpect(jsonPath("name").value("User"))
				.andExpect(status().isCreated())
				.andReturn();		
	}
	
	@Test
	public void updateRole_test() throws Exception {
		
		Role role = roles.get(0);
		role.setName("TestRole");

		when(roleService.findById(Mockito.anyLong())).thenReturn(role);
		when(roleService.save(Mockito.any(Role.class))).thenReturn(role);
		
		RequestBuilder request = MockMvcRequestBuilders
				.put("/api/v1/role")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":1,\"name\":\"TestRole\"}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("id").value("1"))
				.andExpect(jsonPath("name").value("TestRole"))
				.andExpect(status().isCreated())
				.andReturn();		
	}
	
	@Test
	public void deleteRoleById_test() throws Exception {
		
		Role role = roles.get(0);
		role.setName("TestRole");

		when(roleService.findById(Mockito.anyLong())).thenReturn(role);
		when(roleService.delete(Mockito.anyLong())).thenReturn(true);
		
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/api/v1/role/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();		
	}
	
}
