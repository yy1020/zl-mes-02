package com.zl.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.zl.beans.PageQuery;
import com.zl.beans.PageResult;
import com.zl.dto.ProductDto;
import com.zl.dto.SearchProductDto;
import com.zl.param.SearchProductParam;
import com.zl.dao.MesOrderMapper;
import com.zl.dao.MesProductCustomerMapper;
import com.zl.dao.MesProductMapper;
import com.zl.model.MesProduct;
import com.zl.param.MesProductVo;
import com.zl.util.BeanValidator;
import com.zl.util.UUIDUtil;

@Service
public class ProductService {

	@Resource
	private MesProductMapper mesProdcutMapper;
	@Resource
	private MesOrderMapper mesOrderMapper;
	
	@Resource
	private MesProductCustomerMapper mesProductCustomerMapper;

	@Resource
	private SqlSession sqlSession;
	
	// 批量增加材料
		public void insert(MesProductVo productVo) {
			// 校验
			BeanValidator.check(productVo);
			// 获取增加个数
			Integer counts = productVo.getCounts();
			if (counts != null && counts > 0) {
				for (int i = 0; i < counts; i++) {
					// 批量增加-productDto
					MesProduct pd = MesProduct.builder().productId(UUIDUtil.generateUUID())//
							.productTargetweight(productVo.getProductTargetweight())//
							.productRealweight(productVo.getProductRealweight())//
							.productLeftweight(productVo.getProductLeftweight())//
							.productBakweight(productVo.getProductLeftweight())//
							.productIrontype(productVo.getProductIrontype())//
							.productIrontypeweight(productVo.getProductIrontypeweight())//
							.productMaterialname(productVo.getProductMaterialname())//
							.productImgid(productVo.getProductImgid())//
							.productMaterialsource(productVo.getProductMaterialsource())//
							.productStatus(productVo.getProductStatus())//
							.productNo(productVo.getProductNo())//
							.productRemark(productVo.getProductRemark()).build();
					pd.setProductOperateIp("127.0.0.1");
					pd.setProductOperateTime(new Date());
					pd.setProductOperator("user01");
					MesProductMapper mapper = sqlSession.getMapper(MesProductMapper.class);
					// 批量增加
					mapper.insertSelective(pd);
				}
			}
		}

		// 到库分页
		public PageResult<ProductDto> searchPageList(SearchProductParam param, PageQuery page) {
			// 校验
			BeanValidator.check(page);
			// vo-dto
			SearchProductDto dto = new SearchProductDto();

			if (StringUtils.isNotBlank(param.getKeyword())) {
				dto.setKeyword("%" + param.getKeyword() + "%");
			}
			if (StringUtils.isNotBlank(param.getSearch_source())) {
				dto.setSearch_source(param.getSearch_source());
				
			}
			if (param.getSearch_status() != null) {
				dto.setSearch_status(param.getSearch_status());
			}
			int count = mesProductCustomerMapper.countBySearchDto(dto);
			if (count > 0) {
				List<ProductDto> productList = mesProductCustomerMapper.getPageListBySearchDto(dto, page);
				return PageResult.<ProductDto>builder().total(count).data(productList).build();
			}

			return PageResult.<ProductDto>builder().build();
		}

	//定义一个id生成器
	private IdGenerator ig = new IdGenerator();
	
	// 1 默认生成代码
		// 2 手工生成代码
		// id生成器
		class IdGenerator {
			// 数量起始位置
			private Long currentdbidscount;
			private List<String> ids = new ArrayList<String>();
			private String idpre;
			private String yearstr;
			private String idafter;

			public IdGenerator() {
			}

			public Long getCurrentdbidscount() {
				return currentdbidscount;
			}

			public void setCurrentdbidscount(Long currentdbidscount) {
				this.currentdbidscount = currentdbidscount;
				if (null == this.ids) {
					this.ids = new ArrayList<String>();
				}
			}

			public List<String> getIds() {
				return ids;
			}

			public void setIds(List<String> ids) {
				this.ids = ids;
			}

			public String getIdpre() {
				return idpre;
			}

			public void setIdpre(String idpre) {
				this.idpre = idpre;
			}

			public String getYearstr() {
				return yearstr;
			}

			public void setYearstr(String yearstr) {
				this.yearstr = yearstr;
			}

			public String getIdafter() {
				return idafter;
			}

			public void setIdafter(String idafter) {
				this.idafter = idafter;
			}

			public List<String> initIds(Long ocounts) {
				for (int i = 0; i < ocounts; i++) {
					this.ids.add(getIdPre() + yearStr() + getIdAfter(i));
				}
				return this.ids;
			}

			//
			private String getIdAfter(int addcount) {
				// 系统默认生成5位 ZX1700001
				int goallength = 7;
				// 获取数据库order的总数量+1+循环次数(addcount)
				int count = this.currentdbidscount.intValue() + 1 + addcount;
				StringBuilder sBuilder = new StringBuilder("");
				// 计算与5位数的差值
				int length = goallength - new String(count + "").length();
				for (int i = 0; i < length; i++) {
					sBuilder.append("0");
				}
				sBuilder.append(count + "");
				return sBuilder.toString();
			}

			private String getIdPre() {
				// idpre==null?this.idpre="ZX":this.idpre=idpre;
				this.idpre = "zx_p_";
				return this.idpre;
			}

			private String yearStr() {
				Date currentdate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String yearstr = sdf.format(currentdate).substring(2, 4);
				return yearstr;
			}

			public void clear() {
				this.ids = null;
			}

			@Override
			public String toString() {
				return "IdGenerator [ids=" + ids + "]";
			}
		}

}
