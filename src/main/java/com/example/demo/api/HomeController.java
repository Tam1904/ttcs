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
import org.springframework.beans.support.PagedListHolder;
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
		List<Product> productCart = new ArrayList<>();
		if (!cart.equals("")) {
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
		}
		session.setAttribute("cartProduct", productCart);
		List<String> list = pDao.getTyped();
		Map<String,List<Product>> map = new HashMap<>();
		for(String s : list) {
			List<Product> products = pDao.getByTyped(s);
			map.put(s, products);
		}
		List<Product> productsTop = pDao.getProductTop("","");
		session.setAttribute("productsTop", productsTop);
		session.setAttribute("map", map);
		session.setAttribute("action", 1);
		return "index";
	}
	
	@PostMapping("/searchHome")
	public String searchProduct(@RequestParam("text")String text,Model model,HttpSession session) {
//		String s = "Kết quả tìm kiếm '" + text + "'";
//		String s = text;
//		if(text==null || text.isEmpty()) {
//			text = "";
//			s = "";
//		}
		session.setAttribute("type", "");
		
//		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu("",0,5000000,text);
//		session.setAttribute("map", mapTyped);
//		model.addAttribute("products",products);
//		model.addAttribute("money", "Từ 0-5.000.000 đ");
//		List<Product> top = pDao.getProductTop("",text);
//		session.setAttribute("top", top);
		session.setAttribute("text", text);
		
		
		List<Product> productList = pDao.search(text);
		PagedListHolder<?> pages = new PagedListHolder<>(productList);
		int pagesize = 12;
		pages.setPageSize(pagesize);
		pages.setPage(0);
		int begin = 1;
		int end = Math.min(pages.getPageCount(), 4);
		session.setAttribute("money", "Từ 0-5.000.000 đ");
//		session.removeAttribute("text");
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu("", 0, 5000000, text);
		session.setAttribute("map", mapTyped);
		List<Product> top = pDao.getProductTop("", text);
		session.setAttribute("top", top);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("current", 1);
		model.addAttribute("products", pages.getPageList());
		session.setAttribute("productPage", pages);
		
		return "typed";
	}
	
	
	@GetMapping("/autoSearch")
	public ResponseEntity<String> search(@ModelAttribute("text")String txt){
		List<Product> products = pDao.search(txt);
		if(products.size()>6) {
			products = products.subList(0, 6);
		}
		String ans = "";
		for(Product product : products) {
			
			ans += "<li class=\"header__seach-history-item\">"
					+ "									<a class=\"header__seach-history-item-link\" href='/detailProduct?ma="+ product.getMa() + "' "
					+ "										title= '" + product.getTen() + "' >"
					+ "										<img src= " + product.getToken() +  ""
					+ "											alt=\"\" class=\"header__seach-history-item-img\">"
					+ "										<div class=\"header__seach-history-item-product\">"
					+ "											<span class=\"header__seach-history-item-product-name\">" + product.getTen() + "</span>"
					+ "											<div class=\"header__seach-history-item-product-price\">"
					+ "												<span>" + product.getFormat()  + "</span>"
					+ "												<span class=\"header__seach-history-item-product-price-item\">đ</span>"
					+ "											</div>"
					+ "										</div>"
					+ "									</a>"
					+ "								</li>";
		}
		
		return new ResponseEntity<>(ans,HttpStatus.OK);
	}

}
