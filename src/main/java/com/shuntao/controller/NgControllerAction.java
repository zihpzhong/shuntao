package com.shuntao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ng")
public class NgControllerAction {
	
	@RequestMapping("ct")
	public String controller(Model model) {
		System.out.println("---------------------");
		System.out.println("------------");
		return "/ng/controller";
		
	
	}
}
