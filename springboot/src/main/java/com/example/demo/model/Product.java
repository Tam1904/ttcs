package com.example.demo.model;

import lombok.Data;

@Data
public class Product {
	private String ma;
	private String ten;
	private String mota;
	private String soluong;
	private String dongia;
	private String thuonghieu;
	private String typed;
	private String size;
	private String anh;
	
	public Product() {
		this.ma= "";
		this.ten = "";
		this.mota = "";
		this.soluong = "";
		this.dongia = "";
		this.thuonghieu = "";
		this.typed = "";
		this.size = "";
		this.anh = "";
	}
	
	public Product(String ma, String ten, String mota, String soluong, String dongia, String thuonghieu, String typed, String size,
			String anh) {
		this.ma= ma;
		this.ten = ten;
		this.mota = mota;
		this.soluong = soluong;
		this.dongia = dongia;
		this.thuonghieu = thuonghieu;
		this.typed = typed;
		this.size = size;
		this.anh = anh;
	}
	
	public String getToken() {
		String [] w = this.anh.split("\\s+");
		return w[0];
	}
}
