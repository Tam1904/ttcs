package com.example.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.dao.BillDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Comment;
import com.example.demo.model.Product;
import com.example.demo.model.User;

//import com.example.demo.model.Comment;
//import com.example.demo.model.Product;
//import com.example.demo.model.Product;

@RestController
public class testController {

	@Autowired
	private ProductDao pDao;
	
	@Autowired
	private BillDao bDao;

	@GetMapping("/test")
	public String filterMoney(@SessionAttribute("type") String typed,
			Model model, HttpSession session) {
		String text = (String) session.getAttribute("text");
		if (text == null || text.isEmpty()) {
			text = "";
		}
//		if(text.equals(typed)) {
//			typed = "";
//		}
		return text +"__" +typed;
	}

//	@GetMapping("/test1")
//	public String index1() {
//		List<Map.Entry<String, Integer>> map = pDao.getThuongHieu("Giày thể thao nam", 0, 500000);
//		return map.toString();
//	}
//
//	@GetMapping("/test2")
//	public String dathang(HttpSession session ,HttpServletResponse response, HttpServletRequest request, Model model) {
//		Cookie [] cookies = request.getCookies();
//		for(Cookie cookie : cookies) {
//			if(cookie.getName().equals("cart")) {
//				return cookie.getValue();
//			}
//		}
//		return "";
//	}

//	@GetMapping("/test3")
//	public String update() {
//		String remove= "SP01:38",  update="SP19:27:3|SP24:27:4|";
//		String cart = "SP01:38:1|SP19:27:1|SP24:27:1|SP25:35:1";
////		String cart2 = "";
//		List<String> list = new ArrayList<>();
//		StringTokenizer tk1 = new StringTokenizer(remove,"|");
//		tk1 = new StringTokenizer(update,"|");
//		while(tk1.hasMoreTokens()) {
//			String tmp = tk1.nextToken();
//			list.add(tmp);
//		}
//		
//		List<String> list2 = new ArrayList<>();
//		tk1 = new StringTokenizer(cart,"|");
//		while(tk1.hasMoreTokens()) {
//			String tmp = tk1.nextToken();
//			list2.add(tmp);
//		}
//		for(String w: list) {
//			for(int i=0;i<list2.size();i++) {
//				StringTokenizer tk3 = new StringTokenizer(w, ":");
//				String w2 = tk3.nextToken() + ":" + tk3.nextToken();
//				if(list2.get(i).contains(w2)) {
//					list2.set(i, w);
//				}
//			}
//		}
////		for(String w: list2) {
////			cart2 += w + "|";
////		}
//		
//		List<Product> products = pDao.getProductTop("");
//		return products.toString();
//	}
}
