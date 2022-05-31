package com.example.demo.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Data;

@Data
public class UserStaff {
	private int makhachhang;
	private String tenkhachhang;
	private String sodienthoaiKH;
	private int manhanvien;
	private String tennhanvien;
	private String sodienthoaiNV;
	private int tongtien;
	private String trangthai;
	private Date ngay;
	private Date ngayxacnhan;
	private String lydo;
	
	public String getMoney() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		return (String) nf.format(this.tongtien);
	}
	
	public String getFormatday() throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		return fm.format(ngay);
	}
}
