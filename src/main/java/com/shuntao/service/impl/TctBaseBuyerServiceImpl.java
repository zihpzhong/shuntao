package com.shuntao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuntao.dao.TctBaseBuyerDAO;
import com.shuntao.model.TctBaseBuyer;
import com.shuntao.service.TctBaseBuyerService;

@Service
public class TctBaseBuyerServiceImpl implements TctBaseBuyerService{

	//@Autowired
	//private TctBaseBuyerDAO tctBaseBuyerDAO;
	
	@Override
	public int insertUser(TctBaseBuyer tctBaseBuyer) {
		// TODO Auto-generated method stub
		return 1;//tctBaseBuyerDAO.insertUser(tctBaseBuyer);
	}

}
