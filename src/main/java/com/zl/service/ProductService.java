package com.zl.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.zl.model.MesOrder;
import com.zl.model.MesStock;
import com.zl.beans.PageQuery;
import com.zl.beans.PageResult;
import com.zl.dto.ProductDto;
import com.zl.dto.SearchProductDto;
import com.zl.param.SearchProductParam;
import com.zl.dao.MesOrderMapper;
import com.zl.dao.MesProductCustomerMapper;
import com.zl.dao.MesProductMapper;
import com.zl.dao.MesStockMapper;
import com.zl.model.MesProduct;
import com.zl.param.MesProductVo;
import com.zl.util.BeanValidator;
import com.zl.util.UUIDUtil;

@Service
public class ProductService {

	@Resource
	private MesProductMapper mesProductMapper;
	@Resource
	private MesOrderMapper mesOrderMapper;
	@Resource
	private MesStockMapper stockMapper;
	@Resource
	private MesProductCustomerMapper mesProductCustomerMapper;

	@Resource
	private SqlSession sqlSession;
	
	//定义一个id生成器
		private IdGenerator ig = new IdGenerator();
	
	// 批量增加材料
		public void insert(MesProductVo productVo) {
			// 校验数据
			BeanValidator.check(productVo);
			// 获取增加个数
			Integer counts = productVo.getCounts();
			
			List<String> ids = createProductIdsDefault(Long.valueOf(counts));
			for(String productid : ids)  {
					// 批量增加-productDto
					MesProduct pd = MesProduct.builder().productId(productid)//
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
		
		//修改
		public void update(MesProductVo productVo) {
			//校验
			BeanValidator.check(productVo);
			
			MesProduct product = mesProductMapper.selectByPrimaryKey(productVo.getId());	
			product.setProductImgid(productVo.getProductImgid());
			product.setProductIrontype(productVo.getProductIrontype());
			product.setProductIrontypeweight(productVo.getProductIrontypeweight());
			product.setProductMaterialname(productVo.getProductMaterialname());
			product.setProductTargetweight(productVo.getProductTargetweight());
			product.setProductMaterialsource(productVo.getProductMaterialsource());
			product.setProductRemark(productVo.getProductRemark());
			product.setProductRealweight(productVo.getProductRealweight());
			product.setProductNo(productVo.getProductNo());
			
			float temp=product.getProductLeftweight()-product.getProductBakweight();
			float leftweight=product.getProductLeftweight();
			
			product.setProductLeftweight(productVo.getProductLeftweight());
			//剩余重量备份需要重新设置
			product.setProductBakweight(product.getProductLeftweight()-temp);
			
			if(leftweight>=temp)
			mesProductMapper.updateByPrimaryKeySelective(product);
			
		}
		
		
		//获取数据库所有的数量
		public Long getProductCount() {
			return mesProductCustomerMapper.getProductCount();
		}
		
		//获取id集合
		public List<String> createProductIdsDefault(Long pcounts){
			if(ig == null) {
				ig = new IdGenerator();
			}
			
			ig.setCurrentdbidscount(getProductCount());
			List<String> list = ig.initIds(pcounts);
			ig.clear();
			return list;
			
		}
	
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

		public void batchStart(String ids) {
			if (StringUtils.isNotEmpty(ids)) {
				MesProductMapper mapper = sqlSession.getMapper(MesProductMapper.class);
				String[] idStrs = ids.split("&");
				for (String id : idStrs) {
					MesProduct mesProduct = mapper.selectByPrimaryKey(Integer.parseInt(id));
					mesProduct.setProductStatus(1);
					mesProduct.setProductOperateTime(new Date());
					mapper.updateByPrimaryKeySelective(mesProduct);
					//生成待入库记录--原料库
					batchStockPre(Integer.parseInt(id),mapper);
				}
			}
		}

		//批量待入库操作-默认进入原料库
		private void batchStockPre(Integer id, MesProductMapper mapper) {
			if(id!=null) {
				//生成库存逻辑
				//增加操作
				MesProduct mesProduct = mapper.selectByPrimaryKey(id);
				//Order*
				if(mesProduct!=null) {
					MesStock mesStock=MesStock.builder().stockProductid(mesProduct.getId())//
							.stockImgid(mesProduct.getProductImgid())//
							.stockProductsource(mesProduct.getProductMaterialsource())//
							.stockStatus(1)//
							.stockStorageid(1).build();//1 代表原料库
					
					Integer orderid=mesProduct.getProductOrderid();
					if(orderid!=null) {
						MesOrder mesOrder=mesOrderMapper.selectByPrimaryKey(mesProduct.getProductOrderid());
						if(mesOrder!=null) {
							mesStock.setStockProductname(mesOrder.getOrderProductname());
							mesStock.setStockOrderid(orderid);
							mesStock.setStockOrdername(mesOrder.getOrderProductname());
						}
					}
					//mesStock.setStockProductname(product.getProductMaterialname());
					mesStock.setStockStoragestatus(1);//待入库
					stockMapper.insertSelective(mesStock);
				}
			}
			
		}
		
		
		
		

}
