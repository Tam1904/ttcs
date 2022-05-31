package com.example.demo.api;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.BillDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Product;
import com.example.demo.model.User;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private ProductDao pDao;
	
	@Autowired
	private BillDao bDao;
	
	@Autowired
	private UserDao uDao;

	@GetMapping
	public String index(HttpSession session, HttpServletResponse response, HttpServletRequest request, Model model) {
		Cookie[] cookies = request.getCookies();
		String cart = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cart")) {
				cart = cookie.getValue();
			}
		}
		List<Product> productCart = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(cart, "|");
		int tamtinh = 0;
		while (token.hasMoreTokens()) {
			String tmp = token.nextToken().trim();
			StringTokenizer tk2 = new StringTokenizer(tmp, ":");
			String ma = tk2.nextToken();
			String size = tk2.nextToken();
			String soluong = tk2.nextToken();
			Product productTmp = pDao.getById(ma);
			productTmp.setSize(size);
			productTmp.setSoluong(soluong);
			productCart.add(productTmp);
			tamtinh += Integer.parseInt(productTmp.getDongia()) * Integer.parseInt(productTmp.getSoluong());
		}
		session.setAttribute("cartProduct", productCart);
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		session.setAttribute("tamtinh", tamtinh);
		model.addAttribute("tamtinh", nf.format(tamtinh));
		session.removeAttribute("action");
		return "cart";
	}

	@PostMapping("/update")
	public String update(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@RequestParam("remove") String remove, @RequestParam("update") String update, Model model) {
		Cookie[] cookies = request.getCookies();
		String cart = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cart")) {
				cart = cookie.getValue();
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		StringTokenizer tk1 = new StringTokenizer(remove, "|");
		while (tk1.hasMoreTokens()) {
			String w = tk1.nextToken();
			StringTokenizer tk = new StringTokenizer(cart, "|");
			while (tk.hasMoreTokens()) {
				String tmp = tk.nextToken();
				if (tmp.contains(w)) {
					cart = cart.replaceAll(tmp, "");
				}
			}
		}

		List<String> list = new ArrayList<>();
		tk1 = new StringTokenizer(update, "|");
		while (tk1.hasMoreTokens()) {
			String tmp = tk1.nextToken();
			list.add(tmp);
		}

		List<String> list2 = new ArrayList<>();
		tk1 = new StringTokenizer(cart, "|");
		while (tk1.hasMoreTokens()) {
			String tmp = tk1.nextToken();
			list2.add(tmp);
		}
		for (String w : list) {
			for (int i = 0; i < list2.size(); i++) {
				StringTokenizer tk3 = new StringTokenizer(w, ":");
				String w2 = tk3.nextToken() + ":" + tk3.nextToken();
				if (list2.get(i).contains(w2)) {
					list2.set(i, w);
				}
			}
		}
		cart = "";
		for (String w : list2) {
			cart += w + "|";
		}

		Cookie cookie = new Cookie("cart", cart);
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60);
		response.addCookie(cookie);
		List<Product> productCart = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(cart, "|");
		int tamtinh = 0;
		while (token.hasMoreTokens()) {
			String tmp = token.nextToken().trim();
			StringTokenizer tk2 = new StringTokenizer(tmp, ":");
			String ma = tk2.nextToken();
			String size = tk2.nextToken();
			String soluong = tk2.nextToken();
			Product productTmp = pDao.getById(ma);
			productTmp.setSize(size);
			productTmp.setSoluong(soluong);
			productCart.add(productTmp);
			tamtinh += Integer.parseInt(productTmp.getDongia()) * Integer.parseInt(productTmp.getSoluong());
		}
		session.setAttribute("cartProduct", productCart);
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		session.setAttribute("tamtinh", tamtinh);
		model.addAttribute("tamtinh", nf.format(tamtinh));
		return "cart";
	}

	@PostMapping("/dathang")
	public String dathang(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@RequestParam("xa") String xa, @RequestParam("huyen") String huyen, @RequestParam("dia") String dia,
			@RequestParam("tinh") String tinh,@RequestParam("ghichu") String ghichu, Model model) {
		List<Product> products = (ArrayList<Product>) session.getAttribute("cartProduct");
		User user = (User) session.getAttribute("user");
		user.setDiachi(dia.trim() + ", " + xa.trim() + ", " + huyen.trim() + ", " + tinh.trim() );
		uDao.update(user);
		session.setAttribute("user", user);
		bDao.saveBill(user, products,ghichu);
		Cookie [] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("cart")) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				session.removeAttribute("cartProduct");
			}
		}
		return "redirect:/";
	}
}
