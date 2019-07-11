package com.zl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zl.dao.TestDomeMapper;
import com.zl.model.TestDome;

@Service
public class TestService {

	@Resource
	private TestDomeMapper mapper;
	
	public void persist(TestDome testDome) {
		mapper.insertSelective(testDome);
	}
}
