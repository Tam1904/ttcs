package com.example.demo.model;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Data;

@Data
public class BillTemp {
	private int makhachhang;
	private String ten;
	private Date ngay;
	private String diachi;
	private String sodienthoai;
	private int tongtien;
	private String trangthai;
	private String lydo;
	
	public String getFormat() {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		return fm.format(ngay);
	}
	
	public String getMoney() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		return (String) nf.format(this.tongtien);
	}
	
	public String getTinhtrang() {
		if(this.trangthai.equals("Chờ xác nhận")) {
			return "1";
		}
		if(this.trangthai.equals("Đã hủy")) {
			return "2";
		}
		if(this.trangthai.equals("Đã giao")) {
			return "3";
		}
		return "4";
	}
}
