package com.example.demo.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
@Repository
public interface UserDao {
	int save(User user);
	int update(User user, int id);
	int delete(int id);
	User getUser(String username, String password);
	Boolean findUser(String username, String password);
	Boolean exitsUser(String username);
	void addUser(String name,String password);
	void addUser(User user);
	void update(User user);
	User getUser(int id);
	List<User> getListUser();
	Boolean exitsUserMa(String ma);
	void removeUser(String list);
	List<User> search(String s);
}

