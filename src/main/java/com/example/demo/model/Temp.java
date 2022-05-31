package com.example.demo.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Temp {
	private String ngay;
	private String soluong;
	private String tongtien;
	private String trangthai;
	private String lydo;
	
	public String getFormat() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		return (String) nf.format(Integer.parseInt(this.tongtien));
	}
	public boolean check() {
		if(this.trangthai.equals("Chờ xác nhận")) {
			return true;
		}
		return false;
	}
	
	public String getFormatday() throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		Date date = fm.parse(ngay);
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
}
