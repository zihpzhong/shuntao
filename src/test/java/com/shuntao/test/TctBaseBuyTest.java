package com.shuntao.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shuntao.model.TctBaseBuyer;
import com.shuntao.service.TctBaseBuyerService;




public class TctBaseBuyTest {

private TctBaseBuyerService tctBaseBuyerService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/applicationContext.xml"
				,"classpath:conf/spring-mybatis.xml"});
		tctBaseBuyerService = (TctBaseBuyerService) context.getBean("tctBaseBuyerServiceImpl");
	}
	
	@Test
	public void addUser(){
		TctBaseBuyer user = new TctBaseBuyer();
		user.setCorpName("Zz");
		user.setLegalPerson("1");
		user.setAddress("1");
		user.setShorpCardPath("1");
		user.setLinkman("1");
		user.setTel("1");
		user.setCheckPhone("1");
		user.setOperatorGuid("1");
		user.setOpTime(new Date());
		System.out.println(tctBaseBuyerService.insertUser(user));
	}
}
