package com.example.demo.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.UserDao;
import com.example.demo.model.Account;
import com.example.demo.model.User;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserDao uDao;
	
	@GetMapping
	public String logForm(Model model) {
		model.addAttribute("account", new Account());
		return "login";
	}
	
	@PostMapping
	public String check(HttpSession session,Model model, @ModelAttribute("account") Account ac) {
//		@RequestParam("username") String username, @RequestParam("password") String password
		String username = ac.getUsername();
		String password = ac.getPassword();
		model.addAttribute("account", new Account(username, password));
		if(uDao.findUser(username, password)) {
			User user = uDao.getUser(username, password);
			session.setAttribute("user", user);
			return "index";
		}
		return "login";
	}
}
