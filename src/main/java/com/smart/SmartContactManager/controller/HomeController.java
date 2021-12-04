package com.smart.SmartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.SmartContactManager.entity.User;
import com.smart.SmartContactManager.repo.UserRepo;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping("/home")
	 public String home(Model model) {		 
		 model.addAttribute("title", "Home");
		 return "home";	 
	 }
	
	@RequestMapping("/about")
	 public String about(Model model) {
		 model.addAttribute("title", "About");
		 return "about";
	 }
	
	@RequestMapping("/signup")
	public String signUp(Model model) {
		
		model.addAttribute("title", "Sign Up");
		model.addAttribute("user", new User());
		return "SignUp";	
	}
	
	@PostMapping("/doRegister")
	public String saveRegisterUser(@ModelAttribute("user") User user,Model model,@RequestParam(value = "agree",defaultValue = "false") boolean agree) {
		if(!agree) {
			model.addAttribute("user", user);
			model.addAttribute("error", "Something went wrong!!!!!!!");
			model.addAttribute("title", "Sign Up");
			return "SignUp";
		} else {
			user.setRole("USER");
			user.setEnabled(true);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userrepo.save(user);
			model.addAttribute("success", "Your Record saved successfully........");
			model.addAttribute("user", new User());
			model.addAttribute("title", "Sign Up");
			return "SignUp";
		}
		
	}
	
	@RequestMapping("/signin")
	public String login(Model model) {
		model.addAttribute("title", "Login");
		return "Login";
	}

}
