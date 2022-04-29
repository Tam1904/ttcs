package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

@Repository
public interface ProductDao {
	void addProduct(Product product);
	void updateProduct(Product product);
	void removeProduct(String ma);
	List<Product> search(String name);
	Boolean exitsProduct(String ma);
}
