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
	public String check(HttpSession session, Model model, @ModelAttribute("account") Account ac) {
		String username = ac.getUsername().trim();
		String password = ac.getPassword().trim();
		String confirm = ac.getConfirm();
		if (uDao.exitsUser(username)) {
			model.addAttribute("exits", "Người dùng đã tồn tại");
			return "signin";
		} else {
			if (!confirm.equals(password)) {
				model.addAttribute("account", new Account(username, password, confirm));
				model.addAttribute("messOne", "Mật khẩu xác nhận chưa chính xác");
				return "signin";
			} else {
				model.addAttribute("account", new Account(username, password));
				uDao.addUser(username, password);
			}
		}
		User user = uDao.getUser(username, password);
		session.setAttribute("user", user);
		session.setMaxInactiveInterval(-1);
		return "redirect:/";
	}
}
