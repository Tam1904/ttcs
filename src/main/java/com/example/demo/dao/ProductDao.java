package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Product;

@Repository
public interface ProductDao {
	void addProduct(Product product);
	void updateProduct(Product product);
	void removeProduct(String ma);
	List<Product> search(String name);
	Boolean exitsProduct(String ma);
	Product getById(String ma);
	List<Comment> getComment(String ma);
	List<Product> getFourProduct(String ma);
	List<String> getTyped();
	List<Product> getByTyped(String typed);
	List<Product> getByTyped12(String typed,int index,String text);
	int getSoluong(String typed,int index,String text);
	List<Product> getByMoney(String typed,int min,int max,String text);
	List<Map.Entry<String, Integer>> getThuongHieu(String typed,int min,int max,String text);
	List<Product> getProductTop(String typed,String text);
	List<Product> getMinMaxPrice(String typed);
	Boolean getQuyenBinhLuan(String masanpham,int makhachhang);
	void upComment(String masanpham,int makhachhang, String comment);
}
