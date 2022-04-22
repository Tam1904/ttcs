package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private int ma;
	private String ten;
	private String username;
	private String pass;
	private String sodienthoai;
	private String diachi;
	private String email;
	private String typed;
	
//	public User(int id,String username,String password) {
//		this.id = id;
//		this.name = "";
//		this.username = username;
//		this.password = password;
//		this.sodienthoai = "";
//		this.diachi = "";
//		this.email = "";
//		this.typed = "";
//	}
}
