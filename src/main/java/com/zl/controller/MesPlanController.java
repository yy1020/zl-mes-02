package com.zl.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.beans.PageQuery;
import com.zl.beans.PageResult;
import com.zl.common.JsonData;
import com.zl.model.MesPlan;
import com.zl.param.MesPlanVo;
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
	public String planStartedPage() {
		return FPATH+"planStarted";
	}
	
	//分页显示
    @RequestMapping("/plan.json")
    @ResponseBody
    public JsonData searchPage(SearchPlanParam param, PageQuery page) {
    	PageResult<MesPlan> pr=(PageResult<MesPlan>) planService.searchPageList(param, page);
    	return JsonData.success(pr);
    }
    
    //批量启动处理
    @ResponseBody
    @RequestMapping("/planBatchStart.json")
    public JsonData planBatchStart(String ids) {
    	planService.batchStartWithIds(ids);
    	return JsonData.success();
    }
    
    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData updatePlan(MesPlanVo mesPlanVo) {
    	planService.update(mesPlanVo);
    	return JsonData.success();
    }
}
