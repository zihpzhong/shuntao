package com.shuntao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ng/directive")
public class DirectiveController {
	
	@RequestMapping("dr")
	public String index() {
		return "/ng/directive/dr";
		
	}
}
