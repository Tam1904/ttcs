package com.example.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Product;

@Controller
@RequestMapping("/typeProduct")
public class typedController {

	@Autowired
	private ProductDao pDao;

	@GetMapping
	public String index(HttpSession session, @ModelAttribute("typed") String typed,
			@ModelAttribute("index") String index, Model model) {
		session.removeAttribute("choice");
		session.setAttribute("filter", typed);
		Map<String, String> map = new HashMap<>();
		map.put("1", "Giày thể thao nam");
		map.put("2", "Giày thể thao nữ");
		map.put("3", "Giày Adidas");
		map.put("4", "Giày Bóng Rổ");
		map.put("5", "Giày Gucci");
		map.put("6", "Giày thể thao trẻ em");
		typed = map.get(typed);
		String text = (String) session.getAttribute("text");
		if(text==null || text.isEmpty()) {
			text = "";
		}
		
		List<Product> productList = (List<Product>) session.getAttribute("productsPage");
		if(productList==null) {
			productList = pDao.getByTyped12(typed, Integer.parseInt(index), text);
		}
		session.setAttribute("type", typed);
		PagedListHolder<?> pages = new PagedListHolder<>(productList);
		int pagesize = 12;
		pages.setPageSize(pagesize);
		pages.setPage(0);
		int begin = 1;
		int end = Math.min(4, pages.getPageCount());
		int count = pages.getPageCount();
		session.removeAttribute("text");
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu(typed, 0, 5000000, "");
		session.setAttribute("map", mapTyped);
		session.setAttribute("money", "Từ 0-5.000.000 đ");
		List<Product> top = pDao.getProductTop(typed, "");
		session.setAttribute("top", top);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("count", count);
		model.addAttribute("current", 1);
		model.addAttribute("products", pages.getPageList());
		session.setAttribute("productPage", pages);
		session.removeAttribute("action");
		return "typed";
	}
	
	
	@GetMapping("/page/{number}")
	public String page(HttpSession session, @ModelAttribute("typed") String typed,
			@ModelAttribute("index") String index, @PathVariable("number") int number, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) (session.getAttribute("productPage"));
		int pagesize = 12;
		pages.setPageSize(pagesize);
		if(number<1) {
			number =1;
		}
		else if(number > pages.getPageCount()) {
			number = pages.getPageCount();
		}
		pages.setPage(number-1);
		int begin = Math.max(1, number-3);
		int end = Math.min(begin+3, pages.getPageCount());
//		session.setAttribute("money", "Từ 0-5.000.000 đ");
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("current", number);
		model.addAttribute("products", pages.getPageList());
		return "typed";
	}

	@PostMapping("/filterMoney")
	public String filterMoney(@RequestParam("moneyFind") String money, @SessionAttribute("type") String typed,
			@ModelAttribute("index") String index,Model model, HttpSession session) {
		session.removeAttribute("choice");
		String text = (String) session.getAttribute("text");
		if (text == null || text.isEmpty()) {
			text = "";
		}
		if(text.equals(typed)) {
			typed = "";
		}
		int min, max;
		if (money.equals("Dưới 500.000 đ")) {
			min = 0;
			max = 500000;
			session.setAttribute("money", "Dưới 500.000 đ");
		} else if (money.equals("Từ 500.000 đ đến 1.000.000 đ")) {
			min = 500000;
			max = 1000000;
			session.setAttribute("money", "Từ 500.000 đ đến 1.000.000 đ");
		} else if (money.equals("Từ 1.000.000 đ đến 2.000.000 đ")) {
			min = 1000000;
			max = 2000000;
			session.setAttribute("money", "Từ 1.000.000 đ đến 2.000.000 đ");
		} else if (money.equals("Trên 2.000.000 đ")) {
			min = 2000000;
			max = 5000000;
			session.setAttribute("money", "Trên 2.000.000 đ");
		} else {
			min = 0;
			max = 5000000;
			session.setAttribute("money", "Từ 0-5.000.000 đ");
		}
		List<Product> productList = pDao.getByMoney(typed, min, max, text);
		PagedListHolder<?> pages = new PagedListHolder<>(productList);
		int pagesize = 12;
		pages.setPageSize(pagesize);
		pages.setPage(0);
		int begin = 1;
		int end = Math.min(pages.getPageCount(), 4);
//		session.removeAttribute("text");
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu(typed, min, max, text);
		session.setAttribute("map", mapTyped);
		List<Product> top = pDao.getProductTop(typed, "");
		session.setAttribute("top", top);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("current", 1);
		model.addAttribute("products", pages.getPageList());
		session.setAttribute("productPage", pages);
		return "typed";
	}

	@PostMapping("/filterTrandeMark")
	public String filterTrademark(HttpServletRequest request, HttpSession session, @RequestParam("money") String money, @ModelAttribute("index") String index,
			 @SessionAttribute("type") String typed, Model model) {
		String text = "";
		String thuonghieu = request.getParameter("thuonghieu");
		if(thuonghieu==null || thuonghieu.isEmpty()) {
			thuonghieu = "none";
		}
		text = (String) session.getAttribute("text");
		if (text ==null || text.isEmpty()) {
			text = "";
		}
		if(text!=null && text.equals(typed)) {
			typed = "";
		}
		int min, max;
		if (money.equals("Dưới 500.000 đ")) {
			min = 0;
			max = 500000;
			session.setAttribute("money", "Dưới 500.000 đ");
		} else if (money.equals("Từ 500.000 đ đến 1.000.000 đ")) {
			min = 500000;
			max = 1000000;
			session.setAttribute("money", "Từ 500.000 đ đến 1.000.000 đ");
		} else if (money.equals("Từ 1.000.000 đ đến 2.000.000 đ")) {
			min = 1000000;
			max = 2000000;
			session.setAttribute("money", "Từ 1.000.000 đ đến 2.000.000 đ");
		} else if (money.equals("Trên 2.000.000 đ")) {
			min = 2000000;
			max = 5000000;
			session.setAttribute("money", "Trên 2.000.000 đ");
		} else {
			min = 0;
			max = 5000000;
			session.setAttribute("money", "Từ 0-5.000.000 đ");
		}
		
		if(!thuonghieu.equals("none")) {
			session.setAttribute("choice", thuonghieu);
		}
//		if (!thuonghieu.contains(",")) {
//			return "typed";
//		}
//		thuonghieu = thuonghieu.replace("null,", "");
		List<Product> products = pDao.getByMoney(typed, min, max, text);
		List<Product> productsOne = new ArrayList<>();
		for (Product product : products) {
			if (product.getThuonghieu().equals(thuonghieu)) {
				productsOne.add(product);
			}
		}
		PagedListHolder<?> pages = new PagedListHolder<>(productsOne);
		int pagesize = 12;
		pages.setPageSize(pagesize);
		pages.setPage(0);
		int begin = 1;
		int end = Math.min(pages.getPageCount(), 4);
//		session.removeAttribute("text");
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu(typed, min, max, text);
		session.setAttribute("map", mapTyped);
		List<Product> top = pDao.getProductTop(typed, "");
		session.setAttribute("top", top);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("current", 1);
		model.addAttribute("products", pages.getPageList());
		model.addAttribute("choice", thuonghieu);
		session.setAttribute("productPage", pages);
		return "typed";
	}
}
