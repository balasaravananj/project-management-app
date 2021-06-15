package com.jrp.pma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jrp.pma.dao.SecurityRepository;
import com.jrp.pma.entity.UserAccount;

@Controller
public class SecurityController {
	
	@Autowired
	SecurityRepository secRepo;

	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@GetMapping("/register")
	public String register(Model model) {
		UserAccount userAccount = new UserAccount();
		
		model.addAttribute("userAccount", userAccount);
		
		return "security/register";
	}
	
	@PostMapping("/register/save")
	public String saveUser(UserAccount userAccount,Model model) {
		userAccount.setPassword(bCryptEncoder.encode(userAccount.getPassword()));
		secRepo.save(userAccount);
		return "redirect:/";
	}
}
