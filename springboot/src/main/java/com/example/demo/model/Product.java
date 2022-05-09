package com.example.demo.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

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
	private String lanmua;

	public Product() {
		this.ma = "";
		this.ten = "";
		this.mota = "";
		this.soluong = "";
		this.dongia = "";
		this.thuonghieu = "";
		this.typed = "";
		this.size = "";
		this.anh = "";
		this.lanmua = "";
	}

	public Product(String ma, String ten, String mota, String soluong, String dongia, String thuonghieu, String typed,
			String size, String anh) {
		this.ma = ma;
		this.ten = ten;
		this.mota = mota;
		this.soluong = soluong;
		this.dongia = dongia;
		this.thuonghieu = thuonghieu;
		this.typed = typed;
		this.size = size;
		this.anh = anh;
		this.lanmua = "";
	}
	
	public Product(String ma, String ten, String mota, String soluong, String dongia, String thuonghieu, String typed,
			String size, String anh,String lanmua) {
		this.ma = ma;
		this.ten = ten;
		this.mota = mota;
		this.soluong = soluong;
		this.dongia = dongia;
		this.thuonghieu = thuonghieu;
		this.typed = typed;
		this.size = size;
		this.anh = anh;
		this.lanmua = lanmua;
	}

	public String getToken() {
		String[] w = this.anh.split("\\s+");
		return w[0];
	}
	
	public List<String> getList() {
		String[] w = this.anh.split("\\s+");
		List<String> s = new ArrayList<>();
		for(int i=0;i<w.length;i++) {
			s.add(w[i]);
		}
		return s;
	}


	public String getFormat() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		return (String) nf.format(Integer.parseInt(dongia));
	}
	
	public List<Integer> getListSize() {
		StringTokenizer token = new StringTokenizer(this.size.trim(),"-");
		String min = token.nextToken();
		String max = token.nextToken();
		List<Integer> s = new ArrayList<>();
		for(int i= Integer.parseInt(min);i<=Integer.parseInt(max);i++) {
			s.add(i);
		}
		return s;
	}
	public String getCaculate() {
		int sl = Integer.parseInt(this.soluong);
		int dg = Integer.parseInt(this.dongia);
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		return (String) nf.format(sl*dg);
	}
}
