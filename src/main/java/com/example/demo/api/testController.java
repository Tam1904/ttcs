package com.example.demo.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Product;

//import com.example.demo.model.Comment;
//import com.example.demo.model.Product;
//import com.example.demo.model.Product;

@RestController
public class testController {

	@Autowired
	private ProductDao pDao;
//	
//	@Autowired
//	private BillDao bDao;

//	@GetMapping("/test")
//	public String filterMoney() throws ParseException {
//		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
//		Date date = fm.parse("19/04/2001");
//		return new SimpleDateFormat("yyyy-MM-dd").format(date);
//	}

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
	
	@GetMapping("/test")
	public String index(HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		return user.toString() + user.getPhanquyen();
	}
	
	@PostMapping("/addProduct")
	public String add(@ModelAttribute("ma")String ma, @ModelAttribute("ten")String ten,
			@ModelAttribute("mota")String mota, @ModelAttribute("soluong")String soluong, @ModelAttribute("dongia") String dongia,
			@ModelAttribute("thuonghieu")String thuonghieu, @ModelAttribute("typed")String typed, @ModelAttribute("anh")String anh,
			@ModelAttribute("size")String size, Model model, @ModelAttribute("action")String action) {
		Product product = new Product(ma, ten, mota, soluong, dongia, thuonghieu, typed, size, anh);
		if(action.equals("Add")) {
			if(!pDao.exitsProduct(product.getMa())) {
				pDao.addProduct(product);
			}
			else {
				model.addAttribute("product", product);
			}
		}
		else {
			pDao.updateProduct(product);
		}
		List<Product> products = pDao.search("");
		model.addAttribute("products", products);
		return product.toString();
	}
}
