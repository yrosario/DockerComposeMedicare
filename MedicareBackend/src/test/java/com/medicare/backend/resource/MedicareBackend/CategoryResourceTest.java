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

import com.medicare.backend.model.Category;
import com.medicare.backend.service.CategoryServiceImpl;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class CategoryResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoryServiceImpl categoryService;
	
	private List<Category> categories;
	
	@BeforeEach
	private void setUp() {
		
		categories = new ArrayList<>();
		
		categories.add(new Category(1L, "Pain Relief"));
		categories.add(new Category(2L, "Cold Flu Medicine"));
		
	}
	
	@Test
	public void getRole_test() throws Exception {

		when(categoryService.findAll()).thenReturn(categories);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/v1/category").accept(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("$[0].id").value("1"))
				.andExpect(jsonPath("$[0].name").value("Pain Relief"))
				.andExpect(status().isOk())
				.andReturn();		
	}
	
	@Test
	public void getRoleById_test() throws Exception {

		when(categoryService.findById(Mockito.anyLong())).thenReturn(categories.get(0));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/v1/category/1").accept(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("id").value("1"))
				.andExpect(jsonPath("name").value("Pain Relief"))
				.andExpect(status().isOk())
				.andReturn();		
	}
	
	@Test
	public void saveRole_test() throws Exception {

		when(categoryService.save(Mockito.any(Category.class))).thenReturn(categories.get(0));
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/v1/category").accept(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Vitamin\"}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("name").value("Pain Relief"))
				.andExpect(status().isCreated())
				.andReturn();		
	}
	
	@Test
	public void updateRole_test() throws Exception {
		
		Category category = categories.get(0);
		category.setName("TestCategory");

		when(categoryService.findById(Mockito.anyLong())).thenReturn(category);
		when(categoryService.update(Mockito.any(Category.class))).thenReturn(category);
		
		RequestBuilder request = MockMvcRequestBuilders
				.put("/api/v1/category")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":1,\"name\":\"TestCategory\"}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(jsonPath("id").value("1"))
				.andExpect(jsonPath("name").value("TestCategory"))
				.andExpect(status().isCreated())
				.andReturn();		
	}
	
	@Test
	public void deleteRoleById_test() throws Exception {
		
		Category category = categories.get(0);
		category.setName("TestRole");

		when(categoryService.findById(Mockito.anyLong())).thenReturn(category);
		when(categoryService.delete(Mockito.anyLong())).thenReturn(true);
		
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/api/v1/category/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		 
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();		
	}
	
}
