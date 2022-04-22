package com.example.demo.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.UserDao;
import com.example.demo.model.Account;
import com.example.demo.model.User;

@Controller
@RequestMapping("/signin")
public class SignController {
	@Autowired
	private UserDao uDao;
	
	@GetMapping
	public String signForm(Model model) {
		model.addAttribute("account", new Account());
		return "signin";
	}
	
	@PostMapping
	public String check(HttpSession session,Model model, @ModelAttribute("account") Account ac) {
		String username = ac.getUsername();
		String password = ac.getPassword();
		String confirm = ac.getConfirm();
		if(!confirm.equals(password)) {
			model.addAttribute("account", new Account(username, password, confirm));
			model.addAttribute("messOne", "You just enter the confirm password");
			return "signin";
		}
		else {
			if(!uDao.exitsUser(username)) {
				uDao.addUser(username, password);
			}
			else {
				model.addAttribute("account", new Account(username, password));
				return "signin";
			}
		}
		User user = uDao.getUser(username, password);
		session.setAttribute("user", user);
		return "index";
	}
}
