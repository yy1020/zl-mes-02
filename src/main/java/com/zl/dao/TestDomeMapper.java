package com.zl.dao;

import com.zl.model.TestDome;

public interface TestDomeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestDome record);

    int insertSelective(TestDome record);

    TestDome selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestDome record);

    int updateByPrimaryKey(TestDome record);
}