package com.example.demo.model;

import lombok.Data;

@Data
public class Account {
	private String username;
	private String password;
	private String confirm;
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Account() {
		this.username = "";
		this.password = "";
		this.confirm = "";
	}
	
	public Account(String username, String password,String confirm) {
		this.username = username;
		this.password = password;
		this.confirm = confirm;
	}
}
