package com.example.demo.api;

import java.text.NumberFormat;
import java.util.ArrayList;
//import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Account;
import com.example.demo.model.Comment;
import com.example.demo.model.Product;
import com.example.demo.model.User;

@Controller
@RequestMapping("/detailProduct")
public class DetailController {
	@Autowired
	private ProductDao pDao;

	@GetMapping
	public String detail(Model model, @ModelAttribute("ma") String ma) {
		Product product = pDao.getById(ma);
		model.addAttribute("product", product);
		List<Comment> comments = pDao.getComment(ma);
		model.addAttribute("comments", comments);
		List<Product> products = pDao.getFourProduct(ma);
		model.addAttribute("products", products);
		return "detail";
	}

	@PostMapping
	public String buy(HttpServletRequest request, HttpServletResponse respone, HttpSession session,
			@ModelAttribute("product") Product product, Model model) {
		String txt = "";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cart")) {
				txt = cookie.getValue();
				cookie.setMaxAge(0);
				cookie.setPath("/");
				respone.addCookie(cookie);
				if (txt.contains(product.getMa() + ":" + product.getSize())) {
					StringTokenizer tk = new StringTokenizer(txt, "|");
					while (tk.hasMoreTokens()) {
						String tmp = tk.nextToken();
						if (tmp.contains(product.getMa() + ":" + product.getSize())) {
							txt = txt.replace(tmp,
									product.getMa() + ":" + product.getSize() + ":" + product.getSoluong());
						}
					}
				} else {
					txt += product.getMa() + ":" + product.getSize() + ":" + product.getSoluong() + "|";
				}
				Cookie cooki = new Cookie("cart", txt);
				cookie.setMaxAge(7 * 24 * 60 * 60);
				respone.addCookie(cooki);
			}
		}
		if (txt == "") {
			txt = product.getMa() + ":" + product.getSize() + ":" + product.getSoluong() + "|";
			Cookie cookie = new Cookie("cart", txt);
			cookie.setMaxAge(7 * 24 * 60 * 60);
			cookie.setPath("/");
			respone.addCookie(cookie);
		}
		List<Product> productCart = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(txt, "|");
		while (token.hasMoreTokens()) {
			String tmp = token.nextToken().trim();
			StringTokenizer tk1 = new StringTokenizer(tmp, ":");
			String ma = tk1.nextToken();
			String size = tk1.nextToken();
			String soluong = tk1.nextToken();
			Product productTmp = pDao.getById(ma);
			productTmp.setSize(size);
			productTmp.setSoluong(soluong);
			productCart.add(productTmp);
		}
		session.removeAttribute("cartProduct");
		session.setAttribute("cartProduct", productCart);
		Product pro = pDao.getById(product.getMa());
		product.setTen(pro.getTen());
		product.setDongia(pro.getDongia());
		List<Product> products = new ArrayList<>();
		products.add(product);
		model.addAttribute("products", products);
		model.addAttribute("tamtinh", product.getFormat());
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		model.addAttribute("tong",
				nf.format(Integer.parseInt(product.getDongia()) * Integer.parseInt(product.getSoluong()) + 65000));
		return "pay";
	}

	@GetMapping("/pay")
	public String pay(HttpServletRequest request, HttpServletResponse respone, HttpSession session,
			Model model) {
		String txt = "";
		User user = (User) session.getAttribute("user");
		if(user==null) {
			model.addAttribute("account", new Account());
			return "redirect:/login";
		}
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cart")) {
				txt = cookie.getValue();
			}
		}
		List<Product> productCart = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(txt, "|");
		int tamtinh=0;
		while (token.hasMoreTokens()) {
			String tmp = token.nextToken().trim();
			StringTokenizer tk1= new StringTokenizer(tmp,":");
			String ma = tk1.nextToken();
			String size = tk1.nextToken();
			String soluong = tk1.nextToken();
			Product productTmp = pDao.getById(ma);
			productTmp.setSize(size);
			productTmp.setSoluong(soluong);
			productCart.add(productTmp);
			tamtinh+= Integer.parseInt(productTmp.getDongia())*Integer.parseInt(productTmp.getSoluong());
		}
		session.removeAttribute("cartProduct");
		session.setAttribute("cartProduct", productCart);
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat nf = NumberFormat.getInstance(localeVN);
		model.addAttribute("tamtinh", nf.format(tamtinh));
		model.addAttribute("tong", nf.format(tamtinh+65000));
		return "pay";
	}

}
