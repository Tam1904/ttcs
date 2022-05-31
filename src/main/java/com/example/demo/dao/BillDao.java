package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.BillTemp;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.UserStaff;

public interface BillDao {
	public void saveBill(User user, List<Product> products,String ghichu);
	public List<String> getDate(User user);
	public List<Product> getProducts(String makhachhang,String ngay,String trangthai);
	public void delete(String makhachhang,String masanpham,String size,String ngay);
	public List<BillTemp> getBillStaff();
	public List<String> getNote(String ma, String ngay);
	void huydon(String ma, String ngay, String lydo,String trangthai,String manhanvien);
	void xacnhan(String ma, String ngay,String manhanvien);
	void xacnhan(String ma, int soluong );
	void insertComment(int makhachhang,String masanpham);
	List<UserStaff> getListUserStaff();
}
