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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.medicare.backend.model.Category;
import com.medicare.backend.model.Product;
import com.medicare.backend.service.CategoryServiceImpl;
import com.medicare.backend.service.ProductServiceImpl;



@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest 
public class ProductResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductServiceImpl productService;
	
	@MockBean
	private CategoryServiceImpl categoryService;
	
	private List<Product> products;
	
	@BeforeEach
	public  void setup(){
		
		products = new ArrayList<>();
		Category category = new Category(1L,"Pain Relief");
		
		//Cart Items here
		products.add (new Product(1L, "Aspirin", "Aspirin inc", 
				8.99f, true,40,null, category, 45));
		
		products.add (new Product(2L, "Avil", "Avo; inc", 
				6.99f, true,40,null, category, 34));
		
		
	}
	
	@Test
	public void getProducts_test() throws Exception{
		
		when(productService.findAll()).thenReturn(products);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/product")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].pid").value(1))
				.andExpect(jsonPath("$[0].name").value("Aspirin"))
				.andExpect(jsonPath("$[0].brand").value("Aspirin inc"))
				.andExpect(jsonPath("$[0].price").value(8.99))
				.andExpect(jsonPath("$[0].active").value(true))
				.andExpect(jsonPath("$[0].numberOfViews").value(40))
				.andExpect(jsonPath("$[0].qty").value(0))
				.andExpect(jsonPath("$[0].category.id").value(1))
				.andExpect(jsonPath("$[0].category.name").value("Pain Relief"))
				.andReturn();
				
	}
	
	@Test
	public void getProductById_test() throws Exception{
		
		when(productService.findById(Mockito.anyLong())).thenReturn(products.get(0));
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/product/1")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("pid").value(1))
				.andExpect(jsonPath("name").value("Aspirin"))
				.andExpect(jsonPath("brand").value("Aspirin inc"))
				.andExpect(jsonPath("price").value(8.99))
				.andExpect(jsonPath("active").value(true))
				.andExpect(jsonPath("numberOfViews").value(40))
				.andExpect(jsonPath("qty").value(0))
				.andExpect(jsonPath("category.id").value(1))
				.andExpect(jsonPath("category.name").value("Pain Relief"))
				.andReturn();
				
	}
	
	@Test
	public void saveProduct_test() throws Exception{
		
		when(categoryService.findById(Mockito.anyLong())).thenReturn(products.get(0).getCategory());
		when(productService.save(Mockito.any(Product.class))).thenReturn(products.get(0));
		
		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/product")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Aspirin\",\"brand\":\"Aspirin inc\","
						+ "\"price\":8.99,\"active\":true,\"numberOfViews\":40,\"qty\":0,"
						+ "\"category\":{\"id\":1}}")
	    		.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("pid").value(1))
				.andExpect(jsonPath("name").value("Aspirin"))
				.andExpect(jsonPath("brand").value("Aspirin inc"))
				.andExpect(jsonPath("price").value(8.99))
				.andExpect(jsonPath("active").value(true))
				.andExpect(jsonPath("numberOfViews").value(40))
				.andExpect(jsonPath("qty").value(0))
				.andExpect(jsonPath("category.id").value(1))
				.andReturn();
				
	}
	
	
	@Test
	public void updateProduct_test() throws Exception{
		
		Product product = products.get(0);
		product.setName("Aspirin 50");
		product.setPrice(10.99f);
		product.setActive(false);
		product.setNumberOfViews(100);
		product.setQty(50);
		
		when(categoryService.findById(Mockito.anyLong())).thenReturn(products.get(0).getCategory());
		when(productService.findById(Mockito.anyLong())).thenReturn(product);
		when(productService.update(Mockito.any(Product.class))).thenReturn(product);
		
		RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/product")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"pid\":1,\"name\":\"Aspirin 50\",\"brand\":\"Aspirin inc\","
						+ "\"price\":10.99,\"active\":false,\"numberOfViews\":100,\"qty\":50,"
						+ "\"category\":{\"id\":1}}")
	    		.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("pid").value(1))
				.andExpect(jsonPath("name").value("Aspirin 50"))
				.andExpect(jsonPath("brand").value("Aspirin inc"))
				.andExpect(jsonPath("price").value(10.99))
				.andExpect(jsonPath("active").value(false))
				.andExpect(jsonPath("numberOfViews").value(100))
				.andExpect(jsonPath("qty").value(50))
				.andExpect(jsonPath("category.id").value(1))
				.andReturn();
				
	}
	
	@Test
	public void deleteProduct_test() throws Exception{
		
		when(productService.findById(Mockito.anyLong())).thenReturn(products.get(0));
		when(productService.delete(Mockito.anyLong())).thenReturn(true);
		
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/product/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
				
	}
	
}
