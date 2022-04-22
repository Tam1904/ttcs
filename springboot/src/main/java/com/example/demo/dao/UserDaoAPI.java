package com.example.demo.dao;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
@org.springframework.transaction.annotation.Transactional
public class UserDaoAPI implements UserDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User user, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public User getUser(String username, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from nguoidung where username = ? and pass = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
		return user.get(0);
	}

	@Override
	public Boolean findUser(String username,String password) {
		// TODO Auto-generated method stub
		String sql = "select * from nguoidung where username = ? and pass = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
		if(user.size()==1) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean exitsUser(String username) {
		// TODO Auto-generated method stub
		String sql = "select * from nguoidung where username = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username);
		if(user.size()==1) {
			return true;
		}
		return false;
	}

	@Override
	public void addUser(String name, String password) {
		// TODO Auto-generated method stub
		String sql = "insert into nguoidung(username,pass,typed) value(?,?,?)";
		jdbc.update(sql, name,password,"Customer");
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		String sql = "update nguoidung set ten=?,username=?,sodienthoai=?,diachi=?,email=? where ma= ?";
		jdbc.update(sql,user.getTen(),user.getUsername(),user.getSodienthoai(),user.getDiachi(),user.getEmail(),user.getMa());
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from nguoidung where ma=? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), id);
		return user.get(0);
	}
	
}

