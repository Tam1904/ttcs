package com.example.demo.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*import org.springframework.web.bind.annotation.RequestParam;*/

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Product;

@Controller
@RequestMapping("/managerProduct")
public class ProductController {
	
	@Autowired
	private ProductDao pDao;

	@GetMapping
	public String index(Model model,HttpSession session) {
		model.addAttribute("product", new Product());
		List<Product> products = pDao.search("");
		model.addAttribute("products", products);
		session.setAttribute("action", 5);
		return "managerProduct";
	}
	
	@GetMapping("/checkProduct")
	public ResponseEntity<String> find(@ModelAttribute("ma")String ma){
		String s = "";
		if(pDao.exitsProduct(ma)) {
			s = "Sản phẩm đã tồn tại";
		}
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<String> add(@ModelAttribute("ma")String ma, @ModelAttribute("ten")String ten,
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
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProduct")
	public ResponseEntity<String> delete(@ModelAttribute("listma")String listma) {
		String [] list = listma.trim().split("\\s++");
		for(int i=0;i<list.length;i++) {
			pDao.removeProduct(list[i]);
		}
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@GetMapping("/autosearchProduct")
	public ResponseEntity<String> findList(@ModelAttribute("text")String text, @ModelAttribute("typed") String typed){
		String s = "";
		List<Product> products = pDao.search(text);
		for(Product p : products) {
			s+="<tr class='cart-table-tr'>"
					+ "<td class='cart-table-td '>";
			if(typed.equals("Quay lại")) {
				s+="<div class='cart-icon '>"
						+ "<i id='icon' class='fas fa-highlighter edit' onclick='chooseProduct(this)'></i>"
						+ "<input type='checkbox' class='checkbox ' value= " + p.getMa()+ ">"
						+ "</div>";
			}
			else {
				s+="<div class='cart-icon '>"
						+ "<i id='icon' class='fas fa-highlighter edit' onclick='chooseProduct(this)' style ='display:none'></i>"
						+ "<input type='checkbox' class='checkbox ' value= " + p.getMa()+ " style='display:block' >"
						+ "</div>";
			}
					
					s += "												<span id='ma'  style='display: none;'> " + p.getMa()  + " </span>"
					+ "												<span id='mota'  style='display: none;'>" + p.getMota() + "</span>"
					+ "												<span id='typed'  style='display: none;'>"  + p.getTyped() + "</span>"
					+ "												<span id='thuonghieu' style='display: none;'> " + p.getThuonghieu() + " </span>"
					+ "												<span id='anh'  style='display: none;'> " + p.getAnh()  + "</span>"
					+ "											</td>"
					+ "											<td class='cart-table-td-img '>"
					+ "												<div class='cart-product-img '>"
					+ "													<img src= " + p.getToken()  + "  style='width: 60px; '>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td>"
					+ "												<div class='cart-product '>"
					+ "													<div class='cart-product-link '>"
					+ "														<span class='cart-product-link-name '>Sản phẩm:</span>"
					+ "														<a href= '/detailProduct?ma=" + p.getMa()   + "' id='ten' href=' ' class='cart-product-name ' >" + p.getTen()  +"</a>"
					+ "													</div>"
					+ "													<div class='cart-product-size '>"
					+ "														<span class='cart-product-size-1 '>Size: </span>"
					+ "														<span id='size' class='cart-product-size-2 '> " + p.getSize()  +" </span>"
					+ "													</div>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td>"
					+ "												<div class='cart-product-price-nth '>"
					+ "													<span class='cart-product-price-nth-link '>Giá:</span>"
					+ "													<div class='cart-product-price '>"
					+ "														<span id='dongia' > " + p.getDongia()  + "</span>"
					+ "														<span style='text-decoration: underline; '>đ</span>"
					+ "													</div>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td>"
					+ "												<div class='cart-product-price-nth '>"
					+ "													<span class='cart-product-price-nth-link '>Số lượng:</span>"
					+ "													<div id='soluong' class='cart-product-price '>" + p.getSoluong() + "</div>" 
					+ "												</div>"
					+ "											</td>"
					+ "										</tr>";
			
		}
		
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	
}
