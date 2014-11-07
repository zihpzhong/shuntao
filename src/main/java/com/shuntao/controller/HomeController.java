package com.shuntao.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuntao.model.TctBaseBuyer;
import com.shuntao.service.TctBaseBuyerService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private TctBaseBuyerService tctBaseBuyerService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}

	@RequestMapping(value="/getsave",method=RequestMethod.GET)
	public String getsave () {
		return "veiws/save";
	}
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(Locale locale, Model model) {
		try {
			TctBaseBuyer po =new TctBaseBuyer();
			///!!!!!!!!!!!!!!!!!!!!!!111
			po.setCorpName("草鸡");
			po.setAddress("草鸡");
			po.setCheckPhone("草鸡");
			po.setLegalPerson("草鸡");
			po.setLinkman("草鸡");
			po.setOperatorGuid("草鸡");
			po.setOpTime(new Date());
			po.setShorpCardPath("1");
			po.setTel("1");
			tctBaseBuyerService.insertUser(po);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESE";
		
	}
}
