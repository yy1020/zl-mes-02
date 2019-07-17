package com.zl.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.service.ProductService;
import com.zl.beans.PageQuery;
import com.zl.beans.PageResult;
import com.zl.common.JsonData;
import com.zl.dto.ProductDto;
import com.zl.param.SearchProductParam;
import com.zl.common.SameUrlData;
import com.zl.param.MesProductVo;

@Controller
@RequestMapping("/product")
public class MesProductController {
	private static String FPATH="product";
	
	@Resource
	private ProductService productService;
	
	//增加材料页面
	@RequestMapping("/productinsert.page")
	public String productInsertPage() {
		return FPATH+"/productinsert";
	}

	//增加材料功能
	@RequestMapping("/insert.json")
	@SameUrlData//防止重复提交
	public String insert(MesProductVo productVo) {
		productService.insert(productVo);
		return FPATH+"/product";
	}
	
	//材料管理页面
	@RequestMapping("/product.page")
	public String productList() {
		return FPATH+"/product";
	}
		
	//材料到库管理分页页面
	@RequestMapping("/product.json")
	@ResponseBody
	public JsonData searchPage(SearchProductParam param, PageQuery page) {
	   	PageResult<ProductDto> pr=(PageResult<ProductDto>) productService.searchPageList(param, page);
	    	return JsonData.success(pr);
	}
	
	//更新材料功能
	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData update(MesProductVo productVo) {
		productService.update(productVo);
		return JsonData.success(true);
	}
		
	//材料到库管理
	@RequestMapping("/productCome.page")
	public String productComePage() {
		return FPATH+"/productCome";
	}
	
	//材料批量到库
	@RequestMapping("/productBatchStart.json")
	public String productBatchStart(String ids) {
		productService.batchStart(ids);
		return FPATH+"/product";
   }
	
	//钢锭查询
	@RequestMapping("/productIron.page")
	public String productIconList() {
		return FPATH+"/productIron";
	}
	
	
}
