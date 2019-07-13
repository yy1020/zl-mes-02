package com.zl.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zl.model.TestDome;
import com.zl.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	private static String PATH="test/";
	@Resource
	private TestService testService;
	
	@RequestMapping("index.page")
	public String test() {
		TestDome test = new TestDome();
		test.setId(1);
		test.setName("A01");
		testService.persist(test);
		return "test";
	}
	@RequestMapping("test.page")
	public String test1() {
		
		return PATH+"test";
	}
	
}
