package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
import com.example.demo.model.User;

@Repository
public class BillDaoAPI implements BillDao{
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public void saveBill(User user, List<Product> products,String ghichu) {
		String sql = "insert into hoadon(makhachhang,ngay,masanpham,size,soluong,trangthai,ghichu) values(CAST(? AS UNSIGNED),now(),?,CAST(? AS UNSIGNED),CAST(? AS UNSIGNED),?,?)";
		for(Product product : products) {
			jdbc.update(sql,user.getMa(),product.getMa(),product.getSoluong(),product.getSoluong(),"Chờ xác nhận",ghichu);
		}
	}

}
