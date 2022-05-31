package com.example.demo.api;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.BillDao;
import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.Temp;
import com.example.demo.model.User;

@Controller
@RequestMapping("/managerOrderCustomer")
public class OrderController {
	@Autowired
	private BillDao bDao;

	@GetMapping
	public String index(HttpServletRequest request, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			if (user == null) {
				model.addAttribute("account", new Account());
				return "redirect:/login";
			}
		}
		List<String> s = bDao.getDate(user);
		List<Temp> temps = new ArrayList<>();
		for (String w : s) {
			StringTokenizer tk = new StringTokenizer(w, ":");
			Temp temp = new Temp(tk.nextToken(), tk.nextToken(), tk.nextToken(), tk.nextToken(),tk.nextToken());
			temps.add(temp);
		}
		model.addAttribute("temps", temps);
		session.setAttribute("action", 2);
		return "managerOrderCustomer";
	}

	@GetMapping("/detail")
	public String detail(@ModelAttribute("ma") String ma, @ModelAttribute("ngay") String ngay, @ModelAttribute("trangthai")String trangthai, Model model)
			throws ParseException {
		List<Product> products = bDao.getProducts(ma, ngay,trangthai);
		int tong = 0;
		model.addAttribute("products", products);
		for (Product product : products) {
			tong += Integer.parseInt(product.getDongia()) * Integer.parseInt(product.getSoluong());
		}
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fm.parse(ngay);
		String day = new SimpleDateFormat("dd/MM/yyyy").format(date);
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		model.addAttribute("tong", nf.format(tong));
		model.addAttribute("ngay", day);
		if(trangthai.equals("Chờ xác nhận")) {
			trangthai = "true";
		}
		else {
			trangthai = "false";
		}
		model.addAttribute("trangthai", trangthai);
		return "manageOrderDetailCustomer";
	}

	@PostMapping("/update")
	public String update(@RequestParam("remove") String ma, @RequestParam("ma") String makhachhang,
			@RequestParam("ngay") String ngay, Model model) throws ParseException {
		StringTokenizer tk = new StringTokenizer(ma, "|");
		while (tk.hasMoreTokens()) {
			String tmp = tk.nextToken();
			StringTokenizer tk1 = new StringTokenizer(tmp, ":");
			String maSP = tk1.nextToken();
			String size = tk1.nextToken();
			SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
			Date date = fm.parse(ngay);
			String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
			bDao.delete(makhachhang, maSP, size, day);
		}
		return "redirect:/managerOrderCustomer";
	}
}
