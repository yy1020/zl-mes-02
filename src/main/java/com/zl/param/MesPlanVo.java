package com.zl.param;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MesPlanVo {

	private Integer id;// 这个不需要校验，自定auto生成

    private String planOrderid;

    private String planProductname;

    private String planClientname;

    private String planContractid;

    private String planImgid;

    private String planMaterialname;

    private String planMaterialdesc;

    private String planCurrentstatus;

    private String planCurrentremark;

    private String planSalestatus;

    private String planMaterialsource;

    private Integer planHurrystatus;

    private Integer planStatus;
    
    private String planCometime;

    private String planCommittime;

    private String planWorkstarttime;

    private String planWorkendtime;

    private Integer planInventorystatus;

    private String planRemark;
}
