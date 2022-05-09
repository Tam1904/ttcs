package com.example.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		Map<String, String> map = new HashMap<>();
		map.put("1", "Giày thể thao nam");
		map.put("2", "Giày thể thao nữ");
		map.put("3", "Giày thể Adidas");
		map.put("4", "Giày bóng rổ");
		map.put("5", "Quần áo bóng đá");
		map.put("6", "Giày thể thao trẻ em");
		typed = map.get(typed);
		List<Product> products = pDao.getByTyped12(typed, Integer.parseInt(index), "");
		model.addAttribute("products", products);
		session.setAttribute("type", typed);
		session.removeAttribute("text");
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu(typed, 0, 5000000, "");
		model.addAttribute("map", mapTyped);
		model.addAttribute("money", "Từ 0-5.000.000 đ");
		List<Product> top = pDao.getProductTop(typed, "");
		session.setAttribute("top", top);
		return "typed";
	}

	@PostMapping("/filterMoney")
	public String filterMoney(@RequestParam("moneyFind") String money, @SessionAttribute("type") String typed,
			Model model, HttpSession session) {
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
			model.addAttribute("money", "Dưới 500.000 đ");
		} else if (money.equals("Từ 500.000 đ đến 1.000.000 đ")) {
			min = 500000;
			max = 1000000;
			model.addAttribute("money", "Từ 500.000 đ đến 1.000.000 đ");
		} else if (money.equals("Từ 1.000.000 đ đến 2.000.000 đ")) {
			min = 1000000;
			max = 2000000;
			model.addAttribute("money", "Từ 1.000.000 đ đến 2.000.000 đ");
		} else if (money.equals("Trên 2.000.000 đ")) {
			min = 2000000;
			max = 5000000;
			model.addAttribute("money", "Trên 2.000.000 đ");
		} else {
			min = 0;
			max = 5000000;
			model.addAttribute("money", "Từ 0-5.000.000 đ");
		}
		List<Product> products = pDao.getByMoney(typed, min, max, text);
		model.addAttribute("products", products);
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu(typed, min, max, text);
		model.addAttribute("map", mapTyped);
		return "typed";
	}

	@PostMapping("/filterTrandeMark")
	public String filterTrademark(HttpSession session, @RequestParam("money") String money,
			@RequestParam("thuonghieu") String thuonghieu, @SessionAttribute("type") String typed, Model model) {
		int min, max;
		String text = (String) session.getAttribute("text");
		if (text == null || text.isEmpty()) {
			text = "";
		}
		if(text.equals(typed)) {
			typed = "";
		}
		if (money.equals("Dưới 500.000 đ")) {
			min = 0;
			max = 500000;
			model.addAttribute("money", "Dưới 500.000 đ");
		} else if (money.equals("Từ 500.000 đ đến 1.000.000 đ")) {
			min = 500000;
			max = 1000000;
			model.addAttribute("money", "Từ 500.000 đ đến 1.000.000 đ");
		} else if (money.equals("Từ 1.000.000 đ đến 2.000.000 đ")) {
			min = 1000000;
			max = 2000000;
			model.addAttribute("money", "Từ 1.000.000 đ đến 2.000.000 đ");
		} else if (money.equals("Trên 2.000.000 đ")) {
			min = 2000000;
			max = 5000000;
			model.addAttribute("money", "Trên 2.000.000 đ");
		} else {
			min = 0;
			max = 5000000;
			model.addAttribute("money", "Từ 0-5.000.000 đ");
		}
		if (!thuonghieu.contains(",")) {
			return "typed";
		}
		thuonghieu = thuonghieu.replace("null,", "");
		List<Product> products = pDao.getByMoney(typed, min, max, text);
		List<Product> productsOne = new ArrayList<>();
		for (Product product : products) {
			if (product.getThuonghieu().equals(thuonghieu)) {
				productsOne.add(product);
			}
		}
		model.addAttribute("products", productsOne);
		List<Map.Entry<String, Integer>> mapTyped = pDao.getThuongHieu(typed, min, max, text);
		model.addAttribute("map", mapTyped);
		model.addAttribute("choice", thuonghieu);
		return "typed";
	}
}
