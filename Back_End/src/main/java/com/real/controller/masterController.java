package com.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.real.service.masterService;

@Controller
@RequestMapping(value="master")
public class masterController {
	
	@Autowired
	private masterService masterservice;

}
