package com.example.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Product;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProductDao pDao;
	
	@GetMapping
	public String home(HttpSession session, HttpServletRequest request, HttpServletResponse respone,Model model) {
		String cart = "";
		Cookie [] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("cart")) {
				cart = cookie.getValue();
			}
		}
		if (cart != null) {
			List<Product> productCart = new ArrayList<>();
			StringTokenizer token = new StringTokenizer(cart, "|");
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
			}
			session.setAttribute("cartProduct", productCart);
		}
		List<String> list = pDao.getTyped();
		Map<String,List<Product>> map = new HashMap<>();
		for(String s : list) {
			List<Product> products = pDao.getByTyped(s);
			map.put(s, products);
		}
		List<Product> productsTop = pDao.getProductTop("","");
		session.setAttribute("productsTop", productsTop);
		session.setAttribute("map", map);
		return "index";
	}
	
	@PostMapping("/searchHome")
	public String searchProduct(@RequestParam("text")String text,Model model,HttpSession session) {
//		String s = "Kết quả tìm kiếm '" + text + "'";
		String s = text;
		session.setAttribute("type", s);
		List<Product> products = pDao.search(text);
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu("",0,5000000,text);
		model.addAttribute("map", mapTyped);
		model.addAttribute("products",products);
		model.addAttribute("money", "Từ 0-5.000.000 đ");
		List<Product> top = pDao.getProductTop("",text);
		session.setAttribute("top", top);
		session.setAttribute("text", text);
		return "typed";
	}
	
	
	@GetMapping("/autoSearch")
	public ResponseEntity<List<Product> > search(@ModelAttribute("text")String txt){
		List<Product> products = pDao.search(txt);
		if(products.size()<4) {
			return new ResponseEntity<>(products,HttpStatus.OK);
		}
		return new ResponseEntity<>(products.subList(0, 4),HttpStatus.OK);
	}

}
