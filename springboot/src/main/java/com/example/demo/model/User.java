package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
	
	
	public String getTinh() {
		StringTokenizer tk = new StringTokenizer(diachi,",");
		List<String> list = new ArrayList<>();
		while(tk.hasMoreTokens()) {
			list.add(tk.nextToken());
		}
		int n = list.size();
		if(n>0) {
			return list.get(n-1).trim();
		}
		return "";
	}
	
	public String getHuyen() {
		StringTokenizer tk = new StringTokenizer(diachi,",");
		List<String> list = new ArrayList<>();
		while(tk.hasMoreTokens()) {
			list.add(tk.nextToken());
		}
		int n = list.size();
		if(n>=2) {
			return list.get(n-2).trim();
		}
		return "";
	}
	
	public String getXa() {
		StringTokenizer tk = new StringTokenizer(diachi,",");
		List<String> list = new ArrayList<>();
		while(tk.hasMoreTokens()) {
			list.add(tk.nextToken());
		}
		int n = list.size();
		if(n>=3) {
			return list.get(n-3).trim();
		}
		return "";
	}
	
	public String getDia() {
		StringTokenizer tk = new StringTokenizer(diachi,",");
		List<String> list = new ArrayList<>();
		while(tk.hasMoreTokens()) {
			list.add(tk.nextToken());
		}
		int n = list.size();
		if(n>=4) {
			return list.get(n-4).trim();
		}
		return "";
	}
}
