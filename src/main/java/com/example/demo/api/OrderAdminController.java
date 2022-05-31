package com.example.demo.api;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.BillDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.UserStaff;

@Controller
@RequestMapping("/admin")
public class OrderAdminController {
	@Autowired
	private BillDao bDao;
	
	@Autowired
	private UserDao uDao;

	@GetMapping
	public String index(Model model,HttpSession session) {
		List<UserStaff> lists = bDao.getListUserStaff();
		model.addAttribute("lists", lists);
		session.setAttribute("action", 6);
		return "managerOrderAdmin";
	}
	
	@PostMapping("/detail")
	public String deatail(Model model, HttpServletRequest request) throws ParseException {
		String makhachhang = (String) request.getParameter("makhachhang");
		User khachhang = uDao.getUser(Integer.parseInt(makhachhang));
		String manhanvien = (String) request.getParameter("manhanvien");
		User nhanvien = uDao.getUser(Integer.parseInt(manhanvien));
		String ngay = request.getParameter("ngay");
		String ngayxacnhan = request.getParameter("ngayxacnhan");
		String trangthai = request.getParameter("trangthai");
		String lydo = request.getParameter("lydo");
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fm.parse(ngay);
		model.addAttribute("khachhang", khachhang);
		model.addAttribute("nhanvien", nhanvien);
		model.addAttribute("ngay", new SimpleDateFormat("dd/MM/yyyy").format(date));
		date = fm.parse(ngayxacnhan);
		model.addAttribute("ngayxacnhan", new SimpleDateFormat("dd/MM/yyyy").format(date));
		model.addAttribute("trangthai", trangthai);
		List<Product> products = bDao.getProducts(makhachhang, ngay, trangthai);
		int tong = 0;
		for(Product product : products) {
			tong += Integer.parseInt(product.getDongia()) * Integer.parseInt(product.getSoluong());
		}
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		model.addAttribute("tongtien", nf.format(tong));
		model.addAttribute("products", products);
		if(lydo!=null && !lydo.isEmpty()) {
			model.addAttribute("lydo", lydo);
		}
		return "managerOrderDetailAdmin";
	}
}
