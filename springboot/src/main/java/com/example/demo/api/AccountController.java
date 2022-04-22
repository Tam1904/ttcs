package com.example.demo.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserDao uDao;

	@GetMapping
	public String accountForm() {
		return "myaccount";
	}
	
	@PostMapping
	public String update(HttpSession session, @ModelAttribute("user") User user) {
		uDao.update(user);
		session.setAttribute("user", user);
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, @SessionAttribute("user")User user) {
		session.removeAttribute("user");
		return "index";
	}
}
