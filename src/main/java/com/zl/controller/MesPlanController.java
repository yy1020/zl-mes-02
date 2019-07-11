package com.zl.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.beans.PageQuery;
import com.zl.beans.PageResult;
import com.zl.common.JsonData;
import com.zl.model.MesPlan;
import com.zl.param.SearchPlanParam;
import com.zl.service.PlanService;

@Controller
@RequestMapping("/plan")
public class MesPlanController {
	private static String FPATH="plan/";
	@Resource
	private PlanService planService;
	
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPATH+"plan";
	}
	@RequestMapping("/planStarted.page")
	public String planStartPage() {
		return FPATH+"planStarted";
	}
	
	
	//分页显示
    @RequestMapping("/plan.json")
    @ResponseBody
    public JsonData searchPage(SearchPlanParam param, PageQuery page) {
    	PageResult<MesPlan> pr=(PageResult<MesPlan>) planService.searchPageList(param, page);
    	return JsonData.success(pr);
    }
}
