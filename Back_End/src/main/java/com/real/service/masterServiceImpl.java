package com.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.dao.masterDao;

@Service
public class masterServiceImpl implements masterService {
	
	@Autowired
	private masterDao masterdao;

}
