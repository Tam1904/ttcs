package com.example.demo.model;

import java.util.Date;


import lombok.Data;

@Data
public class Comment {
	private String masanpham;
	private String ten ;
	private String noidung;
	private Date thoigian;
	
	public String getName() {
		return "Tâm Nguyễn";
	}

	public Comment(String masanpham, String ten, String noidung, Date thoigian) {
		super();
		this.masanpham = masanpham;
		this.ten = ten;
		this.noidung = noidung;
		this.thoigian = thoigian;
	}
	
	
}
