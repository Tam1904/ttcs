package com.example.demo.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDTO;

@CrossOrigin
@RestController
public class productAPI {
	
	@PostMapping(value= "/post")
	public ProductDTO creatProductDTO(@RequestBody ProductDTO model) {
		return model;
	}
}
