package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.model.User;

@RestController
public class testController {
	
	@GetMapping("/test")
	public String test(@SessionAttribute("user") User user) {
		return user.toString();
	}

}
