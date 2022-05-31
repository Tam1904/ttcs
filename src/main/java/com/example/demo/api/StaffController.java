package com.example.demo.api;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.dao.BillDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Account;
import com.example.demo.model.BillTemp;
import com.example.demo.model.Product;
import com.example.demo.model.User;

@Controller
@RequestMapping("/staff")
public class StaffController {
	@Autowired
	private UserDao uDao;
	@Autowired
	private BillDao bDao;

	@GetMapping
	public String index(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			if (user == null) {
				model.addAttribute("account", new Account());
				return "redirect:/login";
			}
		}
		List<BillTemp> bills = bDao.getBillStaff();
		model.addAttribute("bills", bills);
		session.setAttribute("action", 3);
		return "viewOrderStaff";
	}

	@PostMapping("/detail")
	public String detail(@ModelAttribute("ma") String ma, @ModelAttribute("ngay") String ngay,
			@ModelAttribute("trangthai") String trangthai, Model model) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		Date date = fm.parse(ngay);
		String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
		User user = uDao.getUser(Integer.parseInt(ma));
		List<Product> products = bDao.getProducts(String.valueOf(ma), day, trangthai);
		model.addAttribute("customer", user);
		model.addAttribute("products", products);
		model.addAttribute("ngay", ngay);
		int tong = 0;
		for (Product pro : products) {
			tong += Integer.parseInt(pro.getSoluong()) * Integer.parseInt(pro.getDongia());
		}
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		model.addAttribute("tong", nf.format(tong));
		List<String> notes = bDao.getNote(ma, day);
		String s = "";
		for (String note : notes) {
			if (note != null && !note.isEmpty()) {
				s += note + ", ";
			}
		}
		if (s == "") {
			s = "Không có";
		} else {
			s = s.substring(0, s.length() - 2);
		}
		model.addAttribute("note", s);
		return "ViewOrderDetailStaff";
	}

	@PostMapping("/huydon")
	public String huydon(@ModelAttribute("makhachhang") String ma, @ModelAttribute("ngay") String ngay,
			@ModelAttribute("lydo") String lydo, @ModelAttribute("trangthai") String trangthai,
			@SessionAttribute("user") User user) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		Date date = fm.parse(ngay);
		String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
		bDao.huydon(ma, day, lydo, trangthai,String.valueOf(user.getMa()));
		return "redirect:/staff";
	}

	@PostMapping("/xacnhan")
	public String xacnhan(@ModelAttribute("makhachhang") String ma, @ModelAttribute("ngay") String ngay
			, @SessionAttribute("user") User user) {
		List<Product> products = bDao.getProducts(ma, ngay, "Chờ xác nhận");
		for(Product product : products) {
			bDao.xacnhan(product.getMa(), Integer.parseInt(product.getSoluong()));
			bDao.insertComment(Integer.parseInt(ma), product.getMa());
		}
		bDao.xacnhan(ma, ngay,String.valueOf(user.getMa()));
 		return "redirect:/staff";
	}
}
