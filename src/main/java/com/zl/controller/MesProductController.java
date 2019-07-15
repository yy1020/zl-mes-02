package com.zl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class MesProductController {
	private static String FPATH="product";
	
	//增加材料页面
	@RequestMapping("/productinsert.page")
	public String productInsertPage() {
		return FPATH+"/productinsert";
	}
}
