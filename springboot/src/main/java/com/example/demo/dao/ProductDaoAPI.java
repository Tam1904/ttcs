package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

@Repository
public class ProductDaoAPI implements ProductDao {
	
	@Autowired
	JdbcTemplate jdbc;
	@Override
	public void addProduct(Product p) {
		// TODO Auto-generated method stub
		String sql = "insert into sanpham value(?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, p.getMa(),p.getTen(),p.getMota(),Integer.parseInt(p.getSoluong()),Integer.parseInt(p.getDongia()),p.getThuonghieu(),p.getTyped(),p.getSize(),p.getAnh());
	}
	@Override
	public List<Product> search(String name) {
		// TODO Auto-generated method stub
		String sql = "select * from sanpham where (select instr(ten,?))>0" ;
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class),name);
		return products;
	}
	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		removeProduct(product.getMa());
		addProduct(product);
	}
	@Override
	public void removeProduct(String ma) {
		// TODO Auto-generated method stub
		String sql = "delete from sanpham where ma = ?";
		jdbc.update(sql,ma);
	}
	@Override
	public Boolean exitsProduct(String ma) {
		// TODO Auto-generated method stub
		String sql = "select * from sanpham where ma = ?" ;
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class),ma);
		if(products.size()>0) {
			return true;
		}
		return false;
	}

}
