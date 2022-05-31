package com.example.demo.dto;

public class ProductDTO {
	private String ma;
	private String ten;
	private String moTa;
	private int soLuong;
	private int donGia;
	private String thuongHieu;
	private String typed;
	
	
	
	public ProductDTO() {
		super();
	}
	
	public ProductDTO(String ma, String ten, String moTa, int soLuong, int donGia, String thuongHieu, String typed) {
		super();
		this.ma = ma;
		this.ten = ten;
		this.moTa = moTa;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thuongHieu = thuongHieu;
		this.typed = typed;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public int getDonGia() {
		return donGia;
	}
	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}
	public String getThuongHieu() {
		return thuongHieu;
	}
	public void setThuongHieu(String thuongHieu) {
		this.thuongHieu = thuongHieu;
	}
	public String getTyped() {
		return typed;
	}
	public void setTyped(String typed) {
		this.typed = typed;
	}
	
	
}
