package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BillTemp;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.UserStaff;

@Repository
public class BillDaoAPI implements BillDao{
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public void saveBill(User user, List<Product> products,String ghichu) {
		String sql = "insert into hoadon(makhachhang,ngay,masanpham,size,soluong,trangthai,ghichu) values(CAST(? AS UNSIGNED),now(),?,CAST(? AS UNSIGNED),CAST(? AS UNSIGNED),?,?)";
		for(Product product : products) {
			jdbc.update(sql,user.getMa(),product.getMa(),product.getSize(),product.getSoluong(),"Chờ xác nhận",ghichu);
		}
	}

	@Override
	public List<String> getDate(User user) {
		String sql = "select ngay,sum(hoadon.soluong),sum(hoadon.soluong*sanpham.dongia),hoadon.trangthai,lydo from hoadon inner join sanpham on hoadon.masanpham = sanpham.ma where makhachhang = ? group by ngay,trangthai;";
		List<String> rs = jdbc.query(sql, this::mapRowToDate,user.getMa());
		return rs;
	}
	private String mapRowToDate(ResultSet rs, int rowNum) throws SQLException {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		Date date = rs.getDate(1);
		int soluong = rs.getInt(2);
		int tong = rs.getInt(3);
		String trangthai = rs.getString(4);
		String lydo = rs.getString(5);
		if(lydo==null || lydo.isEmpty()) {
			lydo = " ";
		}
		return fm.format(date) + ":" + soluong + ":" + tong + ":" + trangthai + ":" + lydo;
	}

	@Override
	public List<Product> getProducts(String makhachhang, String ngay,String trangthai) {
		String sql = "select sanpham.ma,sanpham.ten,sanpham.mota,hoadon.soluong,sanpham.dongia,sanpham.typed,hoadon.size,sanpham.anh\r\n"
				+ "from sanpham  inner join hoadon on sanpham.ma = hoadon.masanpham where hoadon.makhachhang = ? and ngay = ? and trangthai = ? ";
		List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class),makhachhang,ngay,trangthai);
		return products;
	}

	@Override
	public void delete(String makhachhang, String masanpham, String size, String ngay) {
		String sql = "delete from hoadon where makhachhang = ? and masanpham = ? and ngay = ?";
		jdbc.update(sql,makhachhang,masanpham,ngay);
	}

	@Override
	public List<BillTemp> getBillStaff() {
		String sql = "select makhachhang,nd.ten as ten, ngay,nd.diachi as diachi ,nd.sodienthoai,sum(sp.dongia*hd.soluong) as tongtien, hd.trangthai, hd.lydo from hoadon hd,nguoidung nd, sanpham sp "
				+ "where nd.ma = hd.makhachhang and sp.ma = hd.masanpham group by hd.makhachhang,hd.ngay,trangthai";
		return jdbc.query(sql, new BeanPropertyRowMapper<BillTemp>(BillTemp.class));
	}

	@Override
	public List<String> getNote(String ma, String ngay) {
		String sql = "select ghichu from hoadon where makhachhang = ? and ngay = ?";
		List<String> notes = jdbc.query(sql, this::mapRowToBill,ma,ngay);
		return notes ;
	}
	
	private String mapRowToBill(ResultSet rs, int rowNum) throws SQLException {
		return rs.getString(1);
	}

	@Override
	public void huydon(String ma, String ngay, String lydo,String trangthai,String manhanvien) {
		String sql = "update hoadon set lydo =  ?, trangthai = 'Đã hủy', manhanvien = ? where makhachhang = ? and ngay = ? and trangthai = 'Chờ xác nhận'";
		jdbc.update(sql,lydo,manhanvien,ma,ngay);
	}

	@Override
	public void xacnhan(String ma, String ngay,String manhanvien) {
		String sql = "update hoadon set trangthai = 'Đã giao', lydo = '' , manhanvien = ? ,ngayxacnhan= now() where makhachhang = ? and ngay = ? and trangthai = 'Chờ xác nhận'" ;
		jdbc.update(sql,manhanvien,ma,ngay);
	}
	
	public void xacnhan(String ma, int soluong) {
		String sql = "update sanpham set soluong = soluong - ?, lanmua = lanmua + ?  where ma = ?" ;
		jdbc.update(sql,soluong,soluong,ma);
	}

	@Override
	public void insertComment(int makhachhang, String masanpham) {
		String sql = "insert into binhluan(makhachhang,masanpham) value(?,?) ";
		jdbc.update(sql,makhachhang,masanpham);
	}

	@Override
	public List<UserStaff> getListUserStaff() {
		String sql = "select nd1.ma as makhachhang, nd1.ten as tenkhachhang, nd1.sodienthoai as sodienthoaiKH, \r\n"
				+ "nd2.ma as manhanvien, nd2.ten as tennhanvien, nd2.sodienthoai as sodienthoaiNV, sum(sp.dongia*hd.soluong) as tongtien , trangthai, ngay, ngayxacnhan, lydo\r\n"
				+ "from hoadon hd, nguoidung nd1, nguoidung nd2 , sanpham sp\r\n"
				+ "where sp.ma = hd.masanpham and hd.makhachhang = nd1.ma  and hd.manhanvien = nd2.ma and (trangthai = 'Đã giao' or trangthai='Đã hủy')\r\n"
				+ "group by ngay,trangthai";
		return jdbc.query(sql, new BeanPropertyRowMapper<UserStaff>(UserStaff.class));
	}
	

}
