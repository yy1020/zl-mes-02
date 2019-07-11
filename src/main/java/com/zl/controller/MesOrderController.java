package com.zl.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.zl.common.JsonData;
import com.zl.model.MesOrder;
import com.zl.param.MesOrderVo;
import com.zl.param.SearchOrderParam;
import com.zl.service.OrderService;

@Controller
@RequestMapping("/order")
public class MesOrderController {
	private static String FPATH="order/";
	
	@Resource
	private OrderService orderService;

	@RequestMapping("/order.page")
	public String orderPage() {
		return FPATH+"order";
	}
	
	//批量处理转发
	@RequestMapping("/orderBatch.page")
	public String orderBatchPage() {
		return FPATH+"orderBatch";
	}
	
	//批量启动处理
//	@ResponseBody
//	@RequestMapping("/orderBatchStart.json")
//	public JsonData orderBatchStart(String ids) {
//		orderService.batchStart(ids);
//		return JsonData.success();
//	}
	
   /* @RequestMapping("/order.json")
    @ResponseBody
    public JsonData searchPage(SearchOrderParam param, PageQuery page) {
    	PageResult<MesOrder> pr=(PageResult<MesOrder>) orderService.searchPageList(param, page);
    	return JsonData.success(pr);
    }*/
	
	//添加接收json数据的注解
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		orderService.orderBatchInserts(mesOrderVo);//batch 批量
		return JsonData.success();
	}
	
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateOrder(MesOrderVo mesOrderVo) {
    	orderService.update(mesOrderVo);
    	return JsonData.success();
    }
	
}
