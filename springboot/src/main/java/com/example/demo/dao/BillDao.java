package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.model.User;

public interface BillDao {
	public void saveBill(User user, List<Product> products,String ghichu);
}
