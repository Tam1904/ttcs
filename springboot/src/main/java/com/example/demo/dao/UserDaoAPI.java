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
		
		return 0;
	}

	@Override
	public int update(User user, int id) {
		
		return 0;
	}

	@Override
	public int delete(int id) {
		
		return 0;
	}

	
	@Override
	public User getUser(String username, String password) {
		
		String sql = "select * from nguoidung where username = ? and pass = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
		return user.get(0);
	}

	@Override
	public Boolean findUser(String username,String password) {
		
		String sql = "select * from nguoidung where username = ? and pass = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
		if(user.size()==1) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean exitsUser(String username) {
		
		String sql = "select * from nguoidung where username = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username.trim());
		if(user.size()==1) {
			return true;
		}
		return false;
	}

	@Override
	public void addUser(String name, String password) {
		
		String sql = "insert into nguoidung(username,pass,typed) value(?,?,?)";
		jdbc.update(sql, name,password,"Customer");
	}

	@Override
	public void update(User user) {
		
		String sql = "update nguoidung set ten=?,username=?,pass=?,sodienthoai=?,diachi=?,email=?,typed=? where ma = CAST(? AS UNSIGNED);";
		jdbc.update(sql,user.getTen(),user.getUsername(),user.getPass(),user.getSodienthoai(),user.getDiachi(),user.getEmail(),user.getTyped(),user.getMa());
	}

	@Override
	public User getUser(int id) {
		
		String sql = "select * from nguoidung where ma=? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), id);
		return user.get(0);
	}

	@Override
	public List<User> getListUser() {
		String sql = "select * from nguoidung ";
		List<User> users = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	@Override
	public Boolean exitsUserMa(String username) {
		String sql = "select * from nguoidung where username = ? LIMIT 1";
		List<User> user = jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class), username);
		if(user.size()==1) {
			return true;
		}
		return false;
	}

	@Override
	public void addUser(User user) {
		String sql = "insert into nguoidung(ten,username,pass,sodienthoai,diachi,typed,email)  value(?,?,?,?,?,?,?)";
		jdbc.update(sql, user.getTen(),user.getUsername(),user.getPass(),user.getSodienthoai(),user.getDiachi(),user.getTyped(),user.getEmail());
	}

	@Override
	public void removeUser(String list) {
		String [] listItem = list.trim().split("\\s+");
		for(int i=0;i<listItem.length;i++) {
			String sql = "delete from nguoidung where ma = ?";
			jdbc.update(sql,listItem[i]);
		}
	}

	@Override
	public List<User> search(String s) {
		String sql = "select * from nguoidung where (select instr(ten,?))>0";
		return jdbc.query(sql, new BeanPropertyRowMapper<User>(User.class),s);
	}
	
}

