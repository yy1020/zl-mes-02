package com.zl.dao;

public interface MesOrderCustomerMapper {
	Long getOrderCount();

	/*// @Param("dto")--缁檓apper.xml鏌ヨsql鎸囧畾鍙傛暟鍚嶇О #{dto.keyword}
	int countBySearchDto(@Param("dto") SearchOrderDto dto);

	List<MesOrder> getPageListBySearchDto(@Param("dto") SearchOrderDto dto, @Param("page") PageQuery page);

	MesOrder getOrderByOid(@Param("pid") String pid);

	void batchStart(@Param("list")String[] idArray);

	MesOrder selectByOrderId(@Param("orderid")String orderid);*/
}