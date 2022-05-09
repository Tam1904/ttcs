package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class Bill {
	private int mahoadon;
	private int makhachhang;
	private int manhanvien;
	private Date ngay;
	private String masanpham;
	private int size;
	private int soluong;
	private String trangthai;
	private String lydo;
	private String ghichu;
}
