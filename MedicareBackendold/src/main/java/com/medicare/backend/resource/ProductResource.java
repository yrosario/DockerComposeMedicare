package com.medicare.backend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.medicare.backend.model.Category;
import com.medicare.backend.model.Product;
import com.medicare.backend.service.GenericService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {
	
	@Autowired
	private GenericService<Product,Long> productService;
	
	@Autowired
	private GenericService<Category, Long> categoryService;
	
	@SuppressWarnings("unused")
	@PostMapping()
	public ResponseEntity<?> addProduct(@RequestBody Product p){
		
		Category category = null;
		
		if(categoryService.findById(p.getCategory().getId()) != null ) {
			category = categoryService.findById(p.getCategory().getId());
		}else {
			return new ResponseEntity<>("{category:not_found}", HttpStatus.NOT_FOUND);
		}
		
		Product product = productService.save(p);
		
		product.setCategory(category);
		
		if(product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Product>(product, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping()
	public ResponseEntity<List<Product>> getProductById(){
		return new ResponseEntity<List<Product>>(productService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id){
		Product Product = productService.findById(id);
		
		if(Product != null) {
			return new ResponseEntity<>(Product, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		Product Product = productService.findById(id);
		if(Product != null) {
			productService.delete(id);
			return new ResponseEntity<>("{Product:deleted}", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("{Product:Not_found}", HttpStatus.NOT_FOUND);
	}
	
	
	@SuppressWarnings("unused")
	@PutMapping()
	public ResponseEntity<?> updateProduct(@RequestBody Product p){
		
		Category category = null;
		
		if(categoryService.findById(p.getCategory().getId()) != null ) {
			category = categoryService.findById(p.getCategory().getId());
		}else {
			return new ResponseEntity<>("{category:not_found}", HttpStatus.NOT_FOUND);
		}
		
		if(p.getPid() == null || productService.findById(p.getPid()) == null) {
			return new ResponseEntity<>("{product:not_found}", HttpStatus.NOT_FOUND);
		}
		
		Product product = productService.update(p);
		
		product.setCategory(category);
		
		if(product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Product>(product, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/{id}/upload")
	public ResponseEntity<?> fileUpload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file)
	{	
		Product product = productService.findById(id);
		if(product == null) {
			return new ResponseEntity<>("{Product:NOT_FOUND", HttpStatus.NOT_FOUND);
		}
		
		try {
			byte[] byteImage = file.getBytes();
			product.setImageBlo(byteImage);
			productService.save(product);
			
			return new ResponseEntity<>("Image:Creation_Successfully_Created", HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}/image")
	public ResponseEntity<?> getImage(@PathVariable("id") Long id){
		Product product = productService.findById(id);
		if(product == null) {
			return new ResponseEntity<>("Product:Not_Found", HttpStatus.NOT_FOUND);
		}
		
		byte[] encodeImg = product.getImageBlob();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(encodeImg);
	}

}
