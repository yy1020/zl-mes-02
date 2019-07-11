package com.zl.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class MesPlanController {
	private static String FPATH="plan/";
	
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPATH+"plan";
	}
}
