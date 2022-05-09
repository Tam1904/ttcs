package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Product;

@Repository
public class ProductDaoAPI implements ProductDao {

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public void addProduct(Product p) {
		String sql = "insert into sanpham value(?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, p.getMa().trim(), p.getTen().trim(), p.getMota().trim(), Integer.parseInt(p.getSoluong().trim()),
				Integer.parseInt(p.getDongia().trim()), p.getThuonghieu().trim(), p.getTyped().trim(), p.getSize().trim() , p.getAnh().trim());
	}

	@Override
	public List<Product> search(String name) {
		String sql = "select * from sanpham where (select instr(ten,?))>0";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), name);
		return products;
	}

	@Override
	public void updateProduct(Product product) {
		removeProduct(product.getMa());
		addProduct(product);
	}

	@Override
	public void removeProduct(String ma) {
		String sql = "delete from sanpham where ma = ?";
		jdbc.update(sql, ma);
	}

	@Override
	public Boolean exitsProduct(String ma) {
		String sql = "select * from sanpham where ma = ?";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), ma);
		if (products.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Product getById(String ma) {
		String sql = "select * from sanpham where ma = ?";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), ma);
		return products.get(0);
	}

	@Override
	public List<Comment> getComment(String ma) {
		String sql = "select masanpham, ten, noidung,thoigian from binhluan,nguoidung where masanpham = ? and makhachhang = nguoidung.ma";
		List<Comment> comments = jdbc.query(sql, this::mapRowToComment, ma);
		return comments;
	}

	private Comment mapRowToComment(ResultSet rs, int rowNum) throws SQLException {
		return new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
	}

	@Override
	public List<Product> getFourProduct(String ma) {
		Product product = getById(ma);
		String sql = "select * from sanpham where typed = ? and ma != ?";
		List<Product> products= jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class),product.getTyped(),ma);
		if(products.size()<4) {
			return products;
		}
		return products.subList(0, 4);
	}

	@Override
	public List<String> getTyped() {
		String sql = "select DISTINCT(typed) from sanpham ";
		return jdbc.queryForList(sql,String.class);
	}

	@Override
	public List<Product> getByTyped(String typed) {
		String sql = "select * from sanpham where (select instr(typed,?))>0";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), typed);
		if(products.size()<4) {
			return products;
		}
		return products.subList(0, 4);
	}
	
	@Override
	public List<Product> getByTyped12(String typed,int index,String text) {
		String sql = "select * from sanpham where (select instr(typed,?))>0 and (select instr(ten,?))>0";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), typed,text);
		if(products.size()<=12) {
			return products;
		}
		if(products.size()%12==0) {
			return products.subList(12*index, 12*(index+1));
		}
		return products.subList(12*index, products.size());
	}

	@Override
	public List<Product> getByMoney(String typed, int min, int max,String text) {
		String sql = "select * from sanpham where (select instr(typed,?))>0 and (select instr(ten,?))>0 and dongia >= CAST(? AS UNSIGNED) and dongia < CAST(? AS UNSIGNED) ";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), typed,text,min,max);
		return products;
	}

	@Override
	public List<Map.Entry<String, Integer>> getThuongHieu(String typed,int min,int max,String text) {
		String sql = "select thuonghieu,count(thuonghieu) from sanpham where (select instr(typed,?))>0 and (select instr(ten,?))>0 and dongia >= CAST(? AS UNSIGNED) and dongia < CAST(? AS UNSIGNED)  group by thuonghieu";
		List<Map.Entry<String, Integer>> map = jdbc.query(sql, this::mapRowToEntry,typed,text,min,max);
		return map;
	}
	
	
	private Map.Entry<String, Integer> mapRowToEntry(ResultSet rs, int rowNum) throws SQLException {
		return new AbstractMap.SimpleEntry<>(rs.getString(1), rs.getInt(2));
	}

	@Override
	public List<Product> getProductTop(String typed,String text) {
		String sql="select * from sanpham where (select instr(typed,?))>0 and (select instr(ten,?))>0 order by lanmua LIMIT 10";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class),typed,text);
		return products;
	}

	@Override
	public List<Product> getMinMaxPrice(String typed) {
		String sql = "select * from sanpham where typed = ? order by dongia LIMIT 1";
		Product productOne = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), typed).get(0);
		sql = "select * from sanpham where typed = ? order by dongia desc LIMIT 1";
		Product productTwo = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), typed).get(0);
		return Arrays.asList(productOne,productTwo);
	}

	
}
