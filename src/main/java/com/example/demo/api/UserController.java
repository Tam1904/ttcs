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

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

@Controller
@RequestMapping("/managerUser")
public class UserController {
	@Autowired
	private UserDao uDao;
	
	@GetMapping
	public String index(Model model,HttpSession session) {
		List<User> users = uDao.getListUser();
		model.addAttribute("users", users);
		session.setAttribute("action", 4);
		return "managerUser";
	}
	
	@GetMapping("/checkUser")
	public ResponseEntity<String> find(@ModelAttribute("username")String username){
		String s = "";
		if(uDao.exitsUserMa(username)) {
			s = "Người dùng đã tồn tại";
		}
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<String> add(@ModelAttribute("ma")String ma,@ModelAttribute("ten")String ten,
			@ModelAttribute("username")String username, @ModelAttribute("pass")String pass, @ModelAttribute("diachi") String diachi,
			@ModelAttribute("email")String email, @ModelAttribute("typed")String typed, @ModelAttribute("sodienthoai")String sodienthoai,
			@ModelAttribute("action")String action) {
		if(ma==null || ma.isEmpty() || ma.equals("")) {
			ma = "0";
		}
		User user = new User(Integer.parseInt(ma), ten, username, pass, sodienthoai, diachi, email, typed);
		if(action.equals("Add")) {
			if(!uDao.exitsUser(username)) {
				uDao.addUser(user);
			}
		}
		else {
			uDao.update(user);
		}
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> delete(@ModelAttribute("listma")String listma) {
		uDao.removeUser(listma);
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@GetMapping("/autosearchUser")
	public ResponseEntity<String> findList(@ModelAttribute("text")String text,@ModelAttribute("typed")String typed){
		String s = "";
		List<User> users = uDao.search(text);
		for(User u : users) {
			s+="<tr class=' cart-table-tr ' style=' min-height: 70px; '>"
					+ "											<td class=' cart-table-td '>";
			if(typed.equals("Quay lại")) {
				s+="<div class=' cart-icon '>"
						+ "<i class='fas fa-highlighter edit' onclick='chooseUser(this)'></i>"
						+ "<input type='checkbox' class='checkbox' value= "+ u.getMa()+ ">"
						+ "<span id='ma' style='display: none;'>" + u.getMa()+ "</span>"
						+ "<span id='email' style='display: none;'>"+ u.getEmail()+ "</span>"
						+ "<span id='typed' style='display: none;'>" + u.getTyped()+ "</span>"
						+ "</div>";
			}
			else {
				s+="<div class=' cart-icon '>"
						+ "<i class='fas fa-highlighter edit' onclick='chooseUser(this)' style='display:none'></i>"
						+ "<input type='checkbox' class='checkbox' value= "+ u.getMa()+ " style='display:block'>"
						+ "<span id='ma' style='display: none;'>" + u.getMa()+ "</span>"
						+ "<span id='email' style='display: none;'>"+ u.getEmail()+ "</span>"
						+ "<span id='typed' style='display: none;'>" + u.getTyped()+ "</span>"
						+ "</div>";
			}
					s+= "											</td>"
					+ "											<td>"
					+ "												<div class=' cart-product '>"
					+ "													<div class=' cart-product-link '>"
					+ "														<span class='cart-product-link-name '>Name:</span>"
					+ "														<span id='ten' class='cart-product-name '> " + u.getTen()+ "</span>"
					+ "													</div>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td class=' cart-table-td '>"
					+ "												<div class=' cart-product '>"
					+ "													<div class=' cart-product-link '>"
					+ "														<span class=' cart-product-link-name '>UserName:</span>"
					+ "														<span class=' cart-product-name ' id='username'>" + u.getUsername()+ "</span>"
					+ "													</div>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td>"
					+ "												<div class=' cart-product '>"
					+ "													<div class=' cart-product-link '>"
					+ "														<span class=' cart-product-link-name '>PassWord:</span>"
					+ "														<span id='pass' class=' cart-product-name '>" + u.getPass()+ "</span>"
					+ "													</div>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td>"
					+ "												<div class=' cart-product-price-nth '>"
					+ "													<span class=' cart-product-price-nth-link '>Phone:</span>"
					+ "													<div id='sodienthoai' class=' cart-product-price '> " + u.getSodienthoai()+ "</div>"
					+ "												</div>"
					+ "											</td>"
					+ "											<td>"
					+ "												<div class=' cart-product-price-nth '>"
					+ "													<span class=' cart-product-price-nth-link '>Addrress:</span>"
					+ "													<div id='diachi' class=' cart-product-price '>" + u.getDiachi()+"</div>"
					+ "												</div>"
					+ "											</td>"
					+ "										</tr>";
		}
			
		
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
}
