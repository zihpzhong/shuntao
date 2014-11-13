package com.shuntao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ng")
public class NgControllerAction {
	
	@RequestMapping("ct")
	public String ctController(Model model) {
		System.out.println("-----------1----------");
		System.out.println("------------");
		return "/ng/controller";
	}
	@RequestMapping("/sv")
	public String svController(Model model) {
		System.out.println("---------2------------");
		System.out.println("------------");
		return "/ng/service/service";
	}
	
	
	@RequestMapping(value="/getsv",method = RequestMethod.GET)
	public String getSvController(Model model) {
		System.out.println("----------3-----------");
		System.out.println("------------");
		return "/ng/service/service";
	}
	@RequestMapping(value="/xhr",method = RequestMethod.GET)
	public  String gxhrController(Model model) {
		System.out.println("----------getxhr-----------");
		System.out.println("------------");
		return "/ng/xhr/xhr";
	}
	@RequestMapping(value="/xhr",method = RequestMethod.POST)
	public @ResponseBody String xhrController(Model model) {
		System.out.println("----------postxhr-----------");
		System.out.println("------------");
		return "1221";
	}

}
